package game;

import java.util.Random;

public class RandomPlayer implements Player {
    private final Random random = new Random();

    @Override
    public Move makeMove(final Position position, final Cell cell) {
        Move move;
        do {
            move = new Move(random.nextInt(position.getRows()), random.nextInt(position.getColumns()), cell);
        } while (!position.isValid(move));
        return move;
    }
}
