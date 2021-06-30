package game;

import java.util.List;

public class Game {
    private final List<Player> players;
    private final boolean enableLogging;
    private final boolean[] lost;

    public Game(final List<Player> players, final boolean enableLogging) {
        this.players = players;
        this.lost = new boolean[players.size()];
        this.enableLogging = enableLogging;
    }

    public int play(final Board board) {
        board.setPlayersCnt(players.size());
        int result;
        int currentPlayer = 0;
        while (true) {
            result = makeMove(board, players.get(currentPlayer), currentPlayer + 1);
            if (result >= 0) {
                break;
            }

            do {
                currentPlayer = (currentPlayer + 1) % players.size();
            } while (lost[currentPlayer]);

        }
        System.out.println("Final position:");
        System.out.println(board.getPosition());
        if (result == 0) {
            System.out.println("Game result: draw");
        } else {
            System.out.println("Game result: player " + result + " won");
        }
        return result;
    }

    private int makeMove(final Board board, final Player player, final int no) {
        if (enableLogging) {
            System.out.println(board.getPosition());
        }
        final Move move = player.makeMove(board.getPosition(), board.getTurn());

        if (enableLogging) {
            System.out.println("Move: " + move);
        }
        final Result result = board.makeMove(move);
        if (result == Result.WIN) {
            return no;
        } else if (result == Result.LOSE) {
            lost[no] = true;
            int no2 = -1;
            for (int i = 0; i < lost.length; i++) {
                if (!lost[i]) {
                    if (no2 != -1) {
                        return -1;
                    }
                    no2 = i;
                }
            }
            return no2 != -1 ? no2 : 0;
        } else if (result == Result.DRAW) {
            return 0;
        } else if (result != Result.UNKNOWN) {
            throw new AssertionError("Unknown result type " + result);
        }
        return -1;
    }
}
