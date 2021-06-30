package expression.exceptions;

import expression.CommonExpression;
import expression.Subtract;

public class CheckedSubtract extends Subtract {
    public CheckedSubtract(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        checkExeptions(a, b);
        return super.operation(a, b);
    }

    private void checkExeptions(int a, int b) {
        if (b > 0 && a < Integer.MIN_VALUE + b) {
            throw new Overflow("Underflow: " + a + " - " + b);
        } else if (b < 0 && a > Integer.MAX_VALUE + b) {
            throw new Underflow("Overflow: " + a + " - (" + b + ")");
        }
    }
}
