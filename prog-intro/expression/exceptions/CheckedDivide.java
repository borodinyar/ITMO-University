package expression.exceptions;

import expression.CommonExpression;
import expression.Divide;

public class CheckedDivide extends Divide {

    public CheckedDivide(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    protected int operation(int a, int b) {
        checkExeptions(a, b);
        return super.operation(a, b);
    }

    private void checkExeptions(int a, int b) {
        if (a == Integer.MIN_VALUE && b == -1) {
            throw new Overflow("Overflow: " + Integer.MIN_VALUE + " / " + "(-1)");
        } else if (b == 0) {
            throw new DivisionByZero("Division by zero");
        }
    }
}
