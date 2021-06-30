package expression.exceptions;


import expression.*;
import expression.parser.StringSource;

import java.util.HashSet;
import java.util.Set;

public class ExpressionParser implements Parser {
    private StringSource source;
    private char current;
    private int balanceBrackets;
    private int action; // -1 - nothing, 0 - const, 1 - variable, 2 - operation
    private final Set<Character> SIGNS = Set.of('+', '-', '*', '/', '(', ')', '&', '^', '|');


    private void nextChar() {
        current = source.hasNext() ? source.next() : '\0';
    }

    private void skipWhitespace() {
        while (Character.isWhitespace(current)) {
            nextChar();
        }
    }

    public CommonExpression parse(String expression) {
        source = new StringSource(expression);
        return parse(source);
    }

    public CommonExpression parse(StringSource expression) {
        nextChar();
        skipWhitespace();
        balanceBrackets = 0;
        action = -1;
        CommonExpression result = parseMinMax();
        if (balanceBrackets > 0) {
            throw new BracketException("Extra open parenthesis");
        }
        return result;
    }

    private boolean checkSymbol(char ch) {
        skipWhitespace();
        if (current == ch) {
            nextChar();
            return true;
        }
        return false;
    }

    private boolean checkOperation() {
        return !Character.isWhitespace(current) && current != '(' && current != '-';
    }

    private CommonExpression parseMinMax() {
        CommonExpression result = parseOr();
        while (true) {
            skipWhitespace();
            String var = parseVariable();
            if (var.equals("max")) {
                action = 0;
                if (checkOperation()) {
                    throw new IllegalOperationException("Invalid operation's format: max " + current);
                }
                result = new Max(result, parseOr());
            } else if (var.equals("min")) {
                action = 0;
                if (checkOperation()) {
                    throw new IllegalOperationException("Invalid operation's format: min " + current);
                }
                result = new Min(result, parseOr());
            } else if (!source.hasNext() || current == ')') {
                checkCloseBrackets();
                return result;
            } else {
                if (action == 0) {
                    throw new IllegalConstException("Invalid symbol in const: " + current + " on position: " + source.getPosition());
                } else {
                    throw new IllegalVariableException("Invalid symbol in variable: " + current + " on position: " + source.getPosition());
                }
            }
        }

    }

    private CommonExpression parseOr() {
        CommonExpression result = parseXor();
        while (true) {
            skipWhitespace();
            if (checkSymbol('|')) {
                action = 0;
                result = new Or(result, parseXor());
            } else {
                return result;
            }
        }
    }


    private CommonExpression parseXor() {
        CommonExpression result = parseAnd();
        while (true) {
            skipWhitespace();
            if (checkSymbol('^')) {
                action = 0;
                result = new Xor(result, parseAnd());
            } else {
                return result;
            }
        }
    }

    private CommonExpression parseAnd() {
        CommonExpression result = parseAdd();
        while (true) {
            skipWhitespace();
            if (checkSymbol('&')) {
                action = 0;
                result = new And(result, parseAdd());
            } else {
                return result;
            }
        }
    }

    private CommonExpression parseAdd() {
        CommonExpression result = parseMultiply();
        skipWhitespace();
        while (true) {
            if (checkSymbol('+')) {
                action = 0;
                result = new CheckedAdd(result, parseMultiply());
            } else if (checkSymbol('-')) {
                action = 0;
                result = new CheckedSubtract(result, parseMultiply());
            } else {
                return result;
            }
        }
    }

    private CommonExpression parseMultiply() {
        CommonExpression result = parsePrimary();
        skipWhitespace();
        while (true) {
            skipWhitespace();
            if (checkSymbol('*')) {
                action = 0;
                result = new CheckedMultiply(result, parsePrimary());
            } else if (checkSymbol('/')) {
                action = 0;
                result = new CheckedDivide(result, parsePrimary());
            } else {
                return result;
            }
        }
    }

    private boolean between(char a, char b) {
        return (current >= a && current <= b);
    }

    private Const parseConst(String prefix) {
        StringBuilder number = new StringBuilder(prefix);
        while (between('0', '9')) {
            number.append(current);
            nextChar();
        }
        return new Const(Integer.parseInt(number.toString()));
    }

    private String parseVariable() {
        skipWhitespace();
        StringBuilder var = new StringBuilder();
        while (between('a', 'z')) {
            var.append(current);
            nextChar();
        }
        return var.toString();
    }

    private void checkCloseBrackets() {
        if (checkSymbol(')')) {
            balanceBrackets--;
        }
        if (balanceBrackets < 0) {
            throw new BracketException("Unnecessary close bracket on position: " + source.getPosition());
        }
    }

    private CommonExpression parsePrimary() {
        CommonExpression result;
        skipWhitespace();
        if (checkSymbol('(')) {
            balanceBrackets++;
            result = parseMinMax();
        } else if (checkSymbol('-')) {
            action = 0;
            result = between('0', '9') ? parseConst("-") : new CheckedNegate(parsePrimary());
        } else if (between('0', '9')) {
            action = 0;
            result = parseConst("");
        } else if (between('a', 'z')) {
            String var = parseVariable();
            switch (var) {
                case "count":
                    action = 2;
                    result = new Count(parsePrimary());
                    break;
                case "abs":
                    if (checkOperation()) {
                        throw new IllegalOperationException("Invalid operation's format: abs " + current);
                    }
                    action = 0;
                    result = new CheckedAbs(parsePrimary());
                    break;
                case "sqrt":
                    if (checkOperation()) {
                        throw new IllegalOperationException("Invalid operation's format: sqrt " + current);
                    }
                    action = 0;
                    result = new CheckedSqrt(parsePrimary());
                    break;
                case "x":
                case "y":
                case "z":
                    action = 1;
                    if (current != '\0' && !Character.isWhitespace(current) && !SIGNS.contains(current)) {
                        throw new IllegalVariableException("Invalid symbol in variable: " + current + " on position: " + source.getPosition());
                    }
                    result = new Variable(var);
                    break;
                default:
                    if (action == 2) {
                        throw new IllegalVariableException("Invalid variable's name: " + var + " on position: " + source.getPosition());
                    } else {
                        throw new IllegalOperationException("Invalid operation: " + var + current + " on position: " + source.getPosition());
                    }
            }
        } else if (checkSymbol('~')) {
            action = 0;
            result = new CheckedNegate(parsePrimary());
        } else {
            if (action == 2) {
                throw new WaitingExpressionException("Waiting argument in expression before symbol: " + current + " on position: " + source.getPosition());
            } else {
                throw new WaitingExpressionException("Waiting operation in expression before symbol: " + current + " on position: " + source.getPosition());
            }
        }
        skipWhitespace();
        return result;
    }

}
