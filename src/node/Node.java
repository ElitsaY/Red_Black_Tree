package node;

import java.util.Objects;

public class Node<T extends Comparable<T>> {
    private T value;
    private Color color;
    private Node<T> left;
    private Node<T> right;
    private Node<T> parent;

    /**
     * Constructs a black node with the specified value
     *
     * @param value value of the node
     */
    public Node(T value) {
        this.value = value;
        this.color = Color.Black;
    }

    /**
     * Constructs a red node with the specified value and parent
     *
     * @param value  value of the node
     * @param parent parent of the node
     */
    public Node(T value, Node<T> parent) {
        this.value = value;
        setParent(parent);
        color = Color.Red;
    }

    /**
     * Constructs a new node with the given value, color and left and right child.
     *
     * @param value value of node
     * @param color color of node
     * @param left  left child of node
     * @param right right child of node
     */
    public Node(T value, Color color, Node<T> left, Node<T> right) {
        this.value = value;
        this.color = color;
        setLeftChild(left);
        setRightChild(right);
    }

    /**
     * Constructs a node with the specified value and color
     *
     * @param value value of the node
     * @param color color of the node
     */
    public Node(T value, Color color) {
        this.value = value;
        this.color = color;
    }

    /**
     * Sets the value of the node to the specified value
     *
     * @param value new value of the node
     */
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return value of the node
     */
    public T value() {
        return value;
    }

    /**
     * @return left child of the node
     */
    public Node<T> left() {
        return left;
    }

    /**
     * @return right child of the node
     */
    public Node<T> right() {
        return right;
    }

    /**
     * @return parent of the node
     */
    public Node<T> parent() {
        return parent;
    }

    /**
     * Returns the color of the left child. If left child is null, returns black.
     *
     * @return color of the left child
     */
    public Color leftColor() {
        return Node.getColor(left);
    }

    /**
     * Returns the color of the right child. If right child is null, returns black.
     *
     * @return color of the right child
     */
    public Color rightColor() {
        return Node.getColor(right);
    }

    /**
     * Returns the color of the node. If node is null, returns black
     *
     * @param node node to get the color of
     * @param <T>  type of the value
     * @return color of the node
     */
    public static <T extends Comparable<T>> Color getColor(Node<T> node) {
        if (node == null) {
            return Color.Black;
        }
        return node.color();
    }

    /**
     * Returns the root of the tree which the node is in.
     *
     * @return root of the tree
     */
    public Node<T> getRoot() {
        Node<T> temp = this;
        while (!temp.isRoot()) {
            temp = temp.parent();
        }
        return temp;
    }

    /**
     * @return color of node
     */
    public Color color() {
        return color;
    }

    /**
     * Sets the color of node to the specified color.
     *
     * @param color new color of node
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * Sets the parent of the node to newParent.
     * If the old parent isn't null, node is detached from old parent.
     *
     * @param newParent new parent of node
     * @throws IllegalArgumentException if newParent is equal to this
     */
    public void setParent(Node<T> newParent) {
        if (newParent == this) {
            throw new IllegalArgumentException("can't set parent of this to this");
        } else if (newParent == parent) {
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

    /**
     * Checks if node is root of a tree - parent if node is null.
     *
     * @return true if parent of node is null, false otherwise
     */
    public boolean isRoot() {
        return parent() == null;
    }

    /**
     * Checks if node is a leaf(both children are null).
     *
     * @return true if node is a leaf, false otherwise
     */
    public boolean isLeaf() {
        return left == null && right == null;
    }


    private void setChild(Node<T> child) {
        int compareRes = child.value().compareTo(value);
        if (compareRes < 0) {
            if (left != null) {
                left.parent = null;
            }
            left = child;
        } else if (compareRes > 0) {
            if (right != null) {
                right.parent = null;
            }
            right = child;
        }
    }

    private void removeChildByValue(T childValue) {
        if (left != null && Objects.equals(childValue, left.value)) {
            left = null;
        } else if (right != null && Objects.equals(childValue, right.value)) {
            right = null;
        }
    }

    private void setLeftChild(Node<T> child) {
        left = child;
        if (child != null) {
            child.parent = this;
        }
    }

    private void setRightChild(Node<T> child) {
        right = child;
        if (child != null) {
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
