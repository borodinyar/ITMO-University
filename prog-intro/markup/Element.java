package markup;

public interface Element extends Markable {
    @Override
    void toMarkdown(StringBuilder stringBuilder);
    @Override
    void toTex(StringBuilder result);
}