package markup;

import java.util.List;

public abstract class AbstractElement implements Element {

    private List<Element> list;

    protected AbstractElement(List<Element> list) {
        this.list = list;
    }

    @Override
    public void toMarkdown(StringBuilder sb) {
        sb.append(getMarkdown());
        for (Element x : list) {
            x.toMarkdown(sb);
        }
        sb.append(getMarkdown());
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(getTexBegin());
        for (Element x : list) {
            x.toTex(sb);
        }
        sb.append(getTexEnd());
    }

    public abstract String getTexBegin();
    public abstract String getTexEnd();
    public abstract String getMarkdown();
}
