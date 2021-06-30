package expression.parser;

import expression.*;
import expression.exceptions.IllegalOperationException;
import expression.exceptions.IllegalVariableException;

import static expression.Priorities.*;

public class ExpressionParser implements Parser {
    private StringSource source;
    private char current;

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
        return parseOr();
    }


    private boolean check(char ch) {
        skipWhitespace();
        if (current == ch) {
            nextChar();
            return true;
        }
        return false;
    }

    private CommonExpression parseOr() {
        CommonExpression result = parseXor();
        while (true) {
            skipWhitespace();
            if (check('|')) {
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
            if (check('^')) {
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
            if (check('&')) {
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
            if (check('+')) {
                result = new Add(result, parseMultiply());
            } else if (check('-')) {
                result = new Subtract(result, parseMultiply());
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
            if (check('*')) {
                result = new Multiply(result, parsePrimary());
            } else if (check('/')) {
                result = new Divide(result, parsePrimary());
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

    private CommonExpression parsePrimary() {
        CommonExpression result;
        skipWhitespace();
        if (check('(')) {
            result = parseOr();
            check(')');
        } else if (check('-')) {
            result = between('0', '9') ? parseConst("-") : new Minus(parsePrimary());
        } else if (between('0', '9')) {
            result = parseConst("");
        } else if (between('a', 'z')) {
            String var = parseVariable();

            switch (var) {
                case "count":
                    result = new Count(parsePrimary());
                    break;
                case "x":
                case "y":
                case "z":
                    result = new Variable(var);
                    break;
                default:
                    throw new ParseException("Invalid operation");
            }
        } else if (check('~')) {
            result = new Not(parsePrimary());
        } else {
            throw new ParseException("No argument");
        }
        skipWhitespace();
        return result;
    }

}
