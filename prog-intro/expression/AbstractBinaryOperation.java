package expression;

public abstract class AbstractBinaryOperation implements CommonExpression {

    private CommonExpression firstOperation, secondOperation;

    public AbstractBinaryOperation(CommonExpression firstOperation, CommonExpression secondOperation) {
        this.firstOperation = firstOperation;
        this.secondOperation = secondOperation;
    }

    abstract protected int operation(int a, int b);

    protected double operation(double a, double b) {
        throw new UnsupportedOperationException("Cannot apply operation to double values");
    }

    abstract protected String getSign();

    @Override
    public int evaluate(int x, int y, int z) {
        return operation(firstOperation.evaluate(x, y, z), secondOperation.evaluate(x, y, z));
    }

    @Override
    public int evaluate(int x) {
        return operation(firstOperation.evaluate(x), secondOperation.evaluate(x));
    }

    @Override
    public double evaluate(double x) {
        return operation(firstOperation.evaluate(x), secondOperation.evaluate(x));
    }

    @Override
    public String toString() {
        return String.format("(%s %s %s)", firstOperation, getSign(), secondOperation);
    }


    private String takeBrackets(final boolean brackets, final CommonExpression currentOperand) {
        if (brackets) {
            return String.format("(%s)", currentOperand.toMiniString());
        } else {
            return currentOperand.toMiniString();
        }
    }

    private boolean checkOperand(final boolean isFirst) {
        if (isFirst) {
            return firstOperation.getPriority().compareTo(getPriority()) < 0;
        }

        if (secondOperation.getPriority() == Priorities.ADD && this.getPriority() == Priorities.ADD
                && secondOperation.needBrackets() && !this.needBrackets()) {
            return false;
        }

        return secondOperation.getPriority() == getPriority()
                && (this.needBrackets() || secondOperation.needBrackets())
                || secondOperation.getPriority().compareTo(getPriority()) < 0;
    }

    @Override
    public String toMiniString() {
        return String.format("%s %s %s",
                                takeBrackets(checkOperand(true), firstOperation),
                                getSign(),
                                takeBrackets(checkOperand(false), secondOperation)
                            );
    }

    @Override
    public boolean equals(Object operand) {
        if (operand != null && operand.getClass() == this.getClass()) {
            AbstractBinaryOperation expression = (AbstractBinaryOperation) operand;

            return this.firstOperation.equals(expression.firstOperation)
                    && this.secondOperation.equals(expression.secondOperation);
        }
        return false;
    }


    public static final int PRIME = 8999;

    @Override
    public int hashCode() {
        return PRIME * (PRIME * (PRIME * firstOperation.hashCode() + secondOperation.hashCode()) + getClass().hashCode());
    }
}
