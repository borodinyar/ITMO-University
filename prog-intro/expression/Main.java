package expression;

import java.util.Scanner;
import java.util.logging.SocketHandler;

import expression.parser.ExpressionParser;
import expression.parser.Parser;


public class Main {
    public static void main(String[] args) {

        final Scanner in = new Scanner(System.in);
        final String exp = in.nextLine();
        final ExpressionParser parser = new ExpressionParser();

        System.out.println(parser.parse(exp).evaluate(4.5));
        System.out.println(parser.parse(exp).toString());
        System.out.println(parser.parse(exp).toMiniString());
         /*System.out.println((new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"), new Variable("x")),
                        new Multiply(
                                new Const(2), new Variable("x"))),
                new Const(1))
        ).evaluate(Integer.parseInt(args[0])));*/


        //System.out.println((new Add(new Variable("x"), new Const(2))).equals(new Add(new Variable("x"), new Const(2))));
    }
}
