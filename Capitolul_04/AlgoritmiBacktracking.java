package Capitolul_04;

public class AlgoritmiBacktracking {

    public static void permuta(int[] s, boolean[] visited, int k, int n) {
        if (k == n) {
            printArray(s);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                s[k] = i;
                permuta(s, visited, k + 1, n);
                visited[i] = false;
            }
        }
    }

    public static void aranjeaza(int[] s, boolean[] visited, int k, int n, int p) {
        if (k == p) {
            printArray(s);
            return;
        }
        for (int i = 1; i <= n; i++) {
            if (!visited[i]) {
                visited[i] = true;
                s[k] = i;
                aranjeaza(s, visited, k + 1, n, p);
                visited[i] = false;
            }
        }
    }

    public static void combina(int[] s, int k, int n, int p, int start) {
        if (k == p) {
            printArray(s);
            return;
        }
        for (int i = start; i <= n; i++) {
            s[k] = i;
            combina(s, k + 1, n, p, i + 1);
        }
    }

    public static void paranteze(int[] s, int k, int n, int ps, int pd) {
        if (k == n) {
            if (ps == pd) {
                printBrackets(s);
            }
            return;
        }
        if (ps < n / 2) {
            s[k] = 1;
            paranteze(s, k + 1, n, ps + 1, pd);
        }
        if (pd < ps) {
            s[k] = 2;
            paranteze(s, k + 1, n, ps, pd + 1);
        }
    }

    public static boolean valid(int[] s, int k) {
        for (int i = 1; i < k; i++) {
            if (s[i] == s[k] || Math.abs(k - i) == Math.abs(s[k] - s[i])) {
                return false;
            }
        }
        return true;
    }

    public static void dame(int[] s, int k, int n) {
        if (k == n + 1) {
            printQueens(s, n);
            return;
        }
        for (int i = 1; i <= n; i++) {
            s[k] = i;
            if (valid(s, k)) {
                dame(s, k + 1, n);
            }
        }
    }

    public static void main(String[] args) {
        int nPerm = 3;
        System.out.println("Permutari de " + nPerm + ":");
        permuta(new int[nPerm], new boolean[nPerm + 1], 0, nPerm);

        int nAr = 3, pAr = 2;
        System.out.println("Aranjamente de " + nAr + " luate cate " + pAr + ":");
        aranjeaza(new int[pAr], new boolean[nAr + 1], 0, nAr, pAr);

        int nComb = 4, pComb = 2;
        System.out.println("Combinari de " + nComb + " luate cate " + pComb + ":");
        combina(new int[pComb], 0, nComb, pComb, 1);

        int nBrackets = 6;
        System.out.println("Paranteze corect inchise de lungime " + nBrackets + ":");
        paranteze(new int[nBrackets], 0, nBrackets, 0, 0);

        int nQueens = 4;
        System.out.println("Solutii problema damelor pe tabla " + nQueens + "x" + nQueens + ":");
        dame(new int[nQueens + 1], 1, nQueens);
    }

    private static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + " ");
        }
        System.out.println();
    }

    private static void printBrackets(int[] arr) {
        for (int val : arr) {
            System.out.print(val == 1 ? "(" : ")");
        }
        System.out.println();
    }

    private static void printQueens(int[] arr, int n) {
        for (int i = 1; i <= n; i++) {
            System.out.print(arr[i] + " ");
        }
        System.out.println();
    }
}
