package weaver.kadabra.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;
import pt.up.fe.specs.util.lazy.Lazy;
import pt.up.fe.specs.util.enums.EnumHelperWithValue;

/**
 * 
 * 
 * @author Lara C.
 */
public enum RefType  implements NamedEnum{
    READ("read"),
    WRITE("write"),
    DECL("decl");
    private String name;
    private static final Lazy<EnumHelperWithValue<RefType>> ENUM_HELPER = EnumHelperWithValue.newLazyHelperWithValue(RefType.class);

    /**
     * 
     */
    private RefType(String name){
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
    public static EnumHelperWithValue<RefType> getHelper() {
        return ENUM_HELPER.get();
    }
}
