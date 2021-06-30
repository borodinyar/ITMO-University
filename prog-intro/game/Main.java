package game;

import java.util.List;

public class Main {
    private static final IntegerReader in = new IntegerReader();

    public static void main(String[] args) {
        final Game game = new Game(List.of(new RandomPlayer(), new HumanPlayer(),  new SequentialPlayer(), new RandomPlayer()), true);
        System.out.println("Enter the length of the rhombus diagonal");
        int m = in.readInt();
        System.out.println("Enter number of cells in a row to win (k)");
        int k = in.readInt();
        System.out.println("Game result: " + game.play(new RhombusBoard(m, k)));
    }
}
