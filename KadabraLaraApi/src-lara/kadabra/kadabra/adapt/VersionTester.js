import Timer from "../monitor/Timer.js";
import { convertPrimitive } from "../Utils.js";
/**
 * Provides basic functionality to test versions by: <br>
 * * adding a new timer, test time, best time, bestVersion, adapt, numRuns and warmup fields, <br>
 * * creating basic code to start the adaptation after "numRuns" executions <br>
 * * creating basic code to update after a given execution <br> <br>
 * must invoke methods "onNewVersion", "ifBetterVersion" and "beforeUpdate" before injecting the adaptation <br>
 * must insert the following variables: adapt(), update(), timerStart, timerStop
 */
export function NewVersionTester($class, targetType, timeUnit = "Millis", numRuns = 1, warmup = 0, jumpIfWorse = false) {
    const convert = convertPrimitive(targetType);
    const testerInit = `VersionTester.newInstance(${JSON.stringify(timeUnit)}, ${numRuns}, ${warmup}, ${jumpIfWorse})`;
    const $tester = $class.newField(["private", "static"], `weaver.kadabra.monitor.VersionTester<${convert.wrapper}>`, "tester", testerInit);
    function onInitialize(code) {
        return `${$tester}.setOnInitialize(()->{${code}});`;
    }
    function onFinalize(code) {
        return `${$tester}.setOnTestFinish(()->{${code}});`;
    }
    function setTests(versions) {
        const vers = versions.join(",\n");
        return `${$tester}.setTests(${vers});`;
    }
    const timerStop = $tester + ".stopTimer();";
    const update = $tester + ".update();";
    return {
        $tester,
        onInitialize,
        onFinalize,
        setTests,
        isAdapting: undefined,
        hasFinished: $tester + ".hasFinished()",
        start: $tester + ".start();",
        pause: $tester + ".pause();",
        stop: $tester + ".stop();",
        timerStart: $tester + ".startTimer();",
        timerStop,
        getTime: $tester + ".getTime()",
        update,
        timerStopAndUpdate: `${timerStop}
					  ${update}`,
        testTime: $tester + ".getTestTime()",
        bestTime: $tester + ".getBestTime()",
        testPos: $tester + ".getTestPos()",
        bestPos: $tester + ".getBestPos()",
        bestVersion: $tester + ".getBestVersion()",
        numRuns: $tester + ".getNumRuns()",
    };
}
const mods = ["private", "static"];
export function VersionTester($class, targetType, targetFieldRef, numRuns = 1, timeUnit = "Millis", warmup = 0, jumpIfWorse = false) {
    let newVersionCode = "";
    let betterVersionCode = "";
    let beforeUpdateCode = "";
    let onInitializeCode = "";
    function onNewVersion(code) {
        newVersionCode = code;
    }
    function ifBetterVersion(code) {
        betterVersionCode = code;
    }
    function beforeUpdate(code) {
        beforeUpdateCode = code;
    }
    function onInitialize(code) {
        onInitializeCode = code;
    }
    let timer = Timer.millisTimer();
    switch (timeUnit.toUpperCase()) {
        case "MILLIS":
        case "MILLISECONDS":
        case "MS":
            break;
        case "NANO":
        case "NANOSECONDS":
        case "NS":
            timer = Timer.nanoTimer();
            break;
        default:
            throw new Error(`The given time unit cannot be used: ${timeUnit}. Please use "nanoseconds"/"ns" or "milliseconds"/"ms"`);
    }
    const convert = convertPrimitive(targetType);
    const $warmup = $class.newField(mods, "int", "warmup", "0");
    const $numRuns = $class.newField(mods, "int", "numRuns", "0");
    const $bestVersion = $class.newField(mods, convert.wrapper, "bestVersion", "null");
    const $bestTime = $class.newField(mods, "long", "bestTime", "java.lang.Long.MAX_VALUE");
    const $testTime = $class.newField(mods, "long", "testTime", "0");
    const $adapt = $class.newField(mods, "boolean", "adapt", "false");
    const $init = $class.newField(mods, "boolean", "initialized", "false");
    function adapt(alwaysAdapt, useNewVersionInInit) {
        let header = "";
        let newVersionInInit = "";
        if (alwaysAdapt) {
            // means that will always execute the adaptation, even if "+$adapt+" is false!
            header = $adapt + " = true;";
        }
        else {
            header = `if(${$adapt})`;
        }
        if (useNewVersionInInit) {
            newVersionInInit = newVersionCode;
        }
        const resetVars = `
			${$testTime} = 0;
			${$numRuns} = ${numRuns};
			${$warmup} = ${warmup};
		`;
        return `
			${header} { //adapt begin
				if(!${$init}){ //Reset all variables so the test starts at the first version and without best time!
					${timerStop}
					${$bestTime} = Long.MAX_VALUE;
					${$testTime} = 0;
					${$bestVersion} = null;
					${$init} = true;
					${resetVars}
					${onInitializeCode}

					${newVersionInInit}
				}
				
				if(${$numRuns} == 0){
					boolean improved = false;
					//If test version is better than the current best version
					if(${$testTime} < ${$bestTime}){
						${$bestVersion} = ${targetFieldRef};
						${$bestTime} = ${$testTime};
						improved = true;
						//execute extra code
						${betterVersionCode}
					}
					//execute code that provides a new version
					${newVersionCode}
	
					//Reset counters and timers for the next test;
					${resetVars}
				}
			} //adapt end`;
    }
    const timerStart = timer.start;
    const timerStop = timer.stop;
    const getTime = timer.getTime;
    const ifWorseCode = !jumpIfWorse
        ? "" //do nothing
        : `if(${$testTime} > ${$bestTime}){ //else, If it is taking more time than the best version, then we discard this version
			${$numRuns} = 0;
		   } else`;
    function update() {
        return `
		${beforeUpdateCode}
		if (${$adapt}) {
			if (${$warmup} == 0) {
				${$testTime} += ${timer.getTime};
				${ifWorseCode}
				${$numRuns}--;
			} else {
				${$warmup}--;
			}
		}`;
    }
    const isAdapting = $adapt.name;
    const start = $adapt + " = true;";
    const pause = $adapt + " = false;";
    const stop = `
		${$init} = false;
		${$adapt} = false;`;
    return {
        $testTime,
        $bestTime,
        $bestVersion,
        $numRuns,
        $improved: "improved",
        ifBetterVersion,
        onNewVersion,
        beforeUpdate,
        onInitialize,
        timerStart,
        timerStop,
        getTime,
        adapt,
        update,
        start,
        stop,
        pause,
        isAdapting,
    };
}
//# sourceMappingURL=VersionTester.js.map