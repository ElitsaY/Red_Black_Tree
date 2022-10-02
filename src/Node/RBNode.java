package Node;

public class RBNode<T extends Comparable<T>> implements Node<T>{
    private T value;
    private Color color;
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    RBNode(T value) {}

    RBNode(T value, Node<T> parent) {}

    RBNode(T value, Node<T> parent, Node<T> left, Node<T> right) {}

    @Override
    public T value() {
        return value;
    }

    @Override
    public Node<T> left() {
        return left;
    }

    @Override
    public Node<T> right() {
        return right;
    }

    @Override
    public Node<T> parent() {
        return parent;
    }

    @Override
    public Color leftColor() {
        return left.color();
    }

    @Override
    public Color rightColor() {
        return right.color();
    }

    @Override
    public Color color() {
        return color;
    }

    @Override
    public void setParent(Node<T> parent) {
        throw new UnsupportedOperationException("Not implemented");
    }
}
