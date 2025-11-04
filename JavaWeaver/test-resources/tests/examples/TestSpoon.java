package tests.examples;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import spoon.Launcher;
import spoon.reflect.declaration.CtClass;
import spoon.reflect.declaration.CtInterface;
import spoon.reflect.declaration.CtPackage;
import spoon.reflect.factory.Factory;
import weaver.utils.scanners.NodeSearcher;

/**
 * Copyright 2015 SPeCS.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

public class TestSpoon {

    public static void main(String[] args) throws Exception {
	final Launcher spoon = new Launcher();
	final Factory factory = spoon.getFactory();
	// ProcessingVisitor pv = new ProcessingVisitor(factory);
	// spoon.getEnvironment().setDebug(true);
	final String src = "src/";
	spoon.addInputResource(src);

	spoon.buildModel();

	final List<CtClass<?>> classes = new ArrayList<>();

	// BiConsumerClassMap<DFSProcessor, List<>>

	for (final CtPackage p : factory.Package().getAll()) {
	    System.out.println("package: " + p.getQualifiedName());

	    // Utils.get(p, classes, CtClass.class, e->{...})

	    // DFSProcessor<CtClass<?>> processor = e -> classes.add(e);
	    // DFS.scan(p, CtClass.class, e -> classes.add(e));
	    NodeSearcher.scan(p, CtInterface.class, e -> System.out.println("int: " + e.getSimpleName()));
	    // DFS dfs = new DFS(CtClass.class, TestSpoon::processCtClass);
	    // dfs.scan(p);
	    // List<String> classeNames = DFS.list(CtClass.class, p).stream()
	    final int charCount = NodeSearcher.list(CtClass.class, p, Collections.emptyList()).stream()
		    // .filter(e -> !CtEnum.class.isInstance(e))
		    .map(e -> e.getSimpleName().length()).reduce(0, (acc, newValue) -> acc + newValue);
	    // .collect(Collectors.toList());

	    System.out.println("CLass names:" + charCount);
	}

	System.out.println("CLASSES: ");

	for (final CtClass<?> ctClass : classes) {
	    System.out.println("\t" + ctClass.getSimpleName());
	}
	// String canonicalName = DPS.class.getCanonicalName();

    }

    /*
     * private static void processCtClass(CtClass ctClass) { // ctClass. // if()
     * System.out.println("YAYA"); }
     */

    /*
     * private static void printTest(Launcher spoon) { Factory factory =
     * spoon.getFactory();
     * 
     * // list all packages of the model for (CtPackage p :
     * factory.Package().getAll()) { System.out.println("package: " +
     * p.getQualifiedName()); } // list all classes of the model for (CtType<?>
     * s : factory.Class().getAll()) { System.out.println("class: " +
     * s.getQualifiedName()); System.out.println(s); } }
     */

    /*
     * private Factory factory;
     * 
     * public static void main(String[] args) throws Exception { String[] args2
     * = { "-i", "src", "--with-imports", "-g" }; Launcher.main(args2);
     * 
     * }
     * 
     * // public static void main(String[] args) throws Exception { // String[]
     * args2 = { "-i", "examples" }; // TestSpoon ts = new TestSpoon(); //
     * ts.setArgs(args2); // if (args2.length != 0) { // ts.run(); // } else {
     * // ts.printUsage(); // } // }
     * 
     * /** Starts the Spoon processing.
     *
     * @Override public void run() throws Exception { Test t = Test.first;
     * factory = getFactory(); //
     * factory.getEnvironment().reportProgressMessage(getVersionMessage());
     * 
     * factory.getEnvironment().debugMessage("loading command-line arguments: "
     * + Arrays.asList(this.jsapActualArgs));
     * 
     * OutputType outputType =
     * OutputType.fromString(jsapActualArgs.getString("output-type")); if
     * (outputType == null) { factory.getEnvironment().report(null,
     * Severity.ERROR, "unsupported output type: " +
     * jsapActualArgs.getString("output-type")); printUsage(); throw new
     * Exception("unsupported output type: " +
     * jsapActualArgs.getString("output-type")); }
     * 
     * SpoonCompiler compiler = createCompiler(factory); run(compiler,
     * jsapActualArgs.getString("encoding"),
     * jsapActualArgs.getBoolean("precompile"), outputType,
     * jsapActualArgs.getFile("output"), getProcessorTypes(),
     * jsapActualArgs.getBoolean("compile"),
     * jsapActualArgs.getFile("destination"),
     * jsapActualArgs.getBoolean("buildOnlyOutdatedFiles"),
     * jsapActualArgs.getString("source-classpath"),
     * jsapActualArgs.getString("template-classpath"), getInputSources(),
     * getTemplateSources()); Factory fac = compiler.getFactory();
     * 
     * List<CtSimpleType<?>> all = fac.Class().getAll(); for (CtSimpleType<?>
     * ctSimpleType : all) { CtClassImpl<?> cla = (CtClassImpl<?>) ctSimpleType;
     * System.out.println(cla.getSuperclass()); }
     * 
     * // display GUI if (getArguments().getBoolean("gui")) { new
     * SpoonModelTree(compiler.getFactory()); }
     * 
     * }
     */
}
