package expression;

public abstract class AbstractUnaryOperation implements CommonExpression{

    private final CommonExpression operand;

    public AbstractUnaryOperation(CommonExpression operand) {
        this.operand = operand;
    }

    abstract protected int operation(int a);

    protected double operation(double a) {
        throw new UnsupportedOperationException("Cannot apply operation to double values");
    }

    abstract protected String getSign();

    @Override
    public Priorities getPriority() {
        return operand.getPriority();
    }

    @Override
    public boolean needBrackets() {
        return operand.needBrackets();
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return operation(operand.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return operation(operand.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return operation(operand.evaluate(x));
    }

    @Override
    public String toString() {
        return String.format("(%s %s)", getSign(), operand);
    }

    @Override
    public String toMiniString() {
        if (operand instanceof AbstractBinaryOperation) {
            return String.format("%s (%s)", getSign(), operand.toMiniString());
        }
        return String.format("%s %s", getSign(), operand); //??? toMiniString
    }


    @Override
    public boolean equals(Object operand) {
        if (operand != null && operand.getClass() == this.getClass()) {
            AbstractUnaryOperation expression = (AbstractUnaryOperation) operand;

            return this.operand.equals(expression.operand);
        }
        return false;
    }


    public static final int PRIME = 8999;

    @Override
    public int hashCode() {
        return PRIME * (PRIME * operand.hashCode() + getClass().hashCode());
    }

}
