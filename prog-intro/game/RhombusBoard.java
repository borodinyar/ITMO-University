package game;

public class RhombusBoard extends MnkBoard{

    public RhombusBoard(final int side, final int k) {
        super(side, side, k);
        //fill the board
        for (int r = 0; r < side; r++) {
            for (int c = 0; c < side; c++) {
                if (isRhombusBuild(r, c, side)) {
                    cells[r][c] = Cell.E;
                } else {
                    emptyCells--;
                    cells[r][c] = Cell.N;
                }
            }
        }
    }

    private boolean isRhombusBuild(int r, int c, final int side) {
        int halfCntSide = side / 2;
        int left = halfCntSide - r;
        int right = halfCntSide + r;

        if (r >= halfCntSide) {
            r = side - r - 1;
            left = halfCntSide - r;
            right = halfCntSide + r;
        }

        left = side % 2 == 0 ? left - 1: left;

        return left <= c && c <= right;
    }
}
