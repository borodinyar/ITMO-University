package expression;

public class And extends AbstractBinaryOperation {

    public And(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        return a & b;
    }

    @Override
    protected String getSign() {
        return "&";
    }

    @Override
    public Priorities getPriority() {
        return Priorities.AND;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }
}
