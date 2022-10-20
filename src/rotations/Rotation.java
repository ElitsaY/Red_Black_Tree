package rotations;

import node.Node;
import node.Color;

public class Rotation {
    /**
     * Performs a left rotation on the node:
     *     b                                            d
     *    / \                                          / \
     *   a   d     -> AFTER LEFT ROTATION  OF b->     b   e
     *      / \                                     /  \
     *     c   e                                   a   c
     * @param node - the node to perform left rotation on
     * @return the new root of the subtree
     * @param <T> the value type of the node
     */
    public static <T extends Comparable<T>> Node<T> rotateLeft(Node<T> node){
        Node<T> newRoot = node.right();
        newRoot.setParent(node.parent());

        if (newRoot.left() != null) {
            newRoot.left().setParent(node);
        }
        node.setParent(newRoot);

        return newRoot;
    }

    /**
     * Performs a right rotation on the node:
     *       d                                        b
     *      / \                                      / \
     *     b   e  -> AFTER RIGHT ROTATION OF d ->   a   d
     *   /  \                                          / \
     *  a   c                                         c   e
     * @param node the node to perform right rotation on
     * @return the new root of the subtree
     * @param <T> the value type of the node
     */
    public static <T extends Comparable<T>> Node<T> rotateRight(Node<T> node){
        Node<T> newRoot = node.left();
        newRoot.setParent(node.parent());

        if (newRoot.right() != null) {
            newRoot.right().setParent(node);
        }
        node.setParent(newRoot);

        return newRoot;
    }

    /**
     * Takes as input a black node u that has two red children and colours u red and its two children black
     *    r                               b
     *   / \     -> PUSHES BLACK ->      / \
     *  b   b                           r   r
     * @param node - the node to perform pushBlack on
     * @param <T> - the value type of the node
     */
    public static <T extends Comparable<T>> void pushBlack(Node<T> node){
        node.setColor(node.color().prevColor());
        node.left().setColor(node.leftColor().nextColor());
        node.right().setColor(node.rightColor().nextColor());
    }

    /**
     * Takes as input a red node u that has two black children and colours u black and its two children red
     *     b                               r
     *    / \      -> PULL BLACK ->       / \
     *   r   r                           b   b
     * @param node - the node to perform pullBlack on
     * @param <T> - the value type of the node
     */
    public static <T extends Comparable<T>> void pullBlack(Node<T> node){
        node.setColor(node.color().nextColor());
        if (node.left() != null) {
            node.left().setColor(node.leftColor().prevColor());
        }
        if (node.right() != null) {
            node.right().setColor(node.rightColor().prevColor());
        }
    }

    /**
     * Takes a node, swaps its colour with its right child's color.
     * Performs a left rotation.
     * @param node - the node to flipLeft
     * @return the new root of the subtree
     * @param <T> - the value type of the node
     */
    public static <T extends Comparable<T>> Node<T> flipLeft(Node<T> node){
        swapColors(node, node.right());
        return rotateLeft(node);
    }

    /**
     *  Takes a node, swaps its colour with its left child's color.
     *  Performs a right rotation.
     *  @param node - the node to flipLeft
     *  @return the new root of the subtree
     *  @param <T> - the value type of the node
     */
    public static <T extends Comparable<T>> Node<T> flipRight(Node<T> node){
        swapColors(node, node.left());
        return rotateRight(node);
    }

    /**
     * Swaps the colors of the two nodes
     * @param first - first node to swap colours with
     * @param second - second node to swap colours
     * @param <T> - the value type of the node
     */
    private static <T extends Comparable<T>> void swapColors(Node<T> first, Node<T> second) {
        Color tempColor = first.color();
        first.setColor(Node.getColor(second));
        if (second != null) {
            second.setColor(tempColor);
        }
    }

}
