package weaver.kadabra.abstracts.joinpoints.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;

/**
 * 
 */
public enum ACommentTypeEnum  implements NamedEnum{
    FILE("file"),
    INLINE("inline"),
    BLOCK("block"),
    JAVADOC("javadoc"),
    PRAGMA("pragma");
    private String name;

    /**
     * 
     */
    private ACommentTypeEnum(String name){
        this.name = name;
    }
    /**
     * 
     */
    public String getName() {
        return name;
    }
}
