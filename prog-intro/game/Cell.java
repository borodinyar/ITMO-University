package game;

public enum Cell {
    X('X'),
    O('O'),
    D('-'),
    S('|'),
    E('.'),
    N(' ');

    final char symbol;

    Cell (final char symbol) {
        this.symbol = symbol;
    }
}
