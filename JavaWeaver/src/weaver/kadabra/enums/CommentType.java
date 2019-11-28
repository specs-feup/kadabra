package weaver.kadabra.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;

import pt.up.fe.specs.util.enums.EnumHelperWithValue;
import pt.up.fe.specs.util.lazy.Lazy;

/**
 * 
 * 
 * @author Lara C.
 */
public enum CommentType  implements NamedEnum{
    FILE("file"),
    INLINE("inline"),
    BLOCK("block"),
    JAVADOC("javadoc"),
    PRAGMA("pragma");
    private String name;
    private static final Lazy<EnumHelperWithValue<CommentType>> ENUM_HELPER = EnumHelperWithValue.newLazyHelperWithValue(CommentType.class);

    /**
     * 
     */
    private CommentType(String name){
        this.name = name;
    }
    /**
     * 
     */
    public String getName() {
        return this.name;
    }

    /**
     * 
     */
    public String toString() {
        return getName();
    }

    /**
     * 
     */
    public static EnumHelperWithValue<CommentType> getHelper() {
        return ENUM_HELPER.get();
    }
}
