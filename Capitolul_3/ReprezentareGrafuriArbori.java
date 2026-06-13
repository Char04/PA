package Capitolul_3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReprezentareGrafuriArbori {

    public static class Node {
        public int dest;
        public Node next;

        public Node(int dest) {
            this.dest = dest;
        }
    }

    public static class NodeInfo {
        public int parent;
        public int key;

        public NodeInfo(int parent, int key) {
            this.parent = parent;
            this.key = key;
        }
    }

    public static class TreeNode {
        public int key;
        public TreeNode parent;
        public List<TreeNode> children;

        public TreeNode(int key) {
            this.key = key;
            this.children = new ArrayList<>();
        }
    }

    public static TreeNode searchTreeByKey(TreeNode node, int key) {
        if (node == null) return null;
        if (node.key == key) return node;
        for (TreeNode child : node.children) {
            TreeNode found = searchTreeByKey(child, key);
            if (found != null) return found;
        }
        return null;
    }

    public static boolean insertNew(TreeNode root, int parentKey, int newKey) {
        TreeNode parentNode = searchTreeByKey(root, parentKey);
        if (parentNode != null) {
            TreeNode newNode = new TreeNode(newKey);
            newNode.parent = parentNode;
            parentNode.children.add(newNode);
            return true;
        }
        return false;
    }

    public static void displayTreeDFS(TreeNode node, String indent) {
        if (node == null) return;
        System.out.println(indent + "+-- " + node.key);
        for (TreeNode child : node.children) {
            displayTreeDFS(child, indent + "    ");
        }
    }
    public static class BSTNode {
        public int key;
        public BSTNode left;
        public BSTNode right;
        public BSTNode parent;

        public BSTNode(int key) {
            this.key = key;
        }
    }

    public static class BST {
        public BSTNode root;

        public BSTNode search(BSTNode x, int k) {
            if (x == null || k == x.key) {
                return x;
            }
            if (k < x.key) {
                return search(x.left, k);
            } else {
                return search(x.right, k);
            }
        }

        public BSTNode searchIterative(BSTNode x, int k) {
            while (x != null && k != x.key) {
                if (k < x.key) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            return x;
        }

        public BSTNode minimum(BSTNode x) {
            while (x.left != null) {
                x = x.left;
            }
            return x;
        }

        public BSTNode maximum(BSTNode x) {
            while (x.right != null) {
                x = x.right;
            }
            return x;
        }

        public BSTNode successor(BSTNode x) {
            if (x.right != null) {
                return minimum(x.right);
            }
            BSTNode y = x.parent;
            while (y != null && x == y.right) {
                x = y;
                y = y.parent;
            }
            return y;
        }

        public void insert(BSTNode z) {
            BSTNode y = null;
            BSTNode x = this.root;
            while (x != null) {
                y = x;
                if (z.key < x.key) {
                    x = x.left;
                } else {
                    x = x.right;
                }
            }
            z.parent = y;
            if (y == null) {
                this.root = z;
            } else if (z.key < y.key) {
                y.left = z;
            } else {
                y.right = z;
            }
        }

        public void transplant(BSTNode u, BSTNode v) {
            if (u.parent == null) {
                this.root = v;
            } else if (u == u.parent.left) {
                u.parent.left = v;
            } else {
                u.parent.right = v;
            }
            if (v != null) {
                v.parent = u.parent;
            }
        }

        public void delete(BSTNode z) {
            if (z.left == null) {
                transplant(z, z.right);
            } else if (z.right == null) {
                transplant(z, z.left);
            } else {
                BSTNode y = minimum(z.right);
                if (y.parent != z) {
                    transplant(y, y.right);
                    y.right = z.right;
                    y.right.parent = y;
                }
                transplant(z, y);
                y.left = z.left;
                y.left.parent = y;
            }
        }

        public void leftRotate(BSTNode x) {
            BSTNode y = x.right;
            x.right = y.left;
            if (y.left != null) {
                y.left.parent = x;
            }
            y.parent = x.parent;
            if (x.parent == null) {
                this.root = y;
            } else if (x == x.parent.left) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
            y.left = x;
            x.parent = y;
        }

        public void rightRotate(BSTNode y) {
            BSTNode x = y.left;
            y.left = x.right;
            if (x.right != null) {
                x.right.parent = y;
            }
            x.parent = y.parent;
            if (y.parent == null) {
                this.root = x;
            } else if (y == y.parent.right) {
                y.parent.right = x;
            } else {
                y.parent.left = x;
            }
            x.right = y;
            y.parent = x;
        }

        public void inorder(BSTNode x, List<Integer> res) {
            if (x != null) {
                inorder(x.left, res);
                res.add(x.key);
                inorder(x.right, res);
            }
        }

        public void preorder(BSTNode x, List<Integer> res) {
            if (x != null) {
                res.add(x.key);
                preorder(x.left, res);
                preorder(x.right, res);
            }
        }

        public void postorder(BSTNode x, List<Integer> res) {
            if (x != null) {
                postorder(x.left, res);
                postorder(x.right, res);
                res.add(x.key);
            }
        }
    }

    public static void main(String[] args) {
        int[][] adjMatrix = new int[7][7];
        Node[] adjList = new Node[7];

        try (BufferedReader br = new BufferedReader(new FileReader("Capitolul_3/edges.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                int u = Integer.parseInt(parts[0]);
                int v = Integer.parseInt(parts[1]);
                adjMatrix[u][v] = 1;

                Node newNode = new Node(v);
                newNode.next = adjList[u];
                adjList[u] = newNode;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        System.out.println("Matricea de adiacenta:");
        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 6; j++) {
                System.out.print(adjMatrix[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Listele de adiacenta:");
        for (int i = 1; i <= 6; i++) {
            System.out.print(i + ": ");
            Node temp = adjList[i];
            while (temp != null) {
                System.out.print(temp.dest + " -> ");
                temp = temp.next;
            }
            System.out.println("null");
        }

        NodeInfo[] treeArray = new NodeInfo[13];
        treeArray[1] = new NodeInfo(0, 7);
        treeArray[2] = new NodeInfo(1, 3);
        treeArray[3] = new NodeInfo(1, 10);
        treeArray[4] = new NodeInfo(1, 4);
        treeArray[5] = new NodeInfo(2, 8);
        treeArray[6] = new NodeInfo(2, 12);
        treeArray[7] = new NodeInfo(3, 11);
        treeArray[8] = new NodeInfo(4, 2);
        treeArray[9] = new NodeInfo(5, 6);
        treeArray[10] = new NodeInfo(5, 5);
        treeArray[11] = new NodeInfo(6, 1);
        treeArray[12] = new NodeInfo(10, 9);

        System.out.println("Reprezentare optimizata arbore (un singur vector de structuri):");
        for (int i = 1; i <= 12; i++) {
            System.out.println("Nod " + i + " -> Cheie: " + treeArray[i].key + ", Parinte index: " + treeArray[i].parent);
        }

        TreeNode root = new TreeNode(7);
        insertNew(root, 7, 3);
        insertNew(root, 7, 10);
        insertNew(root, 7, 4);
        insertNew(root, 3, 8);
        insertNew(root, 3, 12);
        insertNew(root, 10, 11);
        insertNew(root, 4, 2);
        insertNew(root, 8, 6);
        insertNew(root, 8, 5);
        insertNew(root, 12, 1);
        insertNew(root, 5, 9);

        System.out.println("Structura arborelui parcurs DFS:");
        displayTreeDFS(root, "");

        int searchKey = 12;
        TreeNode found = searchTreeByKey(root, searchKey);
        System.out.println("Cautare nod cu cheia " + searchKey + ": " + (found != null ? "Gasit!" : "Negasit"));

        System.out.println("BST implementation tests:");
        BST bst = new BST();
        bst.insert(new BSTNode(15));
        bst.insert(new BSTNode(6));
        bst.insert(new BSTNode(18));
        bst.insert(new BSTNode(3));
        bst.insert(new BSTNode(7));
        bst.insert(new BSTNode(17));
        bst.insert(new BSTNode(20));

        List<Integer> in = new ArrayList<>();
        bst.inorder(bst.root, in);
        System.out.println("BST Inorder (sorted): " + in);

        BSTNode searchNode = bst.search(bst.root, 17);
        System.out.println("Search key 17: " + (searchNode != null ? "Found with key " + searchNode.key : "Not found"));

        BSTNode minNode = bst.minimum(bst.root);
        BSTNode maxNode = bst.maximum(bst.root);
        System.out.println("Min key: " + minNode.key + ", Max key: " + maxNode.key);

        BSTNode succNode = bst.successor(bst.search(bst.root, 15));
        System.out.println("Successor of 15: " + (succNode != null ? succNode.key : "none"));

        System.out.println("Deleting node with key 6:");
        bst.delete(bst.search(bst.root, 6));
        in.clear();
        bst.inorder(bst.root, in);
        System.out.println("BST Inorder after deletion: " + in);

        System.out.println("BST Rotations test:");
        bst.leftRotate(bst.root);
        in.clear();
        bst.inorder(bst.root, in);
        System.out.println("BST root after left rotation: " + bst.root.key);
        System.out.println("BST Inorder after rotation (should be unchanged): " + in);
    }
}

