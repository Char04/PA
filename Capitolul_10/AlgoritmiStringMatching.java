package Capitolul_10;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class AlgoritmiStringMatching {

    public static List<Integer> naiveSearch(String T, String P) {
        List<Integer> shifts = new ArrayList<>();
        int n = T.length();
        int m = P.length();
        for (int s = 0; s <= n - m; s++) {
            boolean match = true;
            for (int i = 0; i < m; i++) {
                if (T.charAt(s + i) != P.charAt(i)) {
                    match = false;
                    break;
                }
            }
            if (match) {
                shifts.add(s);
            }
        }
        return shifts;
    }

    public static List<Integer> rabinKarpSearch(String T, String P, int d, int q) {
        List<Integer> shifts = new ArrayList<>();
        int n = T.length();
        int m = P.length();
        if (n < m) return shifts;
        int h = 1;
        for (int i = 0; i < m - 1; i++) {
            h = (h * d) % q;
        }
        int p = 0;
        int t = 0;
        for (int i = 0; i < m; i++) {
            p = (d * p + P.charAt(i)) % q;
            t = (d * t + T.charAt(i)) % q;
        }
        for (int s = 0; s <= n - m; s++) {
            if (p == t) {
                boolean match = true;
                for (int i = 0; i < m; i++) {
                    if (T.charAt(s + i) != P.charAt(i)) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    shifts.add(s);
                }
            }
            if (s < n - m) {
                t = (d * (t - T.charAt(s) * h) + T.charAt(s + m)) % q;
                if (t < 0) {
                    t = t + q;
                }
            }
        }
        return shifts;
    }

    public static List<Map<Character, Integer>> computeTransitionFunction(String P) {
        int m = P.length();
        List<Character> alphabet = new ArrayList<>();
        for (char c : P.toCharArray()) {
            if (!alphabet.contains(c)) {
                alphabet.add(c);
            }
        }
        List<Map<Character, Integer>> delta = new ArrayList<>();
        for (int q = 0; q <= m; q++) {
            Map<Character, Integer> transitions = new HashMap<>();
            for (char a : alphabet) {
                int k = Math.min(m, q + 1);
                String target = P.substring(0, q) + a;
                while (k > 0) {
                    String prefix = P.substring(0, k);
                    if (target.endsWith(prefix)) {
                        break;
                    }
                    k--;
                }
                transitions.put(a, k);
            }
            delta.add(transitions);
        }
        return delta;
    }

    public static List<Integer> finiteAutomataSearch(String T, String P) {
        List<Integer> shifts = new ArrayList<>();
        int n = T.length();
        int m = P.length();
        if (m == 0 || n < m) return shifts;
        List<Map<Character, Integer>> delta = computeTransitionFunction(P);
        int q = 0;
        for (int i = 0; i < n; i++) {
            q = delta.get(q).getOrDefault(T.charAt(i), 0);
            if (q == m) {
                shifts.add(i - m + 1);
            }
        }
        return shifts;
    }

    public static void main(String[] args) {
        String T = "AABAACAADAABAAABAA";
        String P = "AABA";
        System.out.println("Text: " + T);
        System.out.println("Pattern: " + P);
        List<Integer> naiveRes = naiveSearch(T, P);
        System.out.println("Naive shifts: " + naiveRes);
        List<Integer> rkRes = rabinKarpSearch(T, P, 256, 101);
        System.out.println("Rabin-Karp shifts: " + rkRes);
        List<Integer> faRes = finiteAutomataSearch(T, P);
        System.out.println("Finite Automata shifts: " + faRes);
    }
}
