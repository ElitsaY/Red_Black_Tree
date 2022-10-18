package tree.operations;

import node.Node;
import node.Color;
import rotations.Rotation;

import java.util.NoSuchElementException;

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
            if(Node.getColor(parentNode.left()) == Color.Black) {
                //ensure left leaning property
                node = Rotation.flipLeft(parentNode);
                parentNode = node.parent();
            }
            if(Node.getColor(parentNode) == Color.Black){
                break; // no red-red edge
            }
            grandParent = parentNode.parent();
            if(Node.getColor(grandParent.right()) == Color.Black) {
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


    private static <T extends Comparable<T>> Node<T> getRoot (Node<T> node) {
        while (!node.isRoot()) {
            node = node.parent();
        }
        return node;
    }

    //finds the leftest node in right subtree
    private static <T extends Comparable<T>> Node<T> getNodeWithOneLeaf(Node<T> node){
        while(node.left() != null) {
            node = node.left();
        }
        return node;
    }

    private static <T extends Comparable<T>> Node<T> fixUpDoubleBlack(Node<T> node){
        while (node.color() == Color.DoubleBlack) {
            if (node.isRoot()) {
                node.setColor(Color.Black);
            }
            else if (Node.getColor(node.parent().left()) == Color.Red) {
                node = removeFixUpCase1(node);
            }
            else if (node.equals(node.parent().left())) {
                node = removeFixUpCase2(node);
            }
            else {
                node = removeFixUpCase3(node);
            }
        }
        return node;
    }
    private static <T extends Comparable<T>> Node<T> removeFixUp(Node<T> node){
        node = fixUpDoubleBlack(node);

        if (!node.isRoot()) { // restore left-leaning property, if needed
            if (Node.getColor(node.parent().right()) == Color.Red &&
                Node.getColor(node.parent().left()) == Color.Black) {
                Rotation.flipLeft(node.parent());
            }
        }
        return getRoot(node);
    }

    private static <T extends Comparable<T>> Node<T> removeFixUpCase1(Node<T> node) {
        Rotation.flipRight(node.parent());
        return node;
    }

    private static <T extends Comparable<T>> Node<T> removeFixUpCase2(Node<T> node) {
        Node<T> parent = node.parent();
        Node<T> sibling = parent.right();
        Node<T> newParent;

        Rotation.pullBlack(parent);
        Rotation.flipLeft(parent);

        newParent = parent.right();
        if (Node.getColor(newParent) == Color.Red) {
            Rotation.rotateLeft(parent);
            Rotation.flipRight(sibling);
            Rotation.pushBlack(newParent);

            if (Node.getColor(sibling.right()) == Color.Red) {
                Rotation.flipLeft(sibling);
            }
            return newParent;
        }

        return sibling;
    }

    private static <T extends Comparable<T>> Node<T> removeFixUpCase3(Node<T> node) {
        Node<T> parent = node.parent();  //w
        Node<T> leftChild = parent.left(); //v

        Rotation.pullBlack(parent);
        if(leftChild == null) {
            return parent;
        }
        leftChild = Rotation.flipRight(parent); //q
        Node<T> newLeftChild = parent.left();

        if(Node.getColor(newLeftChild) == Color.Red) {
            parent = Rotation.rotateRight(parent);
            leftChild = Rotation.flipLeft(leftChild);
            Rotation.pushBlack(newLeftChild);
            return newLeftChild;
        }

        if(Node.getColor(leftChild.left()) == Color.Red){
            Rotation.pushBlack(leftChild); // both leftChild's children are red
            return leftChild;
        }
        else{
            parent = Rotation.flipLeft(leftChild);
            return parent;
        }
    }

    public static <T extends Comparable<T>> Node<T> remove(Node<T> tree, T value) {
        Node<T> node = getNode(tree, value);

        if (node == null || !node.value().equals(value)) {
            return tree;
        }

        if (node.isRoot() && node.isLeaf()) {
            return null;
        }

        return removeNode(tree, node);
    }

    public static <T extends Comparable<T>> Node<T> removeNode(Node<T> tree, Node<T> node) {
        Node<T> toRemove = node;
        Node<T> fixUpStartNode;

        if (node.right() == null) {
            fixUpStartNode = node.left();
        }
        else {
            toRemove = getNodeWithOneLeaf(node.right());
            node.setValue(toRemove.value());
            fixUpStartNode = toRemove.right();
        }

        return toRemove.isLeaf() ? removeLeaf(toRemove) :
                removeNodeWithOneChild(toRemove, fixUpStartNode);
    }

    private static <T extends Comparable<T>> Node<T> removeLeaf(Node<T> leaf) {
        leaf.setColor(leaf.color().nextColor());
        Node<T> root = removeFixUp(leaf);
        leaf.setParent(null);
        return root;
    }

    private static <T extends Comparable<T>> Node<T> removeNodeWithOneChild(Node<T> node, Node<T> child) {
        child.setParent(node.parent());
        child.setColor(child.color().addValue(Node.getColor(node)));
        return removeFixUp(child);
    }
}
