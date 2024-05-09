package weaver.kadabra.joinpoints;

import spoon.reflect.code.CtCatch;
import spoon.reflect.declaration.CtElement;
import weaver.kadabra.abstracts.joinpoints.ABody;
import weaver.kadabra.abstracts.joinpoints.ACatch;
import weaver.utils.weaving.converters.CtElement2JoinPoint;

public class JCatch extends ACatch {

    private final CtCatch node;

    public JCatch(CtCatch node) {
        this.node = node;
    }

    @Override
    public CtElement getNode() {
        return node;
    }

    @Override
    public ABody getBodyImpl() {
        return CtElement2JoinPoint.convert(node.getBody(), ABody.class);
    }

}
