package ce326.hw1;

public class HBTree {
    private HBNode root;

    public HBTree() {
        this.root = null;
    }

    // Get the root of the tree
    public HBNode getRoot() {
        return this.root;
    }

    // Get the size of the tree (number of nodes)
    public int getSize() {
        return (root == null) ? 0 : root.getWeight();
    }

    // Get the maximum height of the tree
    public int getMaxHeight() {
        return (root == null) ? 0 : root.getHeight();
    }

    // Get the minimum height of the tree
    public int getMinHeight() {
        if (root == null) {
            return 0;
        }
        return getMinHeight(root);
    }

    private int getMinHeight(HBNode node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }
        if (node.getLeft() == null) {
            return getMinHeight(node.getRight()) + 1;
        }
        if (node.getRight() == null) {
            return getMinHeight(node.getLeft()) + 1;
        }
        return Math.min(getMinHeight(node.getLeft()), getMinHeight(node.getRight())) + 1;
    }

    // Find a node with the given key
    public HBNode find(int key) {
        return find(root, key);
    }

    private HBNode find(HBNode node, int key) {
        if (node == null) {
            return null;
        }
        if (key == node.getKey()) {
            return node;
        }
        if (key < node.getKey()) {
            return find(node.getLeft(), key);
        } else {
            return find(node.getRight(), key);
        }
    }

    // Insert a new key into the tree
    public boolean insert(int key) {
        // Check if key already exists
        if (find(key) != null) {
            return false;
        }

        // If tree is empty, create root
        if (root == null) {
            root = new HBNode(key);
            return true;
        }

        // Find the position to insert and insert the node
        HBNode parent = findParentForInsertion(root, key);
        HBNode newNode = new HBNode(key);

        if (key < parent.getKey()) {
            parent.setLeft(newNode);
        } else {
            parent.setRight(newNode);
        }
        newNode.setParent(parent);

        // Rebalance the tree
        rebalanceAfterInsertion(newNode);
        return true;
    }

    private HBNode findParentForInsertion(HBNode node, int key) {
        if (key < node.getKey()) {
            if (node.getLeft() == null) {
                return node;
            }
            return findParentForInsertion(node.getLeft(), key);
        } else {
            if (node.getRight() == null) {
                return node;
            }
            return findParentForInsertion(node.getRight(), key);
        }
    }

    private void rebalanceAfterInsertion(HBNode node) {
        HBNode current = node;
        
        // Update heights and weights up to the root
        while (current != null) {
            current.updateHeight();
            current.updateWeight();
            
            // Check if the current node is unbalanced
            if (!current.isBalanced()) {
                // Identify the imbalance type and perform rotation
                rebalance(current);
            }
            
            current = current.getParent();
        }
    }

    // Delete a node with the given key
    public boolean delete(int key) {
        HBNode nodeToDelete = find(key);
        if (nodeToDelete == null) {
            return false;
        }

        // Case 1: Node to delete has no children or one child
        if (nodeToDelete.getLeft() == null || nodeToDelete.getRight() == null) {
            HBNode replacementNode = (nodeToDelete.getLeft() != null) ? nodeToDelete.getLeft() : nodeToDelete.getRight();
            HBNode parent = nodeToDelete.getParent();

            // If the node to delete is a leaf
            if (replacementNode == null) {
                if (parent == null) {
                    root = null;
                } else if (parent.getLeft() == nodeToDelete) {
                    parent.setLeft(null);
                } else {
                    parent.setRight(null);
                }
                rebalanceAfterDeletion(parent);
            } else {
                // Node has one child
                nodeToDelete.setKey(replacementNode.getKey());
                
                if (nodeToDelete.getLeft() == replacementNode) {
                    nodeToDelete.setLeft(replacementNode.getLeft());
                } else {
                    nodeToDelete.setRight(replacementNode.getRight());
                }
                
                rebalanceAfterDeletion(nodeToDelete);
            }
        } else {
            // Case 2: Node has two children
            // Find the leftmost node in the right subtree
            HBNode successor = findLeftmost(nodeToDelete.getRight());
            
            // Copy the successor's key to the node to be deleted
            nodeToDelete.setKey(successor.getKey());
            
            // Remove the successor (which has at most one child)
            HBNode successorParent = successor.getParent();
            
            if (successorParent == nodeToDelete) {
                // If successor is direct right child of nodeToDelete
                nodeToDelete.setRight(successor.getRight());
                rebalanceAfterDeletion(nodeToDelete);
            } else {
                // Otherwise
                successorParent.setLeft(successor.getRight());
                rebalanceAfterDeletion(successorParent);
            }
        }

        return true;
    }

    private HBNode findLeftmost(HBNode node) {
        while (node.getLeft() != null) {
            node = node.getLeft();
        }
        return node;
    }

    private void rebalanceAfterDeletion(HBNode node) {
        HBNode current = node;
        
        while (current != null) {
            current.updateHeight();
            current.updateWeight();
            
            // Check if the current node is unbalanced
            if (!current.isBalanced()) {
                current = rebalance(current);
                
                // After rebalancing, check if nodes involved in rotation need further rebalancing
                if (!current.isBalanced()) {
                    current = rebalance(current);
                }
                
                if (current.getLeft() != null && !current.getLeft().isBalanced()) {
                    rebalance(current.getLeft());
                }
                
                if (current.getRight() != null && !current.getRight().isBalanced()) {
                    rebalance(current.getRight());
                }
            }
            
            current = current.getParent();
        }
    }

    // Perform appropriate rotation to rebalance the tree
    private HBNode rebalance(HBNode node) {
        int leftHeight = node.getLeftHeight();
        int rightHeight = node.getRightHeight();
        
        // Left subtree is higher
        if (leftHeight > rightHeight) {
            HBNode leftChild = node.getLeft();
            
            // Check for left-left or left-right case
            if (leftChild.getLeftHeight() >= leftChild.getRightHeight()) {
                // Left-Left case: Right rotation
                return rightRotate(node);
            } else {
                // Left-Right case: Left-Right rotation
                leftRotate(leftChild);
                return rightRotate(node);
            }
        } else {
            // Right subtree is higher
            HBNode rightChild = node.getRight();
            
            // Check for right-right or right-left case
            if (rightChild.getRightHeight() >= rightChild.getLeftHeight()) {
                // Right-Right case: Left rotation
                return leftRotate(node);
            } else {
                // Right-Left case: Right-Left rotation
                rightRotate(rightChild);
                return leftRotate(node);
            }
        }
    }

    // Perform a right rotation
    private HBNode rightRotate(HBNode y) {
        HBNode x = y.getLeft();
        HBNode T2 = x.getRight();
        HBNode parent = y.getParent();
        
        // Perform rotation
        x.setRight(y);
        y.setLeft(T2);
        x.setParent(parent);
        
        // Update parent links
        if (parent == null) {
            root = x;
        } else if (parent.getLeft() == y) {
            parent.setLeft(x);
        } else {
            parent.setRight(x);
        }
        
        // Update heights and weights
        y.updateHeight();
        y.updateWeight();
        x.updateHeight();
        x.updateWeight();
        
        return x;
    }

    // Perform a left rotation
    private HBNode leftRotate(HBNode x) {
        HBNode y = x.getRight();
        HBNode T2 = y.getLeft();
        HBNode parent = x.getParent();
        
        // Perform rotation
        y.setLeft(x);
        x.setRight(T2);
        y.setParent(parent);
        
        // Update parent links
        if (parent == null) {
            root = y;
        } else if (parent.getLeft() == x) {
            parent.setLeft(y);
        } else {
            parent.setRight(y);
        }
        
        // Update heights and weights
        x.updateHeight();
        x.updateWeight();
        y.updateHeight();
        y.updateWeight();
        
        return y;
    }

    // Print the tree in pre-order traversal
    public String printPreOrder() {
        StringBuilder sb = new StringBuilder();
        printPreOrder(root, sb);
        return sb.toString();
    }

    private void printPreOrder(HBNode node, StringBuilder sb) {
        if (node != null) {
            sb.append(node.getKey()).append(" ");
            printPreOrder(node.getLeft(), sb);
            printPreOrder(node.getRight(), sb);
        }
    }
}