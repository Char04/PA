package Capitolul_5;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AlgoritmiGreedy {

    public static class Edge implements Comparable<Edge> {
        public int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        @Override
        public int compareTo(Edge o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static int[] dijkstra(int[][] L, int n) {
        int[] D = new int[n + 1];
        boolean[] S = new boolean[n + 1];
        for (int i = 2; i <= n; i++) {
            D[i] = L[1][i];
        }
        S[1] = true;
        for (int i = 3; i <= n; i++) {
            int minVal = Integer.MAX_VALUE;
            int v = -1;
            for (int j = 2; j <= n; j++) {
                if (!S[j] && D[j] < minVal) {
                    minVal = D[j];
                    v = j;
                }
            }
            if (v == -1) break;
            S[v] = true;
            for (int w = 2; w <= n; w++) {
                if (!S[w] && L[v][w] != Integer.MAX_VALUE) {
                    if (D[v] + L[v][w] < D[w]) {
                        D[w] = D[v] + L[v][w];
                    }
                }
            }
        }
        return D;
    }

    public static int find(int[] set, int x) {
        return set[x];
    }

    public static void merge(int[] set, int a, int b, int n) {
        int i = a;
        int j = b;
        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }
        for (int k = 1; k <= n; k++) {
            if (set[k] == j) {
                set[k] = i;
            }
        }
    }

    public static List<Edge> kruskal(List<Edge> edges, int n) {
        Collections.sort(edges);
        int[] set = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            set[i] = i;
        }
        List<Edge> A = new ArrayList<>();
        for (Edge edge : edges) {
            int ucomp = find(set, edge.u);
            int vcomp = find(set, edge.v);
            if (ucomp != vcomp) {
                merge(set, ucomp, vcomp, n);
                A.add(edge);
                if (A.size() == n - 1) break;
            }
        }
        return A;
    }

    public static void prim(int[][] C, int n, List<Edge> MST) {
        int[] vecin = new int[n + 1];
        int[] mincost = new int[n + 1];
        for (int i = 2; i <= n; i++) {
            vecin[i] = 1;
            mincost[i] = C[i][1];
        }
        for (int step = 1; step <= n - 1; step++) {
            int min = Integer.MAX_VALUE;
            int k = -1;
            for (int j = 2; j <= n; j++) {
                if (mincost[j] != -1 && mincost[j] < min) {
                    min = mincost[j];
                    k = j;
                }
            }
            if (k == -1) break;
            MST.add(new Edge(k, vecin[k], min));
            mincost[k] = -1;
            for (int j = 2; j <= n; j++) {
                if (mincost[j] != -1 && C[j][k] < mincost[j]) {
                    mincost[j] = C[j][k];
                    vecin[j] = k;
                }
            }
        }
    }


    public static class Spectacol implements Comparable<Spectacol> {
        public int id;
        public int start;
        public int end;

        public Spectacol(int id, int start, int end) {
            this.id = id;
            this.start = start;
            this.end = end;
        }

        @Override
        public int compareTo(Spectacol o) {
            return Integer.compare(this.end, o.end);
        }
    }

    public static List<Spectacol> selectSpectacole(List<Spectacol> spectacole) {
        Collections.sort(spectacole);
        List<Spectacol> selectate = new ArrayList<>();
        if (spectacole.isEmpty()) return selectate;

        Spectacol ultimul = spectacole.get(0);
        selectate.add(ultimul);

        for (int i = 1; i < spectacole.size(); i++) {
            Spectacol curent = spectacole.get(i);
            if (curent.start >= ultimul.end) {
                selectate.add(curent);
                ultimul = curent;
            }
        }
        return selectate;
    }

    public static void main(String[] args) {
        int nDijk = 5;
        int[][] L = new int[nDijk + 1][nDijk + 1];
        for (int i = 1; i <= nDijk; i++) {
            for (int j = 1; j <= nDijk; j++) {
                if (i == j) L[i][j] = 0;
                else L[i][j] = Integer.MAX_VALUE;
            }
        }
        L[1][2] = 1; L[1][3] = Integer.MAX_VALUE; L[1][4] = Integer.MAX_VALUE; L[1][5] = Integer.MAX_VALUE;
        L[2][3] = 4; L[2][5] = 1;
        L[3][4] = 3;
        L[5][4] = 2;

        int[] dist = dijkstra(L, nDijk);
        System.out.println("Dijkstra - Distante minime de la nodul 1:");
        for (int i = 2; i <= nDijk; i++) {
            System.out.println("Nod " + i + ": " + (dist[i] == Integer.MAX_VALUE ? "INF" : dist[i]));
        }

        int nGreedy = 7;
        List<Edge> edges = new ArrayList<>();
        edges.add(new Edge(1, 2, 1));
        edges.add(new Edge(2, 3, 2));
        edges.add(new Edge(1, 4, 4));
        edges.add(new Edge(2, 4, 6));
        edges.add(new Edge(2, 5, 5));
        edges.add(new Edge(3, 5, 8));
        edges.add(new Edge(3, 6, 6));
        edges.add(new Edge(4, 5, 3));
        edges.add(new Edge(4, 7, 4));
        edges.add(new Edge(5, 6, 3));
        edges.add(new Edge(5, 7, 7));

        List<Edge> mstKruskal = kruskal(new ArrayList<>(edges), nGreedy);
        System.out.println("Kruskal MST Muchii:");
        int costK = 0;
        for (Edge e : mstKruskal) {
            System.out.println(e.u + " - " + e.v + " (cost: " + e.weight + ")");
            costK += e.weight;
        }
        System.out.println("Cost total MST Kruskal: " + costK);

        int[][] C = new int[nGreedy + 1][nGreedy + 1];
        for (int i = 1; i <= nGreedy; i++) {
            for (int j = 1; j <= nGreedy; j++) {
                if (i == j) C[i][j] = 0;
                else C[i][j] = Integer.MAX_VALUE;
            }
        }
        for (Edge e : edges) {
            C[e.u][e.v] = e.weight;
            C[e.v][e.u] = e.weight;
        }

        List<Edge> mstPrim = new ArrayList<>();
        prim(C, nGreedy, mstPrim);
        System.out.println("Prim MST Muchii:");
        int costP = 0;
        for (Edge e : mstPrim) {
            System.out.println(e.u + " - " + e.v + " (cost: " + e.weight + ")");
            costP += e.weight;
        }
        System.out.println("Cost total MST Prim: " + costP);

        System.out.println("Activity Selection Test:");
        List<Spectacol> specList = new ArrayList<>();
        specList.add(new Spectacol(1, 10, 14));
        specList.add(new Spectacol(2, 12, 15));
        specList.add(new Spectacol(3, 14, 16));
        specList.add(new Spectacol(4, 11, 13));
        specList.add(new Spectacol(5, 15, 17));

        List<Spectacol> selected = selectSpectacole(specList);
        for (Spectacol s : selected) {
            System.out.println("Selected Activity " + s.id + ": [" + s.start + ", " + s.end + ")");
        }
    }
}
