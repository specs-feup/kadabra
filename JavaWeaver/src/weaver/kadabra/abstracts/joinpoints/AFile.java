package weaver.kadabra.abstracts.joinpoints;

import org.lara.interpreter.exception.AttributeException;
import org.lara.interpreter.exception.ActionException;
import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.List;

/**
 * Auto-Generated class for join point AFile
 * This class is overwritten by the Weaver Generator.
 * 
 * Represents a source-code file
 * @author Lara Weaver Generator
 */
public abstract class AFile extends AJavaWeaverJoinPoint {

    /**
     * Get value on attribute dir
     * @return the attribute's value
     */
    public abstract String getDirImpl();

    /**
     * Get value on attribute dir
     * @return the attribute's value
     */
    public final Object getDir() {
        try {
        	String result = this.getDirImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "dir", e);
        }
    }

    /**
     * Main class of the file. Java files must have a top level class with the same name as the file.
     */
    public abstract AType getMainClassImpl();

    /**
     * Main class of the file. Java files must have a top level class with the same name as the file.
     */
    public final Object getMainClass() {
        try {
        	AType result = this.getMainClassImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "mainClass", e);
        }
    }

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public abstract String getNameImpl();

    /**
     * Get value on attribute name
     * @return the attribute's value
     */
    public final Object getName() {
        try {
        	String result = this.getNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "name", e);
        }
    }

    /**
     * Get value on attribute numClasses
     * @return the attribute's value
     */
    public abstract Integer getNumClassesImpl();

    /**
     * Get value on attribute numClasses
     * @return the attribute's value
     */
    public final Object getNumClasses() {
        try {
        	Integer result = this.getNumClassesImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "numClasses", e);
        }
    }

    /**
     * Get value on attribute numInterfaces
     * @return the attribute's value
     */
    public abstract Integer getNumInterfacesImpl();

    /**
     * Get value on attribute numInterfaces
     * @return the attribute's value
     */
    public final Object getNumInterfaces() {
        try {
        	Integer result = this.getNumInterfacesImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "numInterfaces", e);
        }
    }

    /**
     * Get value on attribute packageName
     * @return the attribute's value
     */
    public abstract String getPackageNameImpl();

    /**
     * Get value on attribute packageName
     * @return the attribute's value
     */
    public final Object getPackageName() {
        try {
        	String result = this.getPackageNameImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "packageName", e);
        }
    }

    /**
     * Get value on attribute path
     * @return the attribute's value
     */
    public abstract String getPathImpl();

    /**
     * Get value on attribute path
     * @return the attribute's value
     */
    public final Object getPath() {
        try {
        	String result = this.getPathImpl();
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new AttributeException(get_class(), "path", e);
        }
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    public void addClassImpl(AClass newClass) {
        throw new UnsupportedOperationException(get_class()+": Action addClass not implemented ");
    }

    /**
     * insert a given class inside the target
     * @param newClass 
     */
    public final void addClass(AClass newClass) {
        try {
        	this.addClassImpl(newClass);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addClass", e);
        }
    }

    /**
     * 
     * @param qualifiedName 
     */
    public void addImportImpl(String qualifiedName) {
        throw new UnsupportedOperationException(get_class()+": Action addImport not implemented ");
    }

    /**
     * 
     * @param qualifiedName 
     */
    public final void addImport(String qualifiedName) {
        try {
        	this.addImportImpl(qualifiedName);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addImport", e);
        }
    }

    /**
     * 
     * @param newInterface 
     */
    public void addInterfaceImpl(AInterfaceType newInterface) {
        throw new UnsupportedOperationException(get_class()+": Action addInterface not implemented ");
    }

    /**
     * 
     * @param newInterface 
     */
    public final void addInterface(AInterfaceType newInterface) {
        try {
        	this.addInterfaceImpl(newInterface);
        } catch(Exception e) {
        	throw new ActionException(get_class(), "addInterface", e);
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
     * 
     * @param interfaceName 
     */
    public AInterfaceType removeInterfaceImpl(String interfaceName) {
        throw new UnsupportedOperationException(get_class()+": Action removeInterface not implemented ");
    }

    /**
     * 
     * @param interfaceName 
     */
    public final Object removeInterface(String interfaceName) {
        try {
        	AInterfaceType result = this.removeInterfaceImpl(interfaceName);
        	return result!=null?result:getUndefinedValue();
        } catch(Exception e) {
        	throw new ActionException(get_class(), "removeInterface", e);
        }
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "file";
    }
    /**
     * 
     */
    protected enum FileAttributes {
        DIR("dir"),
        MAINCLASS("mainClass"),
        NAME("name"),
        NUMCLASSES("numClasses"),
        NUMINTERFACES("numInterfaces"),
        PACKAGENAME("packageName"),
        PATH("path"),
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
        private FileAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<FileAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(FileAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
