package Capitolul_9;

import java.util.Arrays;

public class AlgoritmiProgramareDinamica {

    public static class LISResult {
        public int start;
        public int length;

        public LISResult(int start, int length) {
            this.start = start;
            this.length = length;
        }

        @Override
        public String toString() {
            return "Start index: " + start + ", lungime: " + length;
        }
    }

    public static LISResult longestIncreasingSubsequence(int[] A) {
        int n = A.length;
        if (n == 0) return new LISResult(0, 0);
        int currentStart = 0;
        int maxStart = 0;
        int maxLen = 1;
        int currentLen = 1;
        for (int i = 1; i < n; i++) {
            if (A[i] >= A[i - 1]) {
                currentLen++;
                if (currentLen > maxLen) {
                    maxStart = currentStart;
                    maxLen = currentLen;
                }
            } else {
                currentStart = i;
                currentLen = 1;
            }
        }
        return new LISResult(maxStart, maxLen);
    }

    public static double dist(double x1, double y1, double x2, double y2) {
        return Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
    }

    public static double weight(double[][] pts, int i, int j, int k) {
        double d1 = dist(pts[i][0], pts[i][1], pts[j][0], pts[j][1]);
        double d2 = dist(pts[j][0], pts[j][1], pts[k][0], pts[k][1]);
        double d3 = dist(pts[k][0], pts[k][1], pts[i][0], pts[i][1]);
        return d1 + d2 + d3;
    }

    public static double minWeightTriangulation(double[][] pts) {
        int n = pts.length;
        if (n < 3) return 0;
        double[][] MWT = new double[n][n];
        for (int gap = 0; gap < n; gap++) {
            for (int i = 0, j = gap; j < n; i++, j++) {
                if (j < i + 2) {
                    MWT[i][j] = 0.0;
                } else {
                    MWT[i][j] = Double.MAX_VALUE;
                    for (int k = i + 1; k < j; k++) {
                        double val = MWT[i][k] + MWT[k][j] + weight(pts, i, j, k);
                        if (val < MWT[i][j]) {
                            MWT[i][j] = val;
                        }
                    }
                }
            }
        }
        return MWT[0][n - 1];
    }

    public static double[][] serie(int n, double p) {
        double[][] P = new double[n + 1][n + 1];
        double q = 1.0 - p;
        for (int s = 1; s <= n; s++) {
            P[0][s] = 1.0;
            P[s][0] = 0.0;
            for (int k = 1; k < s; k++) {
                P[k][s - k] = p * P[k - 1][s - k] + q * P[k][s - k - 1];
            }
        }
        for (int s = 1; s <= n; s++) {
            for (int k = 0; k <= n - s; k++) {
                P[s + k][n - k] = p * P[s + k - 1][n - k] + q * P[s + k][n - k - 1];
            }
        }
        return P;
    }

