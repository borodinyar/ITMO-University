package markup;

import java.util.List;

public abstract class AbstractList implements InList {

    private List<ListItem> list;

    protected AbstractList(List<ListItem> list) {
        this.list = list;
    }

    @Override
    public void toTex(StringBuilder sb) {
        sb.append(getTexBegin());
        for (ListItem x : list) {
            x.toTex(sb);
        }
        sb.append(getTexEnd());
    }

    public abstract String getTexBegin();
    public abstract String getTexEnd();
}
