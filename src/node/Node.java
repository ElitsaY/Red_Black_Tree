package node;

import java.util.Objects;

public class Node<T extends Comparable<T>>{
    private T value;
    private Color color;
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    public Node(T value) {
        this.value = value;
        this.color = Color.Black;
    }

    public Node(T value, Node<T> parent) {
        this.value = value;
        setParent(parent);
        color = Color.Red;
    }

    public Node(T value, Color color, Node<T> left, Node<T> right) {
        this.value = value;
        this.color = color;
        setLeftChild(left);
        setRightChild(right);
    }

    public Node(T value, Color color) {
        this.value = value;
        this.color = color;
    }

    public void setValue(T value){
        this.value = value;
    }
    public T value() {
        return value;
    }

    public Node<T> left() {
        return left;
    }

    public Node<T> right() {
        return right;
    }

    public Node<T> parent() {
        return parent;
    }

    public Color leftColor() {
        if (left == null) {
            return Color.Black;
        }
        return left.color();
    }

    public Color rightColor() {
        if (right == null) {
            return Color.Black;
        }
        return right.color();
    }

    public static <T extends Comparable<T>> Color getColor(Node<T> node) {
        if (node == null) {
            return Color.Black;
        }
        return node.color();
    }


    public Color color() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setParent(Node<T> newParent) {
        if (newParent == this) {
            throw new IllegalArgumentException("can't set parent of this to this");
        }
        else if (newParent == parent) {
            return;
        }

        if (parent != null) {
            parent.removeChildByValue(value);
        }

        parent = newParent;
        if (parent != null) {
            parent.setChild(this);
        }
    }

    public boolean isRoot() {
        return parent() == null;
    }

    public boolean isLeaf() { return left == null && right == null;}


    private void setChild(Node<T> child) {
        int compareRes = child.value().compareTo(value);
        if (compareRes < 0) {
            if (left != null) {
                left.parent = null;
            }
            left = child;
        }
        else if (compareRes > 0) {
            if (right != null) {
                right.parent = null;
            }
            right = child;
        }
    }

    private void removeChildByValue(T childValue) {
        if (left != null && Objects.equals(childValue, left.value)) {
            left = null;
        }
        else if (right != null && Objects.equals(childValue, right.value)) {
            right = null;
        }
    }

    public void setLeftChild(Node<T> child){
        left = child;
        if(child != null){
            child.parent = this;
        }
    }

    public void setRightChild(Node<T> child){
        right = child;
        if(child != null){
            child.parent = this;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Node<?> node = (Node<?>) o;
        return Objects.equals(value, node.value) && color == node.color &&
                Objects.equals(left, node.left) && Objects.equals(right, node.right);
    }
    @Override
    public int hashCode() {
        return Objects.hash(value, color, left, right);
    }

}
