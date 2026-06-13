package Capitolul_7;

import java.util.Arrays;

public class AlgoritmiDivideEtImpera {

    public static int binSearch(int[] S, int x) {
        int low = 0;
        int high = S.length - 1;
        int location = -1;
        while (low <= high && location == -1) {
            int mid = (low + high) / 2;
            if (x == S[mid]) {
                location = mid;
            } else if (x < S[mid]) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        return location;
    }

    public static int binSearchRec(int[] S, int x, int low, int high) {
        if (low > high) {
            return -1;
        }
        int mid = (low + high) / 2;
        if (x == S[mid]) {
            return mid;
        } else if (x < S[mid]) {
            return binSearchRec(S, x, low, mid - 1);
        } else {
            return binSearchRec(S, x, mid + 1, high);
        }
    }

    public static int partition(int[] A, int p, int r) {
        int x = A[p];
        int i = p - 1;
        int j = r + 1;
        while (true) {
            do {
                j--;
            } while (A[j] > x);
            do {
                i++;
            } while (A[i] < x);
            if (i < j) {
                int temp = A[i];
                A[i] = A[j];
                A[j] = temp;
            } else {
                return j;
            }
        }
    }

    public static void quickSort(int[] A, int p, int r) {
        if (p < r) {
            int q = partition(A, p, r);
            quickSort(A, p, q);
            quickSort(A, q + 1, r);
        }
    }

    public static void merge(int[] A, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] L = new int[n1];
        int[] R = new int[n2];
        for (int i = 0; i < n1; i++) {
            L[i] = A[p + i];
        }
        for (int j = 0; j < n2; j++) {
            R[j] = A[q + 1 + j];
        }
        int i = 0, j = 0, k = p;
        while (i < n1 && j < n2) {
            if (L[i] <= R[j]) {
                A[k] = L[i];
                i++;
            } else {
                A[k] = R[j];
                j++;
            }
            k++;
        }
        while (i < n1) {
            A[k] = L[i];
            i++;
            k++;
        }
        while (j < n2) {
            A[k] = R[j];
            j++;
            k++;
        }
    }

    public static void mergeSort(int[] A, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(A, p, q);
            mergeSort(A, q + 1, r);
            merge(A, p, q, r);
        }
    }

    public static long karatsuba(long x, long y) {
        if (x < 10 || y < 10) {
            return x * y;
        }
        int n = Math.max(String.valueOf(x).length(), String.valueOf(y).length());
        int m = n / 2;
        long power = (long) Math.pow(10, m);
        long xH = x / power;
        long xL = x % power;
        long yH = y / power;
        long yL = y % power;
        long a = karatsuba(xH, yH);
        long b = karatsuba(xL, yL);
        long c = karatsuba(xH + xL, yH + yL) - a - b;
        return a * (long) Math.pow(10, 2 * m) + c * power + b;
    }

    public static void main(String[] args) {
        System.out.println("--- Testare Cautare Binara ---");
        int[] sortedArray = {2, 4, 5, 7, 8, 10, 14, 18, 52, 102};
        int target = 18;
        
        int idxIterative = binSearch(sortedArray, target);
        int idxRecursive = binSearchRec(sortedArray, target, 0, sortedArray.length - 1);
        
        System.out.println("Tablou ordonat: " + Arrays.toString(sortedArray));
        System.out.println("Cautare " + target + ":");
        System.out.println("  Varianta iterativa (index): " + idxIterative);
        System.out.println("  Varianta recursiva (index): " + idxRecursive);
        
        System.out.println("\n--- Testare Quick Sort ---");
        int[] unsortedArray = {5, 3, 2, 6, 4, 1, 5, 7};
        System.out.println("Tablou initial:  " + Arrays.toString(unsortedArray));
        quickSort(unsortedArray, 0, unsortedArray.length - 1);
        System.out.println("Tablou sortat:    " + Arrays.toString(unsortedArray));

        System.out.println("\n--- Testare Merge Sort ---");
        int[] unsortedArray2 = {5, 2, 4, 7, 1, 3, 2, 6};
        System.out.println("Tablou initial:  " + Arrays.toString(unsortedArray2));
        mergeSort(unsortedArray2, 0, unsortedArray2.length - 1);
        System.out.println("Tablou sortat:    " + Arrays.toString(unsortedArray2));

        System.out.println("\n--- Testare Karatsuba Multiplication ---");
        long val1 = 12345;
        long val2 = 6789;
        long expected = val1 * val2;
        long res = karatsuba(val1, val2);
        System.out.println("Inmultire " + val1 + " * " + val2 + ":");
        System.out.println("  Asteptat: " + expected);
        System.out.println("  Rezultat: " + res);
    }
}
