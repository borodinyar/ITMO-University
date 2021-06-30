package markup;

import java.util.List;

public class ListItem implements Texable {
    private List<InList> list;

    public ListItem(List<InList> list) {
        this.list = list;
    }


    public void toTex(StringBuilder sb) {
        sb.append("\\item ");
        for (InList x : list) {
            x.toTex(sb);
        }
    }
}