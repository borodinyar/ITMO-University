package markup;

import java.util.List;

public class Strong extends AbstractElement {

    public Strong(List<Element> list) {
        super(list);
    }

    @Override
    public String getTexBegin() {
        return "\\textbf{";
    }

    @Override
    public String getTexEnd() {
        return "}";
    }

    @Override
    public String getMarkdown() {
        return "__";
    }
}
