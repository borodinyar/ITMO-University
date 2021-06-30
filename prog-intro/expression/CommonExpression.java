package expression;

public interface CommonExpression extends Expression, DoubleExpression, TripleExpression {
    Priorities getPriority();

    boolean needBrackets();
}
