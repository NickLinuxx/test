package ce326.hw1;

public class HBNode {
    private int key;
    private HBNode left;
    private HBNode right;
    private int height;
    private int weight;

    public HBNode(int key) {
        this.key = key;
        this.left = null;
        this.right = null;
        this.height = 1;
        this.weight = 1;
    }

    // Getters and Setters
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public HBNode getLeft() {
        return left;
    }

    public void setLeft(HBNode left) {
        this.left = left;
    }

    public HBNode getRight() {
        return right;
    }

    public void setRight(HBNode right) {
        this.right = right;
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
}