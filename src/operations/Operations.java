package operations;

import node.Node;
import node.Color;
import rotations.Rotation;

public class Operations {
    public static <T extends Comparable<T>> int height(Node<T> tree) {
        if (tree == null) {
            return 0;
        }
        return 1 + Math.max(height(tree.left()), height(tree.right()));
    }

    public static <T extends Comparable<T>> boolean contains(Node<T> tree, T value) {
        Node<T> result = getNode(tree, value);
        return result != null && result.value().equals(value);
    }

    private static <T extends Comparable<T>> Node<T> binaryTreeAdd(Node<T> tree, T value) {
        Node<T> parentNode = getNode(tree, value);
        if (parentNode != null && parentNode.value().equals(value)) {
            //value is already in the tree
            return null;
        }

        return new Node<>(value, parentNode);
    }

    /**
     *
     * @param tree
     * @param value
     * @return if node with such value exists, returns it
     * Else returns the eventual parent of a node with the given value
     * @param <T>
     */
    public static <T extends Comparable<T>> Node<T> getNode(Node<T> tree, T value)
    {
        int compareRes;
        Node<T> prev = null;

        while (tree != null) {
            compareRes = value.compareTo(tree.value());

            if (compareRes == 0) {
                return tree;
            }
            prev = tree;
            tree = compareRes < 0 ? tree.left() : tree.right();
        }

        return prev;
    }

    public static <T extends Comparable<T>> Node<T> add(Node<T> tree, T value) {
        if (tree == null) {
            return new Node<>(value, Color.Black);
        }

        Node<T> newNode = binaryTreeAdd(tree, value);
        if(newNode == null) {   //duplicate element
            return tree;
        }

        //fixing the left leaning property
        return addFixUp(tree, newNode);
    }

    private static <T extends Comparable<T>> Node<T> addFixUp(Node<T> root, Node<T> node) {
        Node<T> parentNode = null;
        Node<T> grandParent = null;

        while(node.color() == Color.Red){
            if (node.isRoot()) {
                node.setColor(Color.Black);
                return node;
            }

            parentNode = node.parent();
            if(getNodeColor(parentNode.left()) == Color.Black) {
                //ensure left leaning property
                node = Rotation.flipLeft(parentNode);
                parentNode = node.parent();
            }
            if(getNodeColor(parentNode) == Color.Black){
                break; // no red-red edge
            }
            grandParent = parentNode.parent();
            if(getNodeColor(grandParent.right()) == Color.Black) {
                grandParent = Rotation.flipRight(grandParent);
                break;
            }
            else {
                Rotation.pushBlack(grandParent);
                //move upwards
                node = grandParent;
            }
        }

        return getRoot(root);
    }

    public static <T extends Comparable<T>> Color getNodeColor(Node<T> node) {
        if (node == null) {
            return Color.Black;
        }
        return node.color();
    }

    private static <T extends Comparable<T>> Node<T> getRoot (Node<T> node) {
        while (!node.isRoot()) {
            node = node.parent();
        }
        return node;
    }

    public static <T extends Comparable<T>> Node<T> remove(Node<T> tree, T value) {
        throw new UnsupportedOperationException();
    }
}
