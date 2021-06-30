import java.util.Scanner;

public class J {
    private static Scanner input;

    private static void solve() {

        int n = input.nextInt();
        int[][] a = new int[n][n];
        for (int i = 0; i < n; ++i) {
            String s = input.next();
            for (int j = 0; j < n; ++j) {
                a[i][j] = (s.charAt(j) - '0' + 10) % 10;
            }
        }

        for (int i = 0; i < n - 2; ++i) {
            for (int j = i + 1; j < n - 1; j++) {
                if (a[i][j] != 0) {
                    for (int k = j + 1; k < n; ++k) {
                        a[i][k] = (a[i][k] - a[j][k] + 10) % 10;
                    }
                }
            }
        }

        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                System.out.print(a[i][j]);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        input = new Scanner(System.in);
        solve();
    }
}
