package Capitolul_02;

public class StructuriDateCapitolul2 {

    public static class Stiva {
        private int[] data;
        private int top;

        public Stiva(int capacity) {
            data = new int[capacity];
            top = -1;
        }

        public void push(int val) {
            data[++top] = val;
        }

        public int pop() {
            return data[top--];
        }

        public boolean isEmpty() {
            return top == -1;
        }
    }

    public static int search(int[] S, int x) {
        for (int i = 0; i < S.length; i++) {
            if (S[i] == x) {
                return i;
            }
        }
        return -1;
    }

    public static void reverse(int[] S) {
        int i = 0;
        int j = S.length - 1;
        while (i < j) {
            int temp = S[i];
            S[i] = S[j];
            S[j] = temp;
            i++;
            j--;
        }
    }

    public static int[][] initMatrix(int rows, int cols) {
        int[][] M = new int[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                M[i][j] = i * cols + j;
            }
        }
        return M;
    }

    public static class HashTable {
        public Integer[] T;
        public int m;
        public static final int DELETED = -999999;

        public HashTable(int size) {
            this.m = size;
            this.T = new Integer[m];
        }

        public int h(int k, int i) {
            return ((k % m + i) % m + m) % m;
        }

        public int insert(int k) {
            int i = 0;
            while (i != m) {
                int j = h(k, i);
                if (T[j] == null || T[j] == DELETED) {
                    T[j] = k;
                    return j;
                }
                i = i + 1;
            }
            return -1;
        }

        public int search(int k) {
            int i = 0;
            int j = h(k, 0);
            while (i != m && T[j] != null) {
                if (T[j] == k) {
                    return j;
                }
                i = i + 1;
                j = h(k, i);
            }
            return -1;
        }
    }

    public static int evaluatePostfix(String expr) {
        String[] tokens = expr.split("\\s+");
        Stiva stack = new Stiva(tokens.length);
        for (String token : tokens) {
            if (token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/")) {
                int val2 = stack.pop();
                int val1 = stack.pop();
                int res = 0;
                if (token.equals("+")) res = val1 + val2;
                else if (token.equals("-")) res = val1 - val2;
                else if (token.equals("*")) res = val1 * val2;
                else if (token.equals("/")) res = val1 / val2;
                stack.push(res);
            } else {
                stack.push(Integer.parseInt(token));
            }
        }
        return stack.pop();
    }

    public static class Cell {
        public int r, c;
        public Cell(int r, int c) {
            this.r = r;
            this.c = c;
        }
    }

    public static boolean solveMaze(int[][] maze, Cell start, Cell end, java.util.List<Cell> path) {
        int rows = maze.length;
        int cols = maze[0].length;
        boolean[][] visited = new boolean[rows][cols];
        java.util.Stack<Cell> stack = new java.util.Stack<>();
        stack.push(start);
        visited[start.r][start.c] = true;
        while (!stack.isEmpty()) {
            Cell curr = stack.peek();
            if (curr.r == end.r && curr.c == end.c) {
                path.addAll(stack);
                return true;
            }
            boolean moved = false;
            int[][] dirs = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
            for (int[] dir : dirs) {
                int nr = curr.r + dir[0];
                int nc = curr.c + dir[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && maze[nr][nc] == 0 && !visited[nr][nc]) {
                    Cell next = new Cell(nr, nc);
                    stack.push(next);
                    visited[nr][nc] = true;
                    moved = true;
                    break;
                }
            }
            if (!moved) {
                stack.pop();
            }
        }
        return false;
    }

    public static class SNode {
        public int val;
        public SNode next;
        public SNode(int val) {
            this.val = val;
        }
    }

    public static class SinglyList {
        public SNode head;

        public void insertAtEnd(int val) {
            SNode newNode = new SNode(val);
            if (head == null) {
                head = newNode;
                return;
            }
            SNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
        }

        public void deleteSecond() {
            if (head == null || head.next == null) return;
            head.next = head.next.next;
        }

        public int size() {
            int count = 0;
            SNode temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            return count;
        }

        public void insertAtMiddle(int val) {
            int sz = size();
            int mid = sz / 2;
            if (mid == 0) {
                SNode newNode = new SNode(val);
                newNode.next = head;
                head = newNode;
                return;
            }
            SNode temp = head;
            for (int i = 0; i < mid - 1; i++) {
                temp = temp.next;
            }
            SNode newNode = new SNode(val);
            newNode.next = temp.next;
            temp.next = newNode;
        }

        public void deleteFirst() {
            if (head != null) {
                head = head.next;
            }
        }

        public void deleteLast() {
            if (head == null) return;
            if (head.next == null) {
                head = null;
                return;
            }
            SNode temp = head;
            while (temp.next.next != null) {
                temp = temp.next;
            }
            temp.next = null;
        }

        public void display() {
            SNode temp = head;
            while (temp != null) {
                System.out.print(temp.val + " -> ");
                temp = temp.next;
            }
            System.out.println("null");
        }
    }

    public static class DNode {
        public int val;
        public DNode prev, next;
        public DNode(int val) {
            this.val = val;
        }
    }

    public static class DoublyList {
        public DNode head;

        public void insertAtEnd(int val) {
            DNode newNode = new DNode(val);
            if (head == null) {
                head = newNode;
                return;
            }
            DNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.prev = temp;
        }

        public void deleteSecond() {
            if (head == null || head.next == null) return;
            DNode second = head.next;
            head.next = second.next;
            if (second.next != null) {
                second.next.prev = head;
            }
        }

        public int size() {
            int count = 0;
            DNode temp = head;
            while (temp != null) {
                count++;
                temp = temp.next;
            }
            return count;
        }

        public void insertAtMiddle(int val) {
            int sz = size();
            int mid = sz / 2;
            if (mid == 0) {
                DNode newNode = new DNode(val);
                newNode.next = head;
                if (head != null) head.prev = newNode;
                head = newNode;
                return;
            }
            DNode temp = head;
            for (int i = 0; i < mid - 1; i++) {
                temp = temp.next;
            }
            DNode newNode = new DNode(val);
            newNode.next = temp.next;
            newNode.prev = temp;
            if (temp.next != null) {
                temp.next.prev = newNode;
            }
            temp.next = newNode;
        }

        public void deleteFirst() {
            if (head != null) {
                head = head.next;
                if (head != null) head.prev = null;
            }
        }

        public void deleteLast() {
            if (head == null) return;
            if (head.next == null) {
                head = null;
                return;
            }
            DNode temp = head;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.prev.next = null;
        }

        public void display() {
            DNode temp = head;
            while (temp != null) {
                System.out.print(temp.val + " <-> ");
                temp = temp.next;
            }
            System.out.println("null");
        }
    }

    public static class CNode {
        public int val;
        public CNode prev, next;
        public CNode(int val) {
            this.val = val;
        }
    }

    public static class CircularList {
        public CNode head;

        public void insertAtEnd(int val) {
            CNode newNode = new CNode(val);
            if (head == null) {
                head = newNode;
                head.next = head;
                head.prev = head;
                return;
            }
            CNode tail = head.prev;
            tail.next = newNode;
            newNode.prev = tail;
            newNode.next = head;
            head.prev = newNode;
        }

        public void deleteSecond() {
            if (head == null || head.next == head) return;
            CNode second = head.next;
            head.next = second.next;
            second.next.prev = head;
        }

        public int size() {
            if (head == null) return 0;
            int count = 1;
            CNode temp = head.next;
            while (temp != head) {
                count++;
                temp = temp.next;
            }
            return count;
        }

        public void insertAtMiddle(int val) {
            int sz = size();
            if (sz == 0) {
                insertAtEnd(val);
                return;
            }
            int mid = sz / 2;
            CNode temp = head;
            for (int i = 0; i < mid - 1; i++) {
                temp = temp.next;
            }
            CNode newNode = new CNode(val);
            newNode.next = temp.next;
            newNode.prev = temp;
            temp.next.prev = newNode;
            temp.next = newNode;
        }

        public void deleteFirst() {
            if (head == null) return;
            if (head.next == head) {
                head = null;
                return;
            }
            CNode tail = head.prev;
            head = head.next;
            head.prev = tail;
            tail.next = head;
        }

        public void deleteLast() {
            if (head == null) return;
            if (head.next == head) {
                head = null;
                return;
            }
            CNode tail = head.prev;
            CNode newTail = tail.prev;
            newTail.next = head;
            head.prev = newTail;
        }

        public void display() {
            if (head == null) {
                System.out.println("empty circular");
                return;
            }
            CNode temp = head;
            do {
                System.out.print(temp.val + " <-> ");
                temp = temp.next;
            } while (temp != head);
            System.out.println("head");
        }
    }

    public static void main(String[] args) {
        String expr = "5 1 2 + 4 * + 3 -";
        System.out.println("Evaluare Postfixata (" + expr + ") -> " + evaluatePostfix(expr));

        int[][] maze = {
            {0, 1, 0, 0, 0},
            {0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0},
            {1, 1, 1, 1, 0},
            {0, 0, 0, 0, 0}
        };
        java.util.List<Cell> path = new java.util.ArrayList<>();
        boolean solved = solveMaze(maze, new Cell(0, 0), new Cell(4, 4), path);
        System.out.println("Maze Solved: " + solved);
        if (solved) {
            for (Cell c : path) {
                System.out.print("(" + c.r + "," + c.c + ") ");
            }
            System.out.println();
        }

        System.out.println("Singly Linked List:");
        SinglyList sl = new SinglyList();
        sl.insertAtEnd(10); sl.insertAtEnd(20); sl.insertAtEnd(30); sl.insertAtEnd(40); sl.insertAtEnd(50);
        System.out.print("Inceput: "); sl.display();
        sl.deleteSecond();
        System.out.print("Dupa stergere al 2-lea element: "); sl.display();
        sl.insertAtMiddle(99);
        System.out.print("Dupa inserare in mijloc: "); sl.display();
        sl.deleteFirst(); sl.deleteLast();
        System.out.print("Dupa stergere capete: "); sl.display();

        System.out.println("Doubly Linked List:");
        DoublyList dl = new DoublyList();
        dl.insertAtEnd(10); dl.insertAtEnd(20); dl.insertAtEnd(30); dl.insertAtEnd(40); dl.insertAtEnd(50);
        System.out.print("Inceput: "); dl.display();
        dl.deleteSecond();
        System.out.print("Dupa stergere al 2-lea element: "); dl.display();
        dl.insertAtMiddle(99);
        System.out.print("Dupa inserare in mijloc: "); dl.display();
        dl.deleteFirst(); dl.deleteLast();
        System.out.print("Dupa stergere capete: "); dl.display();

        System.out.println("Circular Doubly Linked List:");
        CircularList cl = new CircularList();
        cl.insertAtEnd(10); cl.insertAtEnd(20); cl.insertAtEnd(30); cl.insertAtEnd(40); cl.insertAtEnd(50);
        System.out.print("Inceput: "); cl.display();
        cl.deleteSecond();
        System.out.print("Dupa stergere al 2-lea element: "); cl.display();
        cl.insertAtMiddle(99);
        System.out.print("Dupa inserare in mijloc: "); cl.display();
        cl.deleteFirst(); cl.deleteLast();
        System.out.print("Dupa stergere capete: "); cl.display();

        System.out.println("Array & Matrix Tests:");
        int[] S = {1, 3, 5, 7, 9};
        System.out.println("Search 7 index: " + search(S, 7));
        reverse(S);
        System.out.print("Reversed S: ");
        for (int val : S) {
            System.out.print(val + " ");
        }
        System.out.println();
        int[][] M = initMatrix(2, 3);
        System.out.println("Matrix 2x3 cell (1,1): " + M[1][1]);

        System.out.println("Hash Table Test:");
        HashTable ht = new HashTable(5);
        ht.insert(10);
        ht.insert(15);
        System.out.println("Search 10: " + ht.search(10));
        System.out.println("Search 15: " + ht.search(15));
        System.out.println("Search 20 (not inserted): " + ht.search(20));
    }
}
