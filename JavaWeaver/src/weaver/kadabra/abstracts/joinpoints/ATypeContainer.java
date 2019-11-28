package weaver.kadabra.abstracts.joinpoints;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.lara.interpreter.weaver.interf.JoinPoint;

import weaver.kadabra.abstracts.AJavaWeaverJoinPoint;

/**
 * Auto-Generated class for join point ATypeContainer
 * This class is overwritten by the Weaver Generator.
 * 
 * 
 * @author Lara Weaver Generator
 */
public abstract class ATypeContainer extends AJavaWeaverJoinPoint {

    /**
     * class, interface and enums
     * @return 
     */
    public abstract List<? extends AType> selectType();

    /**
     * Method used by the lara interpreter to select classs
     * @return 
     */
    public abstract List<? extends AClass> selectClass();

    /**
     * Method used by the lara interpreter to select interfaces
     * @return 
     */
    public abstract List<? extends AInterface> selectInterface();

    /**
     * comments that start with @ followed by the pragma name
     * @return 
     */
    public abstract List<? extends APragma> selectPragma();

    /**
     * Method used by the lara interpreter to select comments
     * @return 
     */
    public abstract List<? extends AComment> selectComment();

    /**
     * 
     */
    @Override
    public List<? extends JoinPoint> select(String selectName) {
        List<? extends JoinPoint> joinPointList;
        switch(selectName) {
        	case "type": 
        		joinPointList = selectType();
        		break;
        	case "class": 
        		joinPointList = selectClass();
        		break;
        	case "interface": 
        		joinPointList = selectInterface();
        		break;
        	case "pragma": 
        		joinPointList = selectPragma();
        		break;
        	case "comment": 
        		joinPointList = selectComment();
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
    public void defImpl(String attribute, Object value) {
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
    protected void fillWithAttributes(List<String> attributes) {
        super.fillWithAttributes(attributes);
    }

    /**
     * 
     */
    @Override
    protected void fillWithSelects(List<String> selects) {
        super.fillWithSelects(selects);
        selects.add("type");
        selects.add("class");
        selects.add("interface");
        selects.add("pragma");
        selects.add("comment");
    }

    /**
     * 
     */
    @Override
    protected void fillWithActions(List<String> actions) {
        super.fillWithActions(actions);
    }

    /**
     * Returns the join point type of this class
     * @return The join point type
     */
    @Override
    public String get_class() {
        return "TypeContainer";
    }
    /**
     * 
     */
    protected enum TypeContainerAttributes {
        SRCCODE("srcCode"),
        LINE("line"),
        ANCESTOR("ancestor");
        private String name;

        /**
         * 
         */
        private TypeContainerAttributes(String name){
            this.name = name;
        }
        /**
         * Return an attribute enumeration item from a given attribute name
         */
        public static Optional<TypeContainerAttributes> fromString(String name) {
            return Arrays.asList(values()).stream().filter(attr -> attr.name.equals(name)).findAny();
        }

        /**
         * Return a list of attributes in String format
         */
        public static List<String> getNames() {
            return Arrays.asList(values()).stream().map(TypeContainerAttributes::name).collect(Collectors.toList());
        }

        /**
         * True if the enum contains the given attribute name, false otherwise.
         */
        public static boolean contains(String name) {
            return fromString(name).isPresent();
        }
    }
}
