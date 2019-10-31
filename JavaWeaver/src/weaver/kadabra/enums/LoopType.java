package weaver.kadabra.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;
import pt.up.fe.specs.util.lazy.Lazy;
import pt.up.fe.specs.util.enums.EnumHelperWithValue;

/**
 * 
 * 
 * @author Lara C.
 */
public enum LoopType  implements NamedEnum{
    FOR("for"),
    FOREACH("foreach"),
    WHILE("while"),
    DOWHILE("doWhile");
    private String name;
    private static final Lazy<EnumHelperWithValue<LoopType>> ENUM_HELPER = EnumHelperWithValue.newLazyHelperWithValue(LoopType.class);

    /**
     * 
     */
    private LoopType(String name){
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
    public static EnumHelperWithValue<LoopType> getHelper() {
        return ENUM_HELPER.get();
    }
}
