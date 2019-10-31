package weaver.kadabra.abstracts.joinpoints.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;

/**
 * 
 */
public enum ALoopTypeEnum  implements NamedEnum{
    FOR("for"),
    WHILE("while"),
    DO_WHILE("do-while"),
    FOREACH("foreach");
    private String name;

    /**
     * 
     */
    private ALoopTypeEnum(String name){
        this.name = name;
    }
    /**
     * 
     */
    public String getName() {
        return name;
    }
}
