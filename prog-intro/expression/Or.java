package expression;

public class Or extends AbstractBinaryOperation {

    public Or(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        return a | b;
    }

    @Override
    protected String getSign() {
        return "|";
    }

    @Override
    public Priorities getPriority() {
        return Priorities.OR;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }
}
