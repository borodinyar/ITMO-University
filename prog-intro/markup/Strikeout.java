package markup;

import java.util.List;

public class Strikeout extends AbstractElement {

    public Strikeout(List<Element> list) {
        super(list);
    }

    @Override
    public String getTexBegin() {
        return "\\textst{";
    }

    @Override
    public String getTexEnd() {
        return "}";
    }

    @Override
    public String getMarkdown() {
        return "~";
    }
}
