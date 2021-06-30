package game;

public class HumanPlayer implements Player{
    private final IntegerReader in;

    public HumanPlayer(IntegerReader in) {
        this.in = in;
    }

    public HumanPlayer(){
        this(new IntegerReader());
    }


    @Override
    public Move makeMove(Position position, Cell cell) {
        while (true) {
            System.out.println("Current position: ");
            System.out.println(position.toString());
            System.out.println("Enter " + cell + "'s move");
            System.out.println("Row: ");
            int row = in.readInt();
            System.out.println("Col: ");
            int col = in.readInt();
            final Move move = new Move(row, col, cell);
            if (position.isValid(move)) {
                return move;
            }
            System.out.println("Invalid move: " + row + " column " + col);
        }
    }
}
