package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.CommonExpression;

public class CheckedSqrt extends AbstractUnaryOperation {

    public CheckedSqrt(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        if (a < 0) {
            throw new IllegalArgumentException("Invalid argument in sqrt: " + a);
        }
        return (int)Math.sqrt(a);
    }

    @Override
    protected String getSign() {
        return "sqrt";
    }
}