package expression;

public class Xor extends AbstractBinaryOperation {

    public Xor(CommonExpression firstOperation, CommonExpression secondOperation) {
        super(firstOperation, secondOperation);
    }

    @Override
    protected int operation(int a, int b) {
        return a ^ b;
    }

    @Override
    protected String getSign() {
        return "^";
    }

    @Override
    public Priorities getPriority() {
        return Priorities.XOR;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }
}
