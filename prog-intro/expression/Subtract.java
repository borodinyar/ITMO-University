package expression;

public class Subtract extends AbstractBinaryOperation {

    public Subtract(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        return a - b;
    }

    @Override
    protected strictfp double operation(double a, double b) {
        return a - b;
    }

    @Override
    protected String getSign() {
        return "-";
    }

    @Override
    public Priorities getPriority() {
        return Priorities.ADD;
    }

    @Override
    public boolean needBrackets() {
        return true;
    }
}
