package markup;

import java.util.List;

public class Emphasis extends AbstractElement {
    public Emphasis(List<Element> list) {
        super(list);
    }

    @Override
    public String getTexBegin() {
        return "\\emph{";
    }

    @Override
    public String getTexEnd() {
        return "}";
    }

    @Override
    public String getMarkdown() {
        return "*";
    }
}
