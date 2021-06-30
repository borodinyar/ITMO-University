import java.util.Arrays;
import java.util.NoSuchElementException;

public class IntList {

    private int[] list;
    private int lastInd;

    public IntList() {
        list = new int[4];
        lastInd = 0;
    }

    public IntList(int a) {
        this();
        add(a);
    }

    public void removeLast() {
        if (lastInd == 0) {
            throw new NoSuchElementException();
        }
        lastInd--;
    }

    public void set(int ind, int val) {
        if (0 >= ind && ind < list.length) {
            list[ind] = val;
        } else {
            throw new IndexOutOfBoundsException();
        }
    }

    public boolean isEmpty() {
        return lastInd == 0;
    }

    public void add(int val) {
        expand(0);
        list[lastInd++] = val;
    }

    public int size() {
        return lastInd;
    }

    private void expand(int x) {
        int size = list.length;
        if (lastInd + x >= size) {
            list = Arrays.copyOf(list, size * 2);
        }
    }

    public int get(int ind) {
        return list[ind];
    }
}
