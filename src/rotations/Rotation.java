package rotations;

import node.Node;
import node.Color;

public class Rotation {
    public static <T extends Comparable<T>> Node<T> rotateLeft(Node<T> node){
        Node<T> newRoot = node.right();
        node.right().setParent(node.parent());

        if (newRoot.left() != null) {
            newRoot.left().setParent(node);
        }
        node.setParent(newRoot);

        return newRoot;
    }

    public static <T extends Comparable<T>> Node<T> rotateRight(Node<T> node){
        Node<T> newRoot = node.left();
        node.left().setParent(node.parent());

        if (newRoot.right() != null) {
            newRoot.right().setParent(node);
        }
        node.setParent(newRoot);

        return newRoot;
    }

    public static <T extends Comparable<T>> void pushBlack(Node<T> node){
        node.setColor(node.color().prevColor());
        node.left().setColor(node.leftColor().nextColor());
        node.right().setColor(node.rightColor().nextColor());
    }

    public static <T extends Comparable<T>> void pullBlack(Node<T> node){
        node.setColor(node.color().nextColor());
        node.left().setColor(node.leftColor().prevColor());
        node.right().setColor(node.rightColor().prevColor());
    }

    public static <T extends Comparable<T>> Node<T> flipLeft(Node<T> node){
        swapColors(node, node.right());
        return rotateLeft(node);
    }

    public static <T extends Comparable<T>> Node<T> flipRight(Node<T> node){
        swapColors(node, node.left());
        return rotateRight(node);
    }

    private static <T extends Comparable<T>> void swapColors(Node<T> first, Node<T> second) {
        Color tempColor = first.color();
        first.setColor(second.color());
        second.setColor(tempColor);
    }

}
