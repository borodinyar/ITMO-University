package markup;

public class Text implements Element {

    private String str;

    public Text(String str) {
        this.str = str;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(str);
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(str);
    }
}
