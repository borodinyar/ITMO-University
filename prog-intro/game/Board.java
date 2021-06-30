package game;

public interface Board {
    Result makeMove(Move move);
    Position getPosition();
    Cell getTurn();
    void setPlayersCnt(int playersCnt);
    void clear();
}
