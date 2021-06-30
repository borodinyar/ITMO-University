package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MarkdownSource {
    private final BufferedReader reader;
    private StringBuilder paragraph;
    private boolean used = true;
    private String line = "";

    public MarkdownSource(final BufferedReader reader) {
        this.reader = reader;
    }

    public String getParagraph() {
        used = true;
        return paragraph.toString();
    }

    public boolean hasNextParagraph() throws IOException {
        if (used) {
            nextParagraph();
            used = false;
        }
        return !paragraph.isEmpty();
    }

    private String nextLine() throws IOException {
        return line = reader.readLine();
    }

    private void skipEmptyLines() throws IOException {
        while (line != null && line.isEmpty()) {
            nextLine();
        }
    }

    private void nextParagraph() throws IOException {
        paragraph = new StringBuilder();
        skipEmptyLines();
        if (line == null) {
            return;
        }
        paragraph.append(line);
        while (nextLine() != null && !line.isEmpty()) {
            paragraph.append('\n').append(line);
        }

        skipEmptyLines();
    }

}
