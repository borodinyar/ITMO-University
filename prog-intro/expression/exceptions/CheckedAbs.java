package expression.exceptions;

import expression.AbstractUnaryOperation;
import expression.CommonExpression;

public class CheckedAbs extends AbstractUnaryOperation {

    public CheckedAbs(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        checkExeptions(a);
        if (a < 0) {
            return -a;
        }
        return a;
    }

    private void checkExeptions(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new Overflow("overflow");
        }
    }

    @Override
    protected String getSign() {
        return "abs";
    }
}