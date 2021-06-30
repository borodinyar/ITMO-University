package expression.exceptions;

import expression.CommonExpression;
import expression.Minus;

public class CheckedNegate extends Minus {
    public CheckedNegate(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        checkExeptions(a);
        return super.operation(a);
    }

    private void checkExeptions(int a) {
        if (a == Integer.MIN_VALUE) {
            throw new Overflow("overflow");
        }
    }
}
