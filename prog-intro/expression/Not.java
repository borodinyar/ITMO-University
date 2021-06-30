package expression;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class Not extends AbstractUnaryOperation {
    public Not(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        return ~a;
    }

    @Override
    protected String getSign() {
        return "~";
    }

}
