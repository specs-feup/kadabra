package weaver.kadabra.abstracts.joinpoints;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.lara.interpreter.weaver.interf.JoinPoint;

import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;

/**
 * Auto-Generated class for join point ALib
 * This class is overwritten by the Weaver Generator.
 * 
 * join point containing all information regarding included types
 * @author Lara Weaver Generator
 */
public abstract class ALib extends AJavaWeaverJoinPoint {

    /**
     * class pertaining to a library, included in the classpath. This select may be slow, depending on the number of included types.
     * @return 
     */
    public abstract List<? extends ALibClass> selectClass();

    /**
     * 
     */
    @Override
    public final List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "class": 
        		joinPointList = selectClass();
        		break;
        	default:
        		joinPointList = super.select(selectName);
        		break;
        }
        return joinPointList;
    }

    /**
     * 
     */
    @Override
    public final void defImpl(String attribute, Object value) {
        switch(attribute){
        case "line": {
        	if(value instanceof Integer){
        		this.defLineImpl((Integer)value);
        		return;
        	}
        	if(value instanceof String){
        		this.defLineImpl((String)value);
        		return;
        	}
        	this.unsupportedTypeForDef(attribute, value);
        }
        default: throw new UnsupportedOperationException("Join point "+get_class()+": attribute '"+attribute+"' cannot be defined");
        }
    }

    /**
     * 
     */
    @Override
    protected final void fillWithAttributes(List<String> attributes) {
        super.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected final void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("class");
    }

    /**
     * 
     */
    @Override
    protected final void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public final String get_class() {
        return "lib";
    }
    /**
     * 
     */
    protected enum LibAttributes {
        SRCCODE("srcCode"),
        LINE("line"),
        ANCESTOR("ancestor");
        private String name;

        /**
         * 
         */
        private LibAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<LibAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(LibAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
