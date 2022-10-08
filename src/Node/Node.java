package Node;

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
        this.parent = parent;
        parent.setChild(this);
        color = Color.Red;
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

    public Color color() {
        return color;
    }

    public void setParent(Node<T> parent) {
        if (parent == this) {
            throw new IllegalArgumentException("can't set parent of this to this");
        }

        this.parent = parent;
        if (parent == null) {
            color = Color.Black;
            return;
        }

        parent.setChild(this);
    }

    private void setChild(Node<T> child) {
        int compareRes = child.value.compareTo(value);
        if (compareRes < 0) {
            left = child;
        }
        else if (compareRes > 0) {
            right = child;
        }
    }
}
