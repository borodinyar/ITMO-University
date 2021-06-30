import java.util.*;
import java.io.*;

class WordStatCountLineIndexChecker implements Checker {
    @Override
    public boolean isWordCharacter(char symbol) {
        return Character.DASH_PUNCTUATION == Character.getType(symbol) ||
                Character.isLetter(symbol) ||
                symbol == '\'';
    }
}


public class WordStatCountLineIndex {
    private static WordStatCountLineIndexChecker checker = new WordStatCountLineIndexChecker();

    private static int wordCnt = 1;

    private static IntList add(IntList cur, int lines) {
        cur.add(lines);
        cur.add(wordCnt);
        return cur;
    }

    public static void main(String[] args) {
        Map<String, PairIntList> ans = new LinkedHashMap<>();
        try {
            Scanner input = new Scanner(args[0], "utf-8", checker);
            try {
                int lines = 1;
                while (!input.isEmpty()) {
                    wordCnt = 1;
                    while (!input.isEnd()) {
                        String word = input.next().toLowerCase();
                        PairIntList tmp =  ans.getOrDefault(word, new PairIntList());
                        tmp.addPair(new int[]{lines, wordCnt});
                        ans.put(word, tmp);
                        wordCnt++;
                    }
                    lines++;
                }
            } finally {
                input.close();
            }
        } catch (IOException e) {
            System.out.println("Error in input file:" + e.getMessage());
            return;
        }

        List<Map.Entry<String, PairIntList>> list = new ArrayList<>(ans.entrySet());
        ans.clear();
        list.sort(new Comparator<>() {
            @Override
            public int compare(Map.Entry<String, PairIntList> a, Map.Entry<String, PairIntList> b) {
                return a.getValue().length - b.getValue().length;
            }
        });

        try {
            Writer out = new OutputStreamWriter(new FileOutputStream(args[1]), "utf-8");
            try {
                for (Map.Entry<String, PairIntList> x : list) {
                    int len = x.getValue().length;
                    out.write(String.format("%s %s", x.getKey(), len));
                    for (int i = 0; i < len; ++i) {
                        out.write(String.format(" %s:%s", x.getValue().getFirst(i), x.getValue().getSecond(i)));
                    }
                    out.write(System.lineSeparator());
                }
            } finally {
                out.close();
            }
        } catch (IOException e) {
            System.out.println("Error in output file:" + e.getMessage());
        }
    }
}