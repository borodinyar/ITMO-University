package markup;

public interface Markable extends Texable {
    void toMarkdown(StringBuilder stringBuilder);
}