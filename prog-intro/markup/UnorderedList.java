package markup;

import java.util.List;

public class UnorderedList extends AbstractList{
    public UnorderedList(List<ListItem> list) {
        super(list);
    }

    @Override
    public String getTexBegin() {
        return "\\begin{itemize}";
    }

    @Override
    public String getTexEnd() {
        return "\\end{itemize}";
    }
}
