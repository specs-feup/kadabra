package weaver.kadabra.abstracts.weaver;

import org.lara.interpreter.weaver.LaraWeaverEngine;
import java.util.Arrays;
import java.util.List;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.enums.CommentType;
import weaver.kadabra.enums.RefType;
import java.util.ArrayList;

/**
 * Abstract Weaver Implementation for JavaWeaver<br>
 * Since the generated abstract classes are always overwritten, their implementation should be done by extending those abstract classes with user-defined classes.<br>
 * The abstract class {@link weaver.kadabra.abstracts.AJavaWeaverJoinPoint} contains attributes and actions common to all join points.
 * The implementation of the abstract methods is mandatory!
 * @author Lara C.
 */
public abstract class AJavaWeaver extends LaraWeaverEngine {

    /**
     * Get the list of available actions in the weaver
     * 
     * @return list with all actions
     */
    @Override
    public final List<String> getActions() {
        String[] weaverActions= {"copy", "insertAfter", "insertAfter", "insertBefore", "insertBefore", "insertReplace", "insertReplace", "remove", "removeAnnotation", "removeModifier", "replaceWith", "replaceWith", "setModifiers", "setText", "extract", "setName", "mapVersions", "newClass", "newClass", "newInterface", "newInterface", "setLhs", "setRhs", "setOperator", "insertBegin", "insertBegin", "clone", "setArgument", "setArguments", "setExecutable", "setTarget", "setTarget", "extractInterface", "insertStatic", "mapVersions", "newConstructor", "newFunctionalClass", "addClass", "addImplement", "addInterface", "insertCode", "insertMethod", "newField", "newField", "newMethod", "newMethod", "removeInterface", "setInit", "addClass", "addImport", "addInterface", "mapVersions", "newClass", "newClass", "newInterface", "newInterface", "removeInterface", "setValue", "setInit", "interchange", "tile", "tile", "addComment", "addParameter", "clone", "createAdapter", "setArguments", "setOperator", "setLine", "setLine", "setOperator", "setAttribute"};
        return Arrays.asList(weaverActions);
    }

    /**
     * Returns the name of the root
     * 
     * @return the root name
     */
    @Override
    public final String getRoot() {
        return "app";
    }

    /**
     * Returns a list of classes that may be imported and used in LARA.
     * 
     * @return a list of importable classes
     */
    @Override
    public final List<Class<?>> getAllImportableClasses() {
        Class<?>[] defaultClasses = {LoopType.class, CommentType.class, RefType.class};
        List<Class<?>> otherClasses = this.getImportableClasses();
        List<Class<?>> allClasses = new ArrayList<>(Arrays.asList(defaultClasses));
        allClasses.addAll(otherClasses);
        return allClasses;
    }

    /**
     * Does the generated code implements events?
     * 
     * @return true if implements events, false otherwise
     */
    @Override
    public final boolean implementsEvents() {
        return true;
    }
}
