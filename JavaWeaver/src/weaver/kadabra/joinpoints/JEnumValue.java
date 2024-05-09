package weaver.kadabra.joinpoints;

import spoon.reflect.declaration.CtElement;
import spoon.reflect.declaration.CtEnumValue;
import weaver.kadabra.abstracts.joinpoints.AEnumValue;

public class JEnumValue<T> extends AEnumValue {

    private final CtEnumValue<T> node;

    public JEnumValue(CtEnumValue<T> node) {
        super(new JField<>(node));

        this.node = node;
    }

    public static <T> JEnumValue<T> newInstance(CtEnumValue<T> field) {
        return new JEnumValue<>(field);
    }

    @Override
    public CtElement getNode() {
        return node;
    }

}
