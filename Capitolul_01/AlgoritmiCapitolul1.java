package Capitolul_01;

public class AlgoritmiCapitolul1 {

    public static void insertionSort(int[] A) {
        for (int j = 1; j < A.length; j++) {
            int key = A[j];
            int i = j - 1;
            while (i >= 0 && A[i] > key) {
                A[i + 1] = A[i];
                i = i - 1;
            }
            A[i + 1] = key;
        }
    }

    public static void selectionSort(int[] A) {
        int n = A.length;
        for (int i = 0; i < n - 1; i++) {
            int minj = i;
            int minx = A[i];
            for (int j = i + 1; j < n; j++) {
                if (A[j] < minx) {
                    minj = j;
                    minx = A[j];
                }
            }
            A[minj] = A[i];
            A[i] = minx;
        }
    }

    public static int euclid(int m, int n) {
        while (n != 0) {
            int temp = n;
            n = m % n;
            m = temp;
        }
        return m;
    }

    public static void hanoi(int n, char A, char B, char C) {
        if (n != 0) {
            hanoi(n - 1, A, C, B);
            System.out.println("Muta discul de pe " + A + " pe " + C);
            hanoi(n - 1, B, A, C);
        }
    }

    public static int fiboR(int n) {
        if (n < 2) {
            return n;
        } else {
            return fiboR(n - 1) + fiboR(n - 2);
        }
    }

    public static int fiboI(int n) {
        int i = 0;
        int j = 1;
        int s = 1;
        for (int k = 1; k <= n; k++) {
            i = j;
            j = s;
            s = i + j;
        }
        return i;
    }

    public static int[][] matrixProduct(int[][] A, int[][] B) {
        int m = A.length;
        int n = A[0].length;
        int p = B[0].length;
        int[][] C = new int[m][p];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < p; j++) {
                for (int k = 0; k < n; k++) {
                    C[i][j] += A[i][k] * B[k][j];
                }
            }
        }
        return C;
    }

    public static int russe(int a, int b) {
        java.util.ArrayList<Integer> X = new java.util.ArrayList<>();
        java.util.ArrayList<Integer> Y = new java.util.ArrayList<>();
        X.add(a);
        Y.add(b);
        int i = 0;
        while (X.get(i) > 1) {
            X.add(X.get(i) / 2);
            Y.add(Y.get(i) + Y.get(i));
            i = i + 1;
        }
        int prod = 0;
        while (i >= 0) {
            if (X.get(i) % 2 == 1) {
                prod = prod + Y.get(i);
            }
            i = i - 1;
        }
        return prod;
    }

    public static void main(String[] args) {
        int[] arr1 = {5, 2, 4, 6, 1, 3};
        System.out.print("Insertion Sort - In: ");
        printArray(arr1);
        insertionSort(arr1);
        System.out.print("Out: ");
        printArray(arr1);

        int[] arr2 = {5, 2, 4, 6, 1, 3};
        System.out.print("Selection Sort - In: ");
        printArray(arr2);
        selectionSort(arr2);
        System.out.print("Out: ");
        printArray(arr2);

        int m = 102, n = 18;
        System.out.println("Euclid - m: " + m + ", n: " + n + " -> GCD: " + euclid(m, n));

        int fibN = 6;
        System.out.println("FibonacciRecursive(" + fibN + ") -> " + fiboR(fibN));
        System.out.println("FibonacciIterative(" + fibN + ") -> " + fiboI(fibN));

        int[][] matA = {
            {1, 2},
            {3, 4}
        };
        int[][] matB = {
            {5, 6},
            {7, 8}
        };
        System.out.println("Matrix Product - A x B:");
        int[][] matC = matrixProduct(matA, matB);
        for (int i = 0; i < matC.length; i++) {
            for (int j = 0; j < matC[i].length; j++) {
                System.out.print(matC[i][j] + " ");
            }
            System.out.println();
        }

        int ra = 52, rb = 15;
        System.out.println("Russe - " + ra + " * " + rb + " -> " + russe(ra, rb));

        System.out.println("Hanoi (3 discuri):");
        hanoi(3, 'A', 'B', 'C');
    }

    private static void printArray(int[] arr) {
        for (int val : arr) {
            System.out.print(val + " ");
        }
        System.out.println();
    }
}
