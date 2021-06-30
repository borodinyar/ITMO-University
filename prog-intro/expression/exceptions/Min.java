package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.CommonExpression;
import expression.Priorities;

import static expression.Priorities.MAX;

public class Min extends AbstractBinaryOperation {
    public Min(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        if (a < b) {
            return a;
        }
        return b;
    }

    @Override
    protected String getSign() {
        return "min";
    }

    @Override
    public Priorities getPriority() {
        return MAX;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }
}
