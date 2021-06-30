package expression.exceptions;

import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        final var parser = new ExpressionParser();
        TripleExpression expression;

        try{
            expression = parser.parse("x*y+(z-1   )/+10");
        } catch (final ParseException e) {
            System.out.println("Unable to parse expression" + e.getMessage());
            return;
        }

        try {
            System.out.println(expression.evaluate(0, 0, 0));
        } catch (final ArithmeticException e) {
            System.out.println(e.getMessage());
        }

        /*try{
            expression = parser.parse("1000000*x*x*x*x*x/(x-1)");
        } catch (final ParseException e) {
            System.out.println("Unable to parse expression" + e.getMessage());
            return;
        }
        System.out.print("x\tf");

        for (int i = 0; i <= 10; ++i) {
            System.out.printf("%d\t", i);
            try {
                System.out.println(expression.evaluate(i, 0, 0));
            } catch (final ArithmeticException e) {
                System.out.println(e.getMessage());
            }
        }*/
    }
}
