package expression;

public class Variable implements CommonExpression {

    private String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public strictfp double evaluate(double x) {
        return x;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object operand) {
        if (operand != null && operand.getClass() == Variable.class) {
            return name.equals(operand.toString());
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
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
        if (name.equals("x")) {
            return x;
        } else if (name.equals("y")) {
            return y;
        } else {
            return z;
        }
    }
}
