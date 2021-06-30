package game;

import java.util.Scanner;

public class IntegerReader {
    private final Scanner in;

    public IntegerReader(Scanner in) {
        this.in = in;
    }

    public IntegerReader(){
        this(new Scanner(System.in));
    }

    public int readInt() {
        while (!in.hasNextInt()) {
            in.next();
            System.out.println("Invalid input, please try again");
        }
        return in.nextInt();
    }

}
