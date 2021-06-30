package game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MnkBoard implements Board {

    private class VirtualPosition implements Position {
        
        @Override
        public Cell get(int row, int col) {
            return cells[row][col];
        }

        @Override   
        public int getRows() {
            return rows;
        }

        @Override
        public int getColumns() {
            return columns;
        }


        @Override
        public boolean isValid(Move move) {
            return 0 <= move.getRow() && move.getRow() < rows
                    && 0 <= move.getCol() && move.getCol() < columns
                    && get(move.getRow(), move.getCol()) == Cell.E;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("\\");
            int add = cntAddR(0);
            sb.append(" ".repeat(add + 1));
            int addIfRow = cntAddC(rows);
            for (int c = 0; c < columns; c++) {
                sb.append(c);
                sb.append(" ".repeat(addIfRow - cntAddC(c) + 1));
            }
            for (int r = 0; r < rows; r++) {
                sb.append("\n");
                sb.append(r);
                sb.append(" ".repeat(cntAddR(r) + 1));
                for (int c = 0; c < columns; c++) {
                    sb.append(get(r, c).symbol);
                    sb.append(" ".repeat(addIfRow));
                }
            }
            return sb.toString();
        }

        private int cntAddC(int c) {
            int x = 1;
            int res = 0;
            while (c / x > 0) {
                x *= 10;
                res++;
            }
            return (c != 0) ? res : 1;
        }

        private int cntAddR(int r) {
            int cntN = 0, cntR = 0;
            int x = 1;
            while (columns / x > 0) {
                if (r / x > 0) {
                    cntR++;
                }
                x *= 10;
                cntN++;
            }
            return (r != 0) ?  cntN - cntR : cntN - cntR - 1 ;
        }
    }

    private static final int[][] ORIENTATIONS = {
            {1, 1},  // Main diagonal
            {1, -1}, // Antidiagonal
            {0, 1},  // Vertically
            {1, 0}   // Horizontal
    };

    private final int rows;
    private final int columns;
    private final int k;
    private Position position;
    protected int emptyCells;
    protected Cell[][] cells;
    private Cell turn;
    private int playersCnt;
    private Set<Cell> used = new HashSet<>();

    public MnkBoard(int rows, int columns, int k) {

        if (k > rows && k > columns) {
            throw new IllegalArgumentException("Unreal game. \'k\' has to be less or equals m or n");
        }

        if (rows <= 0 || columns <= 0 || k <= 0) {
            throw new IllegalArgumentException("Values must be positive numbers");
        }
        this.rows = rows;
        this.columns = columns;
        this.k = k;
        this.cells = new Cell[rows][columns];
        clear();
    }

    @Override
    public void setPlayersCnt(int playersCnt) {
        if (4 < playersCnt || playersCnt < 2) {
            throw new IllegalArgumentException("The number of players must be from 2 to 4");
        }
        this.playersCnt = playersCnt;
    }

    @Override
    public void clear() {
        position = new VirtualPosition();

        emptyCells = columns * rows;
        this.turn = Cell.X;
        this.cells = new Cell[rows][columns];

        for (Cell[] arrayCell : cells) {
            Arrays.fill(arrayCell, Cell.E);
        }

        used.clear();
    }

    public Cell getTurn() {
        return turn;
    }

    private boolean isWin(final Move move) {
        for (int[] orientaton : ORIENTATIONS) {
            if (k < lengthSequence(move, orientaton[0], orientaton[1]) + lengthSequence(move, -orientaton[0], -orientaton[1])) {
                return true;
            }
        }
        return false;
    }

    private int lengthSequence(final Move move, final int stepRow, final int stepCol) {
        final Cell currentCell = move.getValue();
        int cnt = 0;
        int r = move.getRow();
        int c = move.getCol();
        while (r >= 0 && r < rows && c >= 0 && c < columns && currentCell == cells[r][c]) {
            cnt++;
            r += stepRow;
            c += stepCol;
        }
        return cnt;
    }

    private Cell nextTurn() {
        if (turn == Cell.X) {
            return Cell.O;
        } else if (turn == Cell.O && playersCnt >= 3) {
            return Cell.D;
        } else if (turn == Cell.D && playersCnt == 4) {
            return Cell.S;
        }
        return Cell.X;
    }

    public Result makeMove(final Move move) {
        while (used.contains(turn)) {
            turn = nextTurn();
        }
        if (!position.isValid(move)) {
            used.add(turn);
            return Result.LOSE;
        }

        cells[move.getRow()][move.getCol()] = move.getValue();
        emptyCells--;

        if (isWin(move)) {
            return Result.WIN;
        }

        if (emptyCells == 0) {
            return Result.DRAW;
        }

        turn = nextTurn();
        return Result.UNKNOWN;
    }

    @Override
    public Position getPosition() {
        return position;
    }

}


