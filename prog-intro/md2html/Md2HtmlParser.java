package md2html;

import java.io.IOException;
import java.util.Map;

public class Md2HtmlParser {
    private final MarkdownSource source;
    private String currentParagraph;
    private int index;
    private static final String[] MARKUP_SEQUENCES = {"~", "**", "__", "--", "*", "_", "`"};

    private static final Map<Character, String> SPECIAL_SYMBOLS = Map.of(
            '<', "&lt;",
            '>', "&gt;",
            '&', "&amp;"
    );

    private static final Map<String, String> TAGS = Map.of(
            "</em>", "<em>",
            "</strong>", "<strong>",
            "</s>", "<s>",
            "</code>", "<code>",
            "</mark>", "<mark>",
            "</u>", "<u>"
    );

    private static final Map<String, String> CONVERT_TO_TAGS = Map.of(
            "__", "</strong>",
            "**", "</strong>",
            "_", "</em>",
            "*", "</em>",
            "--", "</s>",
            "`", "</code>",
            "~", "</mark>",
            "]", "",
            ")", ""
    );

    public Md2HtmlParser(final MarkdownSource source) {
        this.source = source;
    }

    public String parse() throws IOException {
        StringBuilder markdownParse = new StringBuilder();

        while (source.hasNextParagraph()) {
            currentParagraph = source.getParagraph();
            markdownParse.append(parseParagraph()).append('\n');
        }
        return markdownParse.toString();
    }

    private String parseParagraph() {
        index = 0;
        StringBuilder parsedParagraph = new StringBuilder();

        int cntHeadline = parsedHeadline();
        String tagName = "p";
        if (cntHeadline != 0) {
            tagName = "h" + cntHeadline;
        } else {
            parsedParagraph.append(currentParagraph, 0, index);
        }

        parsedParagraph.append(parseText(null, false));

        return String.format("<%s>%s</%s>", tagName, parsedParagraph.toString(), tagName);
    }

    private int parsedHeadline() {
        while (checkSymbol(index, '#')) {
            index++;
        }
        return Character.isWhitespace(currentParagraph.charAt(index)) ? index++ : 0;
    }


    private String parseText(String closeSequence, boolean isImage) {
        StringBuilder parsed = new StringBuilder();

        for (; index < currentParagraph.length(); index++) {
            if (index + 1 < currentParagraph.length() && checkSymbol(index, '\\') && !isImage) {
                parsed.append(getSpecialSymbolsFromMarkdown(currentParagraph.charAt(index + 1)));
            } else if (isFinishSequence(closeSequence)) {
                parsed.append(CONVERT_TO_TAGS.get(closeSequence));
                index += closeSequence.length();
                return parsed.toString();
            } else if (checkSymbol(index, '!')) {
                parsed.append(parseImage());
            } else if (getMarkup() != null && !isImage) {
                parsed.append(convertMarkup());
            } else {
                parsed.append(getSpecialSymbolsFromHtml(currentParagraph.charAt(index)));
            }
        }
        return parsed.toString();
    }

    private boolean isFinishSequence(String closeSequence) {
        if (closeSequence != null) {
            int lastIndex = index + closeSequence.length();
            return lastIndex <= currentParagraph.length()
                    && closeSequence.equals(currentParagraph.substring(index, lastIndex));
        }
        return false;
    }

    private String convertMarkup() {
        StringBuilder converted = new StringBuilder();

        String markupSequence = getMarkup();
        index += markupSequence != null ? markupSequence.length() : 0;

        String parsed = parseText(markupSequence, false);

        int indexFinishCloseSequence = parsed.length() - CONVERT_TO_TAGS.get(markupSequence).length();
        String openTag = "", closeTag = "";
        if (indexFinishCloseSequence != 0) {
            closeTag = parsed.substring(indexFinishCloseSequence);
            openTag = TAGS.getOrDefault(closeTag, markupSequence);
        }

        converted.append(openTag);

        index--;
        converted.append(parsed);
        return converted.toString();
    }

    private String getMarkup() {
        for (String sequences : MARKUP_SEQUENCES) {
            int lastIndex = index + sequences.length();
            if (lastIndex < currentParagraph.length() && sequences.equals(currentParagraph.substring(index, lastIndex))) {
                return sequences;
            }
        }
        return null;
    }


    private String getSpecialSymbolsFromHtml(char symbol) {
        return SPECIAL_SYMBOLS.getOrDefault(symbol, String.valueOf(symbol));
    }

    private String getSpecialSymbolsFromMarkdown(char current) {
        StringBuilder converted = new StringBuilder();

        if (current != '\\' && current != '_' && current != '*') {
            converted.append('\\');
        }

        converted.append(current);
        index++;
        return converted.toString();
    }

    private String parseImage() {
        int startIndex = index;
        index += 2; // "!" & "["
        int indexOpenSquareBracket = index - 1;

        String imageName = parseText("]", true);
        int indexCloseSquareBracket = index - 1;
        int indexOpenRoundBracket = index;
        index++; // "("

        String url = parseText(")", true);
        int indexCloseRoundBracket = index - 1;
        index--;
        if (imageName.length() == 0 || url.length() == 0
                || !checkSymbol(indexOpenRoundBracket, '(') || !checkSymbol(indexCloseRoundBracket, ')')
                || !checkSymbol(indexOpenSquareBracket, '[') || !checkSymbol(indexCloseSquareBracket, ']')) {
            index = startIndex;
            return "!";
        }
        // <img alt='картинок' src='http://www.ifmo.ru/images/menu/small/p10.jpg'>
        return String.format("<img alt='%s' src='%s'>", imageName, url);
    }

    private boolean checkSymbol(int index, char symbol) {
        return index < currentParagraph.length() && currentParagraph.charAt(index) == symbol;
    }
}

