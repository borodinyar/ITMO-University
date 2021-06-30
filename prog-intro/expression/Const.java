package expression;

public class Const implements CommonExpression {

    private double x;

    public Const(double x) {
        this.x = x;
    }

    @Override
    public int evaluate(int x) {
        return (int) this.x;
    }

    @Override
    public double evaluate(double x) {
        return this.x;
    }

    @Override
    public String toString() {
        if ((int) x == x) {
            return Integer.toString((int) x);
        }
        return Double.toString(x);
    }

    @Override
    public int hashCode() {
        return Double.hashCode(x);
    }

    @Override
    public boolean equals(Object operand) {
        if (operand != null && operand.getClass() == Const.class) {
            return this.x == ((Const) operand).x;
        }
        return false;
    }



    @Override
    public Priorities getPriority() {
        return Priorities.HIGH;
    }

    @Override
    public boolean needBrackets() {
        return false;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return (int)this.x;
    }
}
