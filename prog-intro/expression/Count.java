package expression;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Count extends AbstractUnaryOperation {
    public Count(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        return Integer.bitCount(a);
    }

    @Override
    protected String getSign() {
        return "count";
    }

}
