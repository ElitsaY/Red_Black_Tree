package Node;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    @Test
    void constructor_WithValue_ColorShouldBeBlack() {
        Node<Integer> n = new Node<>(5);

        assertEquals(Color.Black, n.color(), "root color should be black");
    }

    @Test
    void constructor_WithValue_ChildrenShouldBeBlack() {
        Node<Integer> n = new Node<>(5);

        assertEquals(Color.Black, n.leftColor(), "left child should be black");
        assertEquals(Color.Black, n.rightColor(), "right child should be black");
    }

    @Test
    void constructor_WithValueAndParent_ColorShouldBeRed() {
        Node<Integer> n = new Node<>(5, new Node<>(4));

        assertEquals(Color.Red, n.color(), "node color should be red");
    }

    @Test
    void constructor_ValueIsLessThanParentValue_NodeAddedAsLeftChild() {
        Node<Integer> parent = new Node<>(4);
        Node<Integer> n = new Node<Integer>(3, parent);

        assertEquals(n, parent.left(), "node should be left child of parent");
        assertNull(parent.right(), "right child should be null");
        assertEquals(parent, n.parent());
    }

    @Test
    void constructor_ValueIsGreaterThanParentValue_NodeAddedAsRightChild() {
        Node<Integer> parent = new Node<>(4);
        Node<Integer> n = new Node<Integer>(5, parent);

        assertEquals(n, parent.right(), "node should be right child of parent");
        assertNull(parent.left(), "left child should be null");
        assertEquals(parent, n.parent());
    }

    /*    9
         / \
        6  12
          /
         11
    */
    @Test
    void constructor_CustomTree_CreatesNodeDoesNotChangeTheObject() {
        Node<Integer> node = new Node<Integer>(9, Color.Black,
                new Node<Integer>(6, Color.Black, null, null),
                new Node<Integer>(12, Color.Black,
                        new Node<Integer>(11, Color.Black, null, null),
                        null));
        assertEquals(9, node.value(),
                "The root has a value of 9");
        assertEquals(6, node.left().value(),
                "The left child has value of 6");
        assertEquals(12, node.right().value(),
                "The right child has value of 12");
        assertEquals(11, node.right().left().value(),
                "Root's right child has a left child with value of 11. " +
                        "Root's grandchild is 11 ;) ");
    }

    @Test
    void leftColor_LeftIsNull_Black() {
        Node<Integer> n = new Node<>(3);

        assertEquals(Color.Black, n.leftColor(), "color of nil child should be black");
    }

    @Test
    void setParent_ParentOfNodeToBeNode_Throws() {
        Node<Integer> n = new Node<>(6);

        assertThrows(IllegalArgumentException.class, () -> n.setParent(n));
    }

    @Test
    void setParent_ParentIsNull_ColorIsBlack() {
        Node<Integer> n = new Node<>(7, new Node<>(10));

        n.setParent(null);

        assertNull(n.parent());
        assertEquals(Color.Black, n.color(), "color should be changed to black");
    }

    @Test
    void setParent_ValueIsLessThanParentValue_NodeBecomesLeftChild() {
        Node<Integer> parent = new Node<>(4);
        Node<Integer> n = new Node<>(3);

        n.setParent(parent);

        assertEquals(n, parent.left(), "node should be left child of parent");
        assertEquals(Color.Black, parent.leftColor(), "node color should not be changed");
        assertNull(parent.right(), "right child should be null");
        assertEquals(parent, n.parent());
    }

    @Test
    void setParent_ValueIsGreaterThanParentValue_NodeBecomesRightChild() {
        Node<Integer> parent = new Node<>(4);
        Node<Integer> n = new Node<>(8);

        n.setParent(parent);

        assertEquals(n, parent.right(), "node should be right child of parent");
        assertEquals(Color.Black, parent.rightColor(), "node color should not be changed");
        assertNull(parent.left(), "left child should be null");
        assertEquals(parent, n.parent());
    }



}