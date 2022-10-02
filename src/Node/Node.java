package Node;

public interface Node <T extends Comparable<T>> {
    T value();

    Node<T> left();
    Node<T> right();
    Node<T> parent();

    Color leftColor();
    Color rightColor();
    Color color();

    void setParent(Node<T> parent);
}
