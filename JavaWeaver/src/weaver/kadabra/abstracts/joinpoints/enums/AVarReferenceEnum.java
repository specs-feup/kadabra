package weaver.kadabra.abstracts.joinpoints.enums;

import org.lara.interpreter.weaver.interf.NamedEnum;

/**
 * 
 */
public enum AVarReferenceEnum  implements NamedEnum{
    READ("read"),
    WRITE("write"),
    DECL("decl");
    private String name;

    /**
     * 
     */
    private AVarReferenceEnum(String name){
        this.name = name;
    }
    /**
     * 
     */
    public String getName() {
        return name;
    }
}
