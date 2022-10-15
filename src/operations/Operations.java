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

    /*
    void splice(Node *u) {
    Node *s, *p;
    if (u->left != nil) {
      s = u->left;
    } else {
      s = u->right;
    }
    if (u == r) {
      r = s;
      p = nil;
    } else {
      p = u->parent;
      if (p->left == u) {
        p->left = s;
      } else {
        p->right = s;
      }
    }
    if (s != nil) {
      s->parent = p;
    }
    n--;
  }
     */

    private static <T extends Comparable<T>> void splice(Node<T> node){
        Node<T> replaceNode = node.left() != null ? node.left() : node.right();

        if (node.isRoot()) {
            if (replaceNode != null) {
                replaceNode.setParent(null);
            }
            //return replaceNode;
        }
        else if (replaceNode != null) {
            replaceNode.setParent(node.parent());
        }
//        else {
//            return null;
//        }

        //return getRoot(replaceNode);
    }

    //finds the leftest node in right subtree
    private static <T extends Comparable<T>> Node<T> getNodeWithOneLeaf(Node<T> node){
        while(node.left() != null) {
            node = node.left();
        }
        return node;
    }

    /*
      void removeFixup(Node *u) {
        while (u->colour > black) {
          if (u == r) {
            u->colour = black;
          } else if (u->parent->left->colour == red) {
            u = removeFixupCase1(u);
          } else if (u == u->parent->left) {
            u = removeFixupCase2(u);
          } else {
            u = removeFixupCase3(u);
          }
        }
        if (u != r) { // restore left-leaning property, if needed
          Node *w = u->parent;
          if (w->right->colour == red && w->left->colour == black) {
            flipLeft(w);
          }
        }
      }
     */

    private static <T extends Comparable<T>> Node<T> fixUpDoubleBlack(Node<T> node){
        while (node.color() == Color.DoubleBlack) {
            if (node.isRoot()) {
                node.setColor(Color.Black);
            }
            else if (getNodeColor(node.parent().left()) == Color.Red) {
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
            if (getNodeColor(node.parent().right()) == Color.Red &&
                getNodeColor(node.parent().left()) == Color.Black) {
                Rotation.flipLeft(node.parent());
            }
        }
        return node;
    }

    private static <T extends Comparable<T>> Node<T> removeFixUpCase1(Node<T> node) {
        Rotation.flipRight(node.parent());
        return node;
    }

    /*
      Node* removeFixupCase2(Node *u) {
        Node *w = u->parent;
        Node *v = w->right;
        pullBlack(w); // w->left
        flipLeft(w); // w is now red
        Node *q = w->right;
        if (q->colour == red) { // q-w is red-red
          rotateLeft(w);
          flipRight(v);
          pushBlack(q);
          if (v->right->colour == red)
            flipLeft(v);
          return q;
        } else {
          return v;
        }
      }
     */
    private static <T extends Comparable<T>> Node<T> removeFixUpCase2(Node<T> node) {
        Node<T> parent = node.parent();
        Node<T> sibling = parent.right();
        Node<T> newParent;

        Rotation.pullBlack(parent);
        Rotation.flipLeft(parent);

        newParent = parent.right();
        if (newParent.color() == Color.Red) {
            Rotation.rotateLeft(parent);
            Rotation.flipRight(sibling);
            Rotation.pushBlack(newParent);

            if (getNodeColor(sibling.right()) == Color.Red) {
                Rotation.flipLeft(sibling);
            }
            return newParent;
        }

        return sibling;
    }

    /*
     Node* removeFixupCase3(Node *u) {
    Node *w = u->parent;
    Node *v = w->left;
    pullBlack(w);
    flipRight(w);            // w is now red
    Node *q = w->left;
    if (q->colour == red) { // q-w is red-red
      rotateRight(w);
      flipLeft(v);
      pushBlack(q);
      return q;
    } else {
      if (v->left->colour == red) {
        pushBlack(v); // both v's children are red
        return v;
      } else { // ensure left-leaning
        flipLeft(v);
        return w;
      }
    }
  }
     */
    private static <T extends Comparable<T>> Node<T> removeFixUpCase3(Node<T> node) {
        Node<T> parent = node.parent();
        Node<T> leftChild = parent.left();

        Rotation.pullBlack(parent);
        parent = Rotation.flipRight(parent);
        Node<T> newLeftChild = parent.left();

        if(newLeftChild.color() == Color.Red) {
            parent = Rotation.rotateRight(parent);
            leftChild = Rotation.flipLeft(leftChild);
            Rotation.pushBlack(newLeftChild);
            return newLeftChild;
        }

        if(leftChild.left().color() == Color.Red){
            Rotation.pushBlack(leftChild); // both leftChild's children are red
            return leftChild;
        }
        else{
            leftChild = Rotation.flipLeft(leftChild);
            return parent;
        }
    }


    /*
    bool remove(T x) {
    Node *u = findLast(x);
    if (u == nil || compare(u->x, x) != 0)
      return false;
    Node *w = u->right;
    if (w == nil) {
      w = u;
      u = w->left;
    } else {
      while (w->left != nil)
        w = w->left;
      u->x = w->x;
      u = w->right;
    }
    splice(w);
    u->colour += w->colour;
    u->parent = w->parent;
    delete w;
    removeFixup(u);
    return true;
  }
     */

    public static <T extends Comparable<T>> Node<T> remove(Node<T> tree, T value) {
        Node<T> node = getNode(tree, value);
        boolean shouldNodeBeNull = false;

        if (node == null || !node.value().equals(value)) {
            return null;
        }

        Node<T> toRemove = node.right();
        if (toRemove == null) {
            toRemove = node;
            //node = node.left();
            if (node.left() == null) {
                shouldNodeBeNull = true;
            }
        }
        else {
            toRemove = getNodeWithOneLeaf(toRemove);
            node.setValue(toRemove.value());
            node = toRemove; // parent
            if (toRemove.right() == null) {
                shouldNodeBeNull = true;
            }
        }
        //tree = splice(toRemove);
        splice(toRemove);

        if (shouldNodeBeNull) {
            node.setColor(Color.Black);
        }
        else {
            node.setColor(node.color().addValue(getNodeColor(toRemove)));
        }
        node.setParent(toRemove.parent());

        removeFixUp(node);
        node.setParent(null);
        return tree;
    }
}
