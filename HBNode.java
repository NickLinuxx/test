package ce326.hw1;

public class HBNode {
    private int key;          // Node's key
    private int height;       // Height of the node
    private int weight;       // Number of nodes in the subtree including this node
    private HBNode left;      // Left child
    private HBNode right;     // Right child
    private HBNode parent;    // Parent node

    // Constructor for creating a new node
    public HBNode(int key) {
        this.key = key;
        this.height = 1;      // A new node is a leaf with height 1
        this.weight = 1;      // A new node has weight 1 (itself)
        this.left = null;
        this.right = null;
        this.parent = null;
    }

    // Getters and setters
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public HBNode getLeft() {
        return left;
    }

    public void setLeft(HBNode left) {
        this.left = left;
        if (left != null) {
            left.setParent(this);
        }
    }

    public HBNode getRight() {
        return right;
    }

    public void setRight(HBNode right) {
        this.right = right;
        if (right != null) {
            right.setParent(this);
        }
    }

    public HBNode getParent() {
        return parent;
    }

    public void setParent(HBNode parent) {
        this.parent = parent;
    }

    // Helper methods
    public int getLeftHeight() {
        return (left == null) ? 0 : left.getHeight();
    }

    public int getRightHeight() {
        return (right == null) ? 0 : right.getHeight();
    }

    public int getLeftWeight() {
        return (left == null) ? 0 : left.getWeight();
    }

    public int getRightWeight() {
        return (right == null) ? 0 : right.getWeight();
    }

    // Update height based on children's height
    public void updateHeight() {
        this.height = Math.max(getLeftHeight(), getRightHeight()) + 1;
    }

    // Update weight based on children's weight
    public void updateWeight() {
        this.weight = getLeftWeight() + getRightWeight() + 1;
    }

    // Calculate the balance factor for HBTree (max allowed height difference)
    public int getMaxAllowedBalanceFactor() {
        return Math.max(1, (int) Math.floor(Math.log(weight) / Math.log(2)));
    }

    // Calculate the actual height difference between left and right subtrees
    public int getHeightDifference() {
        return Math.abs(getLeftHeight() - getRightHeight());
    }

    // Check if the node is balanced according to HBTree criteria
    public boolean isBalanced() {
        return getHeightDifference() <= getMaxAllowedBalanceFactor();
    }
}