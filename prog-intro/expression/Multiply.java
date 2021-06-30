package expression;

public class Multiply extends AbstractBinaryOperation {
    public Multiply(CommonExpression firstOperand, CommonExpression secondOperand) {
        super(firstOperand, secondOperand);
    }

    @Override
    protected int operation(int a, int b) {
        return a * b;
    }

    @Override
    protected strictfp double operation(double a, double b) {
        return a * b;
    }

    @Override
    protected String getSign() {
        return "*";
    }

    @Override
    public Priorities getPriority() {
        return Priorities.MULTIPLY;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }
}
