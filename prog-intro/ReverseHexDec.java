import java.util.Arrays;
import java.io.IOException;

class ReverseChecker implements Checker {
	@Override
	public boolean isWordCharacter(char symbol) {
		return Character.DASH_PUNCTUATION == Character.getType(symbol) ||
				Character.isLetter(symbol) ||
				symbol == '\'' ||
				Character.isDigit(symbol) ||
				!(Character.isWhitespace(symbol));
	}
}

public class ReverseHexDec {
	private static ReverseChecker checker = new ReverseChecker();

	private static boolean hasNextInt(String wr){
		if (wr.isEmpty())
			return false;
		try {
			Long.parseLong(wr);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	private static int nextInt(String s) throws IOException {
		if (hasNextInt(s)) {
			return (int) Long.parseLong(s);
		}
		String x = s.toLowerCase();
		return (int) Long.parseLong(x.substring(2), 16);
	}

	public static void main(String[] args) {
		try {
			Scanner input = new Scanner(System.in, checker);
			try {
				int[] ans = new int[1];
				int[] cnt = new int[1];
				int n = 0;
				int lines = 0;
				while (!input.isEmpty()) {
					int k = 0;
					while (!input.isEnd()) {
						int x = nextInt(input.next());
						if (n >= ans.length) {
							ans = Arrays.copyOf(ans, ans.length * 2);
						}
						ans[n++] = x;
						++k;
					}
					if (lines >= cnt.length) {
						cnt = Arrays.copyOf(cnt, cnt.length * 2);
					}
					cnt[lines++] = k;
				}
				--n;
				for (int i = lines - 1; i >= 0; --i) {
					for (int j = cnt[i] - 1; j >= 0; --j) {
						System.out.print(ans[n--]);
						System.out.print(" ");
					}
					System.out.println();
				}
			} finally {
				input.close();
			}
		} catch (IOException e) {
			System.err.println("Input error: " + e.getMessage());
		}
	}
}