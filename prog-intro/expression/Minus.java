package expression;

public class Minus extends AbstractUnaryOperation {

    public Minus(CommonExpression operand) {
        super(operand);
    }

    @Override
    protected int operation(int a) {
        return -a;
    }

    @Override
    protected double operation(double a) {
        return -a;
    }

    @Override
    protected String getSign() {
        return "-";
    }
}