import Query from "@specs-feup/lara/api/weaver/Query.js";
import { Class, FileJp, Method, App } from "../Joinpoints.js";
import { LaraJoinPoint } from "@specs-feup/lara/api/LaraJoinPoint.js";
export class Factory {
    /**
     * Retrieves an existing class or creates a new one.
     *
     * @param qualifiedName - The qualified name of the class.
     * @param extend - The class to extend (optional).
     * @param implement - The interfaces to implement (optional).
     * @param target - The target join point (optional).
     * @returns The class join point.
     */
    static getOrNewClass(qualifiedName, extend = null, implement = [], target) {
        /*
        select $sel=class{qualifiedName~=qualifiedName} end
        apply
            $class = $sel;
            return;
        end

        Is '~=' the same as '=='?

        Also, could I write this like this:
        const existingClass = Query.search(Class, (cls: any) => cls.qualifiedName == qualifiedName)[0];
        if (existingClass) {
            return existingClass;
        }
        */
        const existingClass = Query.search(Class, (cls) => cls.qualifiedName == qualifiedName);
        for (const cls of existingClass) {
            if (cls) {
                return cls;
            }
        }
        return this.newClass(qualifiedName, extend, implement, target);
    }
    /**
     * Creates a new class.
     *
     * @param qualifiedName - The qualified name of the class.
     * @param extend - The class to extend (optional).
     * @param implement - The interfaces to implement (optional).
     * @param target - The target join point (optional).
     * @returns The newly created class join point.
     */
    static newClass(qualifiedName, extend = null, implement = [], target) {
        if (!target) {
            target = Query.root();
        }
        if (!(target instanceof LaraJoinPoint)) {
            throw new Error("Target join point must be a valid join point.");
        }
        if (!(target.instanceOf("app") || target.instanceOf("file"))) {
            throw new Error('The target join point for a new class must be "app" or "file".');
        }
        // $target.exec $c:newClass(qualifiedName, extend, implement);
        // I think this is how to do this?
        return target.newClass(qualifiedName, extend, implement);
    }
    /**
     * Generates a provider function.
     *
     * @param code - The code to be executed by the provider.
     * @param args - The arguments for the provider (optional).
     * @returns The provider function as a string.
     */
    static providerOf(code, args) {
        let providerCode = "(";
        if (args != undefined && args.length > 0) {
            providerCode += args[0];
            for (let i = 1; i < args.length; i++) {
                providerCode += "," + args[i];
            }
        }
        return providerCode + ") -> " + code;
    }
    /**
     * Generates a functional interface based on a method of a class.
     *
     * @param targetMethod - The name of the target method.
     * @param targetClass - The name of the target class (optional).
     * @param targetFile - The name of the target file (optional).
     * @param associate - Whether to associate the interface with the class (optional).
     * @param newFile - Whether to create the interface in a new file (optional).
     * @returns The generated functional interface and related information.
     */
    static generateFunctionalInterface(targetMethod, targetClass = ".*", targetFile = ".*", associate = false, newFile = true) {
        // select app.file{name~=targetFile}.class{qualifiedName~=targetClass}.method{name==targetMethod} end
        const class1 = Query.search(App).search(FileJp, (file) => file.name == targetFile).search(Class, (cls) => cls.qualifiedName == targetClass);
        const method = class1.search(Method, { name: targetMethod });
        if (!method) {
            throw new Error("Could not find the method to extract a functional interface, specified by the conditions: " +
                "file{" + targetFile + "}.class{" + targetClass + "}.method{" + targetMethod + "}");
        }
        let interface1 = undefined;
        let defMethod = undefined;
        let tempClass = undefined;
        for (const m of method) {
            if (interface1 != undefined) {
                throw Error("More than one method to be extracted, please redefine this aspect call. Target method: " + targetMethod +
                    ". 1st Location" + tempClass.qualifiedName + ". 2nd Location " + class1.getFirst().qualifiedName);
            }
            console.log("[LOG] Extracting functional interface from " + class1.getFirst().name + "#" + m.name);
            const interfacePackage = class1.getFirst().packageName;
            const interfaceName = 'I' + m.name.charAt(0).toUpperCase() + m.name.slice(1);
            const newInterface = class1.getFirst().extractInterface(interfaceName, interfacePackage, m, associate, newFile);
            if (!newFile) {
                // newInterface.def modifiers = 'static';
                // I think this is it?
                newInterface.def('static', class1.getFirst().modifiers);
                class1.getFirst().addInterface(newInterface);
            }
            interface1 = newInterface;
            defMethod = m;
            tempClass = class1;
        }
        return {
            interface: interface1,
            defaultMethod: defMethod,
            function: Query.searchFrom(interface1).search(Method, { name: targetMethod }).getFirst(),
            targetMethodName: targetMethod
        };
    }
}
/**
 * Utility class for defining common modifiers.
 */
export class Mod {
    static PRIVATE = "private";
    static PUBLIC = "public";
    static PROTECTED = "protected";
    static STATIC = "static";
    static PUBLIC_STATIC = ["public", "static"];
    static PRIVATE_STATIC = ["private", "static"];
}
//# sourceMappingURL=Factory.js.map