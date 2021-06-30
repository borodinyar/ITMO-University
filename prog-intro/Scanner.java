import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.FileInputStream;
import java.io.File;

public class Scanner {
    //:NOTE: looks good
    final private Reader reader;
    private boolean hasSymbol;
    private char symbol;
    private boolean isFin;
    private Checker check;

    public Scanner(Reader r, Checker check) {
        this.check = check;
        reader = r;
        hasSymbol = false;
        isFin = false;
    }

    public Scanner(String input, String decoder, Checker check) throws IOException {
        this(new BufferedReader(new InputStreamReader(new FileInputStream(new File(input)), decoder)), check);
    }

    public Scanner(String string, Checker check) {
        this(new StringReader(string), check);
    }

    public Scanner(InputStream stream, Checker check) {
        this(new BufferedReader(new InputStreamReader(stream)), check);
    }

    public void close() throws IOException {
        reader.close();
    }

    private boolean hasRead() throws IOException {
        if (!hasSymbol) {
            int ch = reader.read();
            if (ch == -1) {
                symbol = 0;
                return false;
            }
            symbol = (char) ch;
            hasSymbol = true;
        }
        return true;
    }

    private boolean isNotSpace() throws IOException {
        return check.isWordCharacter(symbol);
    }

    public boolean isEmpty() throws IOException {
        skipWhitespace();
        return !hasRead();
    }

    public boolean isEnd() throws IOException {
        skipWhitespace();
        if (!hasRead()) {
            return true;
        }
        if (checkEnter()) {
            return false;
        }
        checkEnd();
        return true;
    }

    //:NOTE: do not need as a function
    private boolean checkEnter() {
        return symbol != '\n' && symbol != '\r';
    }

    private void skipWhitespace() throws IOException {
        while (hasRead() && !isNotSpace() && (isFin || checkEnter())) { // isFin == true => skip all except our symbols
            hasSymbol = false;                                          // isFin == false => skip line separators too
        }
        isFin = false;
    }

    public boolean hasNext() throws IOException {
        isFin = true;
        return !isEmpty();
    }

    private void checkEnd() throws IOException {
        char last = symbol;
        hasSymbol = false;
        hasRead();
        if (last == '\r' && symbol == '\n') {
            hasSymbol = false;
            hasRead();
        }
    }

    public String next() throws IOException {
        skipWhitespace();
        StringBuilder word = new StringBuilder();
        while (hasRead() && isNotSpace()) {
            word.append(symbol);
            hasSymbol = false;
        }
        return word.toString();
    }
}