package markup;

import java.util.List;

public class Paragraph implements InList, Markable {
    private List<Element> children;

    public Paragraph(List<Element> children) {
        this.children = children;
    }

    public void toMarkdown(StringBuilder sb) {
        for (Element child : children) {
            child.toMarkdown(sb);
        }
    }

    public void toTex(StringBuilder sb) {
        for (Element child : children) {
            child.toTex(sb);
        }
    }
}
