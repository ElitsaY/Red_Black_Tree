package operations;

import node.Node;

public class Operations {
    public static <T extends Comparable<T>> int height(Node<T> tree) {
        if (tree == null) {
            return 0;
        }
        return 1 + Math.max(height(tree.left()), height(tree.right()));
    }

    public static <T extends Comparable<T>> boolean contains(Node<T> tree, T value) {
        int compareRes;

        while (tree != null) {
            compareRes = value.compareTo(tree.value());

            if (compareRes == 0) {
                return true;
            }
            tree = compareRes < 0 ? tree.left() : tree.right();
        }

        return false;
    }

    public static <T extends Comparable<T>> boolean add(Node<T> tree, T value) {
        throw new UnsupportedOperationException();
    }

    public static <T extends Comparable<T>> boolean remove(Node<T> tree, T value) {
        throw new UnsupportedOperationException();
    }
}
