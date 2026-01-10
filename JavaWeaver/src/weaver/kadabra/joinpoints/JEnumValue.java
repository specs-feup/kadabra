package weaver.kadabra.joinpoints;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnumValue;
import weaver.kadabra.JavaWeaver;
import weaver.kadabra.abstracts.joinpoints.AEnumValue;

public class JEnumValue<T> extends AEnumValue {

    private final CtEnumValue<T> node;

    public JEnumValue(CtEnumValue<T> node, JavaWeaver weaver) {
        super(new JField<>(node, weaver), weaver);

        this.node = node;
    }

    public static <T> JEnumValue<T> newInstance(CtEnumValue<T> field, JavaWeaver weaver) {
        return new JEnumValue<>(field, weaver);
    }

    @Override
    public CtElement getNode() {
        return node;
    }

}
