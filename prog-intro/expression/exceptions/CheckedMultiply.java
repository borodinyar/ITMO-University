package expression.exceptions;

import expression.CommonExpression;
import expression.Multiply;

public class CheckedMultiply extends Multiply {
    public CheckedMultiply(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    protected int operation(int a, int b) {
        int result = super.operation(a, b);
        checkExeptions(a, b, result);
        return result;
    }

    private void checkExeptions(int a, int b, int result) {
        if (a != 0 && result / a != b || b != 0 &&  result / b != a) {
            if (a > 0 && b > 0 || a < 0 && b < 0) {
                throw new Overflow("Overflow: " + a + " * " + b);
            } else {
                throw new Underflow("Underflow: " + a + " * " + b);
            }
        }
    }
}
