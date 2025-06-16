package weaver.kadabra.abstracts.joinpoints;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
