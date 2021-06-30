package expression.exceptions;

import expression.AbstractBinaryOperation;
import expression.CommonExpression;
import expression.Priorities;

import static expression.Priorities.MAX;

public class Max extends AbstractBinaryOperation {
    public Max(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        if (a > b) {
            return a;
        }
        return b;
    }

    @Override
    protected String getSign() {
        return "max";
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
