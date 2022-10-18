package tree;

import node.Node;
import tree.operations.Operations;

public class RedBlackTree<T extends Comparable<T>> {
    private Node<T> root;
    private int size;

    public boolean contains(T value) {
        return Operations.contains(root, value);
    }

    public void insert(T value) {
        root = Operations.add(root, value);
        size++;
    }

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

    public void clear() {
        root = null;
        size = 0;
    }

    public boolean empty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public int height() {
        return Operations.height(root);
    }

}
