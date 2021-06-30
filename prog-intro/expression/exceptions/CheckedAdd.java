package expression.exceptions;

import expression.Add;
import expression.CommonExpression;

public class CheckedAdd extends Add {

    public CheckedAdd(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        checkExeptions(a, b);
        return super.operation(a, b);
    }

    private void checkExeptions(int a, int b) {
        if (b > 0 && a > Integer.MAX_VALUE - b) {
            throw new Overflow("Overflow: " + a + " + " + b);
        } else if (b < 0 && a < Integer.MIN_VALUE - b) {
            throw new Underflow("Underflow: " + a + " + (" + b + ")");
        }
    }

}