    public static void matrixChainOrder(int[] p, int[][] m, int[][] s) {
        int n = p.length - 1;
        for (int i = 1; i <= n; i++) {
            m[i][i] = 0;
        }
        for (int l = 2; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                m[i][j] = Integer.MAX_VALUE;
                for (int k = i; k <= j - 1; k++) {
                    int q = m[i][k] + m[k + 1][j] + p[i - 1] * p[k] * p[j];
                    if (q < m[i][j]) {
                        m[i][j] = q;
                        s[i][j] = k;
                    }
                }
            }
        }
    }

    public static void lcsLength(String X, String Y, int[][] c, String[][] b) {
        int m = X.length();
        int n = Y.length();
        for (int i = 1; i <= m; i++) {
            c[i][0] = 0;
        }
        for (int j = 0; j <= n; j++) {
            c[0][j] = 0;
        }
        for (int i = 1; i <= m; i++) {
            for (int j = 1; j <= n; j++) {
                if (X.charAt(i - 1) == Y.charAt(j - 1)) {
                    c[i][j] = c[i - 1][j - 1] + 1;
                    b[i][j] = "↖";
                } else if (c[i - 1][j] >= c[i][j - 1]) {
                    c[i][j] = c[i - 1][j];
                    b[i][j] = "↑";
                } else {
                    c[i][j] = c[i][j - 1];
                    b[i][j] = "←";
                }
            }
        }
    }

    public static String getLCS(String[][] b, String X, int i, int j) {
        StringBuilder sb = new StringBuilder();
        buildLCSString(b, X, i, j, sb);
        return sb.toString();
    }

    private static void buildLCSString(String[][] b, String X, int i, int j, StringBuilder sb) {
        if (i == 0 || j == 0) {
            return;
        }
        if ("↖".equals(b[i][j])) {
            buildLCSString(b, X, i - 1, j - 1, sb);
            sb.append(X.charAt(i - 1));
        } else if ("↑".equals(b[i][j])) {
            buildLCSString(b, X, i - 1, j, sb);
        } else {
            buildLCSString(b, X, i, j - 1, sb);
        }
    }

    public static void optimalBST(double[] p, double[][] Cost, int[][] root) {
        int n = p.length - 1;
        for (int i = 1; i <= n + 1; i++) {
            Cost[i][i - 1] = 0.0;
        }
        for (int l = 1; l <= n; l++) {
            for (int i = 1; i <= n - l + 1; i++) {
                int j = i + l - 1;
                Cost[i][j] = Double.MAX_VALUE;
                double sum = 0.0;
                for (int k = i; k <= j; k++) {
                    sum += p[k];
                }
                for (int r = i; r <= j; r++) {
                    double val = Cost[i][r - 1] + Cost[r + 1][j] + sum;
                    if (val < Cost[i][j]) {
                        Cost[i][j] = val;
                        root[i][j] = r;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("--- Testare LIS (Subsecventa Continua Maxima) ---");
        int[] A = {2, 5, 4, 8, 10, 7, 12};
        System.out.println("Tablou initial: " + Arrays.toString(A));
        LISResult lis = longestIncreasingSubsequence(A);
        System.out.println("Rezultat:       " + lis);
        System.out.print("Elemente LIS:   ");
        for (int i = lis.start; i < lis.start + lis.length; i++) {
            System.out.print(A[i] + " ");
        }
        System.out.println();

        System.out.println("\n--- Testare Triangulare Convex-Poligon ---");
        double[][] vertices = {
            {0, 0},
            {1, 0},
            {2, 1},
            {1.5, 2},
            {0.5, 2},
            {-0.5, 1}
        };
        double cost = minWeightTriangulation(vertices);
        System.out.printf("Ponderea minima totala a triangularii: %.4f\n", cost);

        System.out.println("\n--- Testare Competitie (serie) ---");
        int nCompetitie = 3;
        double probA = 0.6;
        double[][] P = serie(nCompetitie, probA);
        System.out.printf("Probabilitatea ca A sa castige competitia (serie): %.4f\n", P[nCompetitie][nCompetitie]);

        System.out.println("\n--- Testare Inmultire Matrice in Lant ---");
        int[] dims = {10, 100, 5, 50};
        int nMatrice = dims.length - 1;
        int[][] m = new int[nMatrice + 1][nMatrice + 1];
        int[][] s = new int[nMatrice + 1][nMatrice + 1];
        matrixChainOrder(dims, m, s);
        System.out.println("Costul minim de inmultire: " + m[1][nMatrice]);

        System.out.println("\n--- Testare Cea mai Lunga Subsecventa Comuna (LCS) ---");
        String X = "ABCBDAB";
        String Y = "BDCABA";
        int[][] c = new int[X.length() + 1][Y.length() + 1];
        String[][] b = new String[X.length() + 1][Y.length() + 1];
        lcsLength(X, Y, c, b);
        System.out.println("Lungime LCS: " + c[X.length()][Y.length()]);
        System.out.println("LCS:         " + getLCS(b, X, X.length(), Y.length()));

        System.out.println("\n--- Testare Arbore Binar de Cautare Optim (OBST) ---");
        double[] probs = {0.0, 0.2, 0.5, 0.3};
        int nKeys = probs.length - 1;
        double[][] CostBST = new double[nKeys + 2][nKeys + 1];
        int[][] rootBST = new int[nKeys + 2][nKeys + 1];
        optimalBST(probs, CostBST, rootBST);
        System.out.printf("Costul minim al OBST: %.4f\n", CostBST[1][nKeys]);
        System.out.println("Radacina radacinii optime: K" + rootBST[1][nKeys]);
    }
}
