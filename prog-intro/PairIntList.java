public class PairIntList {
    private IntList first, second;
    public int length;

    public PairIntList() {
        first = new IntList();
        second = new IntList();
        length = 0;
    }

    public PairIntList(int[] pair) {
        this();
        addPair(pair);
    }

    public int getFirst(int index) {
        return first.get(index);
    }

    public int getSecond(int index) {
        return second.get(index);
    }

    public void addPair(int[] pair) {
        first.add(pair[0]);
        second.add(pair[1]);
        ++length;
    }
}
