package expression.parser;

import expression.exceptions.FormatException;

public interface CharSource {
    boolean hasNext();
    char next();
    ParseException error(final String message);
    int getPosition();
}
