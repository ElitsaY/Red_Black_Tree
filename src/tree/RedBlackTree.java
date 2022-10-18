package tree;

import node.Node;
import tree.operations.Operations;

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    /**
     * Complexity : O(logN)
     * @param value the value to check whether is contained
     * @return true if the passed value is contained in the tree and false otherwise
     */
    public boolean contains(T value) {
        return Operations.contains(root, value);
    }

    /**
     * Complexity : O(logN)
     * Inserts the value in the tree. If it is already contained doesn't do anything.
     * @param value the value to be inserted
     */
    public void insert(T value) {
        root = Operations.add(root, value);
        size++;
    }

    /**
     * Complexity : O(logN)
     * Removes the value from the tree and decreases its size
     * @param value
     */
    public void erase(T value) {
        Node<T> node = Operations.getNode(root, value);

        if (node == null || !node.value().equals(value)) {
            return;
        }

        if (node.isRoot() && node.isLeaf()) {
            clear();
            return;
        }

        root = Operations.removeNode(root, node);
        --size;
    }

    /**
     * Clears all elements and sets the size to zero
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * @return true if the tree is empty, false otherwise
     */
    public boolean empty() {
        return size == 0;
    }

    /**
     * @return the number of stored elements in the tree
     */
    public int size() {
        return size;
    }

    /**
     * Complexity : O(logN)
     * @return the height of the tree
     */
    public int height() {
        return Operations.height(root);
    }

}
