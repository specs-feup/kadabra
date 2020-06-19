package weaver.kadabra.abstracts.weaver;

import org.lara.interpreter.weaver.interf.WeaverEngine;
import java.util.Arrays;
import java.util.List;
import weaver.kadabra.entities.Pair;
import weaver.kadabra.enums.LoopType;
import weaver.kadabra.enums.CommentType;
import weaver.kadabra.enums.RefType;
import java.util.ArrayList;

/**
 * Abstract Weaver Implementation for JavaWeaver<br>
 * Since the generated abstract classes are always overwritten, their implementation should be done by extending those abstract classes with user-defined classes.<br>
 * The abstract class {@link weaver.kadabra.abstracts.AJavaWeaverJoinPoint} can be used to add user-defined methods and fields which the user intends to add for all join points and are not intended to be used in LARA aspects.
 * The implementation of the abstract methods is mandatory!
 * @author Lara C.
 */
public abstract class AJavaWeaver extends WeaverEngine {

    /**
     * Get the list of available actions in the weaver
     * 
     * @return list with all actions
     */
    @Override
    public final List<String> getActions() {
        String[] weaverActions= {"insertBefore", "insertBefore", "insertAfter", "insertAfter", "insertReplace", "insertReplace", "copy", "remove", "removeAnnotation", "setText", "setAttribute", "newClass", "newClass", "newInterface", "newInterface", "addClass", "addInterface", "mapVersions", "setName", "newConstructor", "newMethod", "newMethod", "insertMethod", "insertCode", "newFunctionalClass", "insertStatic", "extractInterface", "addImplement", "newField", "newField", "addComment", "addParameter", "createAdapter", "clone", "clone", "tile", "tile", "interchange", "setInit", "setLhs", "setRhs", "extract", "setInit", "insertBegin", "insertBegin", "setOperator", "setOperator", "setValue"};
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
        Class<?>[] defaultClasses = {Pair.class, LoopType.class, CommentType.class, RefType.class};
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
