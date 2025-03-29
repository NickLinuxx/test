package ce326.hw1;

public class HBTree {
    private HBNode root;

    public HBTree() {
        this.root = null;
    }

    // Helper method to get height of a node
    private int height(HBNode node) {
        return node == null ? 0 : node.getHeight();
    }

    public HBNode getRoot() {
        return root;
    }

    // Helper method to get weight of a node
    private int weight(HBNode node) {
        return node == null ? 0 : node.getWeight();
    }

    // Update height and weight of a node
    private void updateNode(HBNode node) {
        if (node == null) return;
        
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        
        node.setHeight(Math.max(leftHeight, rightHeight) + 1);
        node.setWeight(weight(node.getLeft()) + weight(node.getRight()) + 1);
    }

    // Right rotation
    private HBNode rotateRight(HBNode y) {
        HBNode x = y.getLeft();
        HBNode T2 = x.getRight();

        x.setRight(y);
        y.setLeft(T2);

        updateNode(y);
        updateNode(x);

        return x;
    }

    // Left rotation
    private HBNode rotateLeft(HBNode x) {
        HBNode y = x.getRight();
        HBNode T2 = y.getLeft();

        y.setLeft(x);
        x.setRight(T2);

        updateNode(x);
        updateNode(y);

        return y;
    }

    // Balance the tree after insertion or deletion
    private HBNode balance(HBNode node) {
        if (node == null) return null;

        updateNode(node);

        // Left heavy
        if (height(node.getLeft()) > height(node.getRight()) + 1) {
            if (height(node.getLeft().getRight()) > height(node.getLeft().getLeft())) {
                // Left-Right case
                node.setLeft(rotateLeft(node.getLeft()));
            }
            node = rotateRight(node);
        }
        // Right heavy
        else if (height(node.getRight()) > height(node.getLeft()) + 1) {
            if (height(node.getRight().getLeft()) > height(node.getRight().getRight())) {
                // Right-Left case
                node.setRight(rotateRight(node.getRight()));
            }
            node = rotateLeft(node);
        }

        return node;
    }

    // Insert a key
    public boolean insert(int key) {
        if (find(key)) return false;
        root = insertRec(root, key);
        return true;
    }

    private HBNode insertRec(HBNode node, int key) {
        if (node == null) {
            return new HBNode(key);
        }

        if (key < node.getKey()) {
            node.setLeft(insertRec(node.getLeft(), key));
        } else {
            node.setRight(insertRec(node.getRight(), key));
        }

        return balance(node);
    }

    // Find a key
    public boolean find(int key) {
        return findRec(root, key);
    }

    private boolean findRec(HBNode node, int key) {
        if (node == null) return false;
        
        if (key == node.getKey()) return true;
        
        if (key < node.getKey()) {
            return findRec(node.getLeft(), key);
        } else {
            return findRec(node.getRight(), key);
        }
    }

    // Delete a key
    public boolean delete(int key) {
        if (!find(key)) return false;
        root = deleteRec(root, key);
        return true;
    }

    private HBNode deleteRec(HBNode node, int key) {
        if (node == null) return null;

        if (key < node.getKey()) {
            node.setLeft(deleteRec(node.getLeft(), key));
        } else if (key > node.getKey()) {
            node.setRight(deleteRec(node.getRight(), key));
        } else {
            // Node with only one child or no child
            if (node.getLeft() == null) return node.getRight();
            if (node.getRight() == null) return node.getLeft();

            // Node with two children, get the inorder successor (smallest in right subtree)
            HBNode successor = findMinNode(node.getRight());
            node.setKey(successor.getKey());
            node.setRight(deleteRec(node.getRight(), successor.getKey()));
        }

        return balance(node);
    }

    private HBNode findMinNode(HBNode node) {
        HBNode current = node;
        while (current.getLeft() != null) {
            current = current.getLeft();
        }
        return current;
    }

    // Get tree info: size, max_height, min_height
    public int[] getTreeInfo() {
        int size = 0;
        int maxHeight = 0;
        int minHeight = 0;

        if (root != null) {
            size = root.getWeight();
            maxHeight = root.getHeight();
            minHeight = calculateMinHeight(root);
        }

        return new int[]{size, maxHeight, minHeight};
    }

    private int calculateMinHeight(HBNode node) {
        if (node == null) return 0;
        
        int leftMinHeight = calculateMinHeight(node.getLeft());
        int rightMinHeight = calculateMinHeight(node.getRight());
        
        return Math.min(leftMinHeight, rightMinHeight) + 1;
    }

    // Pre-order traversal for printing
    public void preOrderTraversal(HBNode node) {
        if (node == null) return;
        
        System.out.print(node.getKey() + " ");
        preOrderTraversal(node.getLeft());
        preOrderTraversal(node.getRight());
    }
}