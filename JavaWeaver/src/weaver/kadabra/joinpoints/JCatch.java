package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtCatch;

import weaver.kadabra.JWeaver;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ACatch;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JCatch<Self extends JCatch<Self>> extends ACatch<Self> {

    public JCatch(CtCatch node, JWeaver weaver) {
        super(node, weaver);
    }

    @Override
    public CtCatch getNodeImpl() {
        return (CtCatch) super.getNodeImpl();
    }

    @Override
    public ABody<?> getBodyImpl() {
        return CtElement2JoinPoint.convert(getNodeImpl().getBody(), getWeaverEngine(), ABody.class);
    }

}
