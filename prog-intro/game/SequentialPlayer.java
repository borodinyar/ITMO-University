package game;

public class SequentialPlayer implements Player{
    @Override
    public Move makeMove(Position position, Cell cell) {
        for (int row = 0; row < position.getRows(); row++) {
            for (int col = 0; col < position.getColumns(); col++) {
                if (position.get(row, col) == Cell.E) {
                    final Move move = new Move(row, col, cell);
                    if (position.isValid(move)) {
                        return move;
                    }
                }
            }
        }
        throw new AssertionError("No empty cells");
    }

}
