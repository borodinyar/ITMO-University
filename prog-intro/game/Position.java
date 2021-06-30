package game;

public interface Position {
    Cell get(int row, int col);

    int getRows();
    int getColumns();
    boolean isValid(Move move);
}
