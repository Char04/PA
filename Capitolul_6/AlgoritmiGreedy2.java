package Capitolul_6;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AlgoritmiGreedy2 {

    public static class MinHeapNode implements Comparable<MinHeapNode> {
        public int weight;
        public int index;
        public char ch;
        public MinHeapNode left;
        public MinHeapNode right;

        public MinHeapNode(int weight, int index) {
            this.weight = weight;
            this.index = index;
            this.ch = '\0';
            this.left = null;
            this.right = null;
        }

        public MinHeapNode(int weight, char ch) {
            this.weight = weight;
            this.index = -1;
            this.ch = ch;
            this.left = null;
            this.right = null;
        }

        @Override
        public int compareTo(MinHeapNode o) {
            return Integer.compare(this.weight, o.weight);
        }
    }

    public static void siftDown(int[] T, int n, int i) {
        int k = i;
        while (true) {
            int j = k;
            int leftChild = 2 * j;
            int rightChild = 2 * j + 1;
            if (leftChild <= n && T[leftChild] > T[k]) {
                k = leftChild;
            }
            if (rightChild <= n && T[rightChild] > T[k]) {
                k = rightChild;
            }
            if (j == k) {
                break;
            }
            int temp = T[j];
            T[j] = T[k];
            T[k] = temp;
        }
    }

    public static void percolate(int[] T, int i) {
        int k = i;
        while (k > 1) {
            int parent = k / 2;
            if (T[parent] < T[k]) {
                int temp = T[parent];
                T[parent] = T[k];
                T[k] = temp;
                k = parent;
            } else {
                break;
            }
        }
    }

    public static void slowMakeHeap(int[] T, int n) {
        for (int i = 2; i <= n; i++) {
            percolate(T, i);
        }
    }

    public static void makeHeap(int[] T, int n) {
        for (int i = n / 2; i >= 1; i--) {
            siftDown(T, n, i);
        }
    }

    public static void alterHeap(int[] T, int n, int i, int v) {
        int oldVal = T[i];
        T[i] = v;
        if (v < oldVal) {
            siftDown(T, n, i);
        } else {
            percolate(T, i);
        }
    }

    public static int insert(int[] T, int n, int v) {
        T[n + 1] = v;
        percolate(T, n + 1);
        return n + 1;
    }

    public static MinHeapNode interopt(int[] Q, int n) {
        PriorityQueue<MinHeapNode> pq = new PriorityQueue<>();
        MinHeapNode[] nodes = new MinHeapNode[2 * n];
        for (int i = 1; i <= n; i++) {
            nodes[i] = new MinHeapNode(Q[i], i);
            pq.add(nodes[i]);
        }
        int nextNodeIndex = n + 1;
        for (int i = n + 1; i <= 2 * n - 1; i++) {
            MinHeapNode leftNode = pq.poll();
            MinHeapNode rightNode = pq.poll();
            MinHeapNode parentNode = new MinHeapNode(leftNode.weight + rightNode.weight, nextNodeIndex++);
            parentNode.left = leftNode;
            parentNode.right = rightNode;
            pq.add(parentNode);
            nodes[i] = parentNode;
        }
        return pq.poll();
    }

    private static void printTree(MinHeapNode root, String indent) {
        if (root != null) {
            System.out.println(indent + "Nod " + root.index + " (pondere: " + root.weight + ")");
            printTree(root.left, indent + "  Stang -> ");
            printTree(root.right, indent + "  Drept -> ");
        }
    }

    public static void heapsort(int[] A) {
        int n = A.length - 1;
        makeHeap(A, n);
        for (int i = n; i >= 2; i--) {
            int temp = A[1];
            A[1] = A[i];
            A[i] = temp;
            siftDown(A, i - 1, 1);
        }
    }

    public static MinHeapNode huffman(char[] C, int[] f) {
        int n = C.length;
        PriorityQueue<MinHeapNode> Q = new PriorityQueue<>();
        for (int i = 0; i < n; i++) {
            Q.add(new MinHeapNode(f[i], C[i]));
        }
        for (int i = 1; i <= n - 1; i++) {
            MinHeapNode x = Q.poll();
            MinHeapNode y = Q.poll();
            MinHeapNode z = new MinHeapNode(x.weight + y.weight, '\0');
            z.left = x;
            z.right = y;
            Q.add(z);
        }
        return Q.poll();
    }

    public static void printHuffmanCodes(MinHeapNode root, String code) {
        if (root == null) return;
        if (root.left == null && root.right == null) {
            System.out.println(root.ch + ": " + code);
            return;
        }
        printHuffmanCodes(root.left, code + "0");
        printHuffmanCodes(root.right, code + "1");
    }

    public static void main(String[] args) {
        System.out.println("--- Testare Heap (Gramada Maxim) ---");
        int[] heapStandard = {0, 4, 1, 3, 2, 16, 9, 10, 14, 8, 7};
        int n = heapStandard.length - 1;
        
        int[] T1 = heapStandard.clone();
        slowMakeHeap(T1, n);
        System.out.print("slowMakeHeap: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(T1[i] + " ");
        }
        System.out.println();

        int[] T2 = heapStandard.clone();
        makeHeap(T2, n);
        System.out.print("makeHeap:     ");
        for (int i = 1; i <= n; i++) {
            System.out.print(T2[i] + " ");
        }
        System.out.println();

        System.out.println("Alterare element de pe pozitia 3 cu valoarea 15:");
        alterHeap(T2, n, 3, 15);
        System.out.print("Dupa alterHeap: ");
        for (int i = 1; i <= n; i++) {
            System.out.print(T2[i] + " ");
        }
        System.out.println();

        System.out.println("Inserare element cu valoarea 20:");
        int[] T3 = new int[n + 2];
        System.arraycopy(T2, 0, T3, 0, n + 1);
        int newN = insert(T3, n, 20);
        System.out.print("Dupa insert:    ");
        for (int i = 1; i <= newN; i++) {
            System.out.print(T3[i] + " ");
        }
        System.out.println();

        System.out.println("\n--- Testare Heapsort ---");
        int[] sortArr = heapStandard.clone();
        heapsort(sortArr);
        System.out.print("Dupa heapsort:  ");
        for (int i = 1; i < sortArr.length; i++) {
            System.out.print(sortArr[i] + " ");
        }
        System.out.println();

        System.out.println("\n--- Testare Interclasare Optima ---");
        int[] lungimiSiruri = {0, 30, 10, 20, 30, 50, 10};
        int countSiruri = lungimiSiruri.length - 1;
        MinHeapNode root = interopt(lungimiSiruri, countSiruri);
        System.out.println("Arborele de interclasare optima construit:");
        printTree(root, "");

        System.out.println("\n--- Testare Huffman Coding ---");
        char[] C = {'a', 'b', 'c', 'd', 'e', 'f'};
        int[] f = {45, 13, 12, 16, 9, 5};
        MinHeapNode huffRoot = huffman(C, f);
        System.out.println("Coduri Huffman generate:");
        printHuffmanCodes(huffRoot, "");
    }
}
