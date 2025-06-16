package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AApp
 * This class is overwritten by the Weaver Generator.
 * 
 * Root node that represents the application
 * @author Lara Weaver Generator
 */
public abstract class AApp extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute files
     * @return the attribute's value
     */
    public abstract AFile[] getFilesArrayImpl();

    /**
     * Get value on attribute files
     * @return the attribute's value
     */
    public Object getFilesImpl() {
        AFile[] aFileArrayImpl0 = getFilesArrayImpl();
        Object nativeArray0 = aFileArrayImpl0;
        return nativeArray0;
    }

    /**
     * Get value on attribute files
     * @return the attribute's value
     */
    public final Object getFiles() {
        try {
        	Object result = this.getFilesImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "files", e);
        }
    }

    /**
     * Get value on attribute folder
     * @return the attribute's value
     */
    public abstract String getFolderImpl();

    /**
     * Get value on attribute folder
     * @return the attribute's value
     */
    public final Object getFolder() {
        try {
        	String result = this.getFolderImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "folder", e);
        }
    }

    /**
     * Get value on attribute manifest
     * @return the attribute's value
     */
    public abstract AAndroidManifest getManifestImpl();

    /**
     * Get value on attribute manifest
     * @return the attribute's value
     */
    public final Object getManifest() {
        try {
        	AAndroidManifest result = this.getManifestImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "manifest", e);
        }
    }

    /**
     * 
     * @param Title
     * @return 
     */
    public abstract String showASTImpl(String Title);

    /**
     * 
     * @param Title
     * @return 
     */
    public final Object showAST(String Title) {
        try {
        	String result = this.showASTImpl(Title);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "showAST", e);
        }
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param interfaceType 
     * @param methodName 
     */
    public AClass mapVersionsImpl(String name, String keyType, AInterfaceType interfaceType, String methodName) {
        throw new UnsupportedOperationException(get_class()+": Action mapVersions not implemented ");
    }

    /**
     * 
     * @param name 
     * @param keyType 
     * @param interfaceType 
     * @param methodName 
     */
    public final Object mapVersions(String name, String keyType, AInterfaceType interfaceType, String methodName) {
        try {
        	AClass result = this.mapVersionsImpl(name, keyType, interfaceType, methodName);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "mapVersions", e);
        }
    }

    /**
     * 
     * @param name 
     * @param extend 
     * @param implement 
     */
    public AClass newClassImpl(String name, String extend, String[] implement) {
        throw new UnsupportedOperationException(get_class()+": Action newClass not implemented ");
    }

    /**
     * 
     * @param name 
     * @param extend 
     * @param implement 
     */
    public final Object newClass(String name, String extend, Object[] implement) {
        try {
        	AClass result = this.newClassImpl(name, extend, pt.up.fe.specs.util.SpecsCollections.cast(implement, String.class));
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newClass", e);
        }
    }

    /**
     * 
     * @param name 
     */
    public AClass newClassImpl(String name) {
        throw new UnsupportedOperationException(get_class()+": Action newClass not implemented ");
    }

    /**
     * 
     * @param name 
     */
    public final Object newClass(String name) {
        try {
        	AClass result = this.newClassImpl(name);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newClass", e);
        }
    }

    /**
     * 
     * @param name 
     * @param extend 
     */
    public AInterfaceType newInterfaceImpl(String name, String[] extend) {
        throw new UnsupportedOperationException(get_class()+": Action newInterface not implemented ");
    }

    /**
     * 
     * @param name 
     * @param extend 
     */
    public final Object newInterface(String name, Object[] extend) {
        try {
        	AInterfaceType result = this.newInterfaceImpl(name, pt.up.fe.specs.util.SpecsCollections.cast(extend, String.class));
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newInterface", e);
        }
    }

    /**
     * 
     * @param name 
     */
    public AInterfaceType newInterfaceImpl(String name) {
        throw new UnsupportedOperationException(get_class()+": Action newInterface not implemented ");
    }

    /**
     * 
     * @param name 
     */
    public final Object newInterface(String name) {
        try {
        	AInterfaceType result = this.newInterfaceImpl(name);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "newInterface", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "app";
    }
    /**
     * 
     */
    protected enum AppAttributes {
        FILES("files"),
        FOLDER("folder"),
        MANIFEST("manifest"),
        SHOWAST("showAST"),
        ANNOTATIONS("annotations"),
        AST("ast"),
        ASTPARENT("astParent"),
        CHILD("child"),
        CHILDREN("children"),
        CODE("code"),
        DESCENDANTS("descendants"),
        GETANCESTOR("getAncestor"),
        HASMODIFIER("hasModifier"),
        ID("id"),
        ISBLOCK("isBlock"),
        ISFINAL("isFinal"),
        ISINSIDELOOPHEADER("isInsideLoopHeader"),
        ISSTATEMENT("isStatement"),
        ISSTATIC("isStatic"),
        LEFT("left"),
        LINE("line"),
        MODIFIERS("modifiers"),
        NUMCHILDREN("numChildren"),
        PARENT("parent"),
        RIGHT("right"),
        SRCCODE("srcCode");
        private String name;

        /**
         * 
         */
        private AppAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<AppAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(AppAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
