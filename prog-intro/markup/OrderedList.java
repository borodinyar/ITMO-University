package markup;

import java.util.List;

public class OrderedList extends AbstractList {

    public OrderedList (List<ListItem> orderedList) {
        super(orderedList);
    }

    @Override
    public String getTexBegin() {
        return "\\begin{enumerate}";
    }

    @Override
    public String getTexEnd() {
        return "\\end{enumerate}";
    }
}
