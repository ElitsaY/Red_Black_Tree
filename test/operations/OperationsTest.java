package operations;

import node.Color;
import node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void height_nullTree_isZero() {
        assertEquals(0, Operations.height(null), "Null tree has zero height");
    }

    @Test
    void height_singleNode_isOne() {
        assertEquals(1, Operations.height(new Node(3)), "Single node's height is 1");
    }

    /*
                7
               / \
              5   10
                 /  \
                8    12
     */
    @Test
    void height_ofTreeWithLongestPathOfThree_isThree() {
        Node<Integer> tree = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));
        assertEquals(3, Operations.height(tree),
                "The height is equal to the longest path(3) from the root to a leaf");
    }

    //add contains tests with complex type
    @Test
    void contains_nullTree_DoesNotContainSeven()
    {
        assertFalse(Operations.contains(null, 7), "Null tree doesn't contain seven");
    }

    @Test
    void contains_singleNode_containsNodeValue() {
        assertTrue(Operations.contains(new Node<Integer>(7), 7),
                "Single node with value 7 should contain 7");
    }

    /*
                   7
                  / \
                 5   10
                    /  \
                   8    12
        */
    @Test
    void contains_treeWithLeafTwelve_containsTwelve() {
        Node<Integer> tree = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color. Black,
                        new Node<Integer>(8, Color.Black),
                        new Node<Integer>(12, Color.Black)));
        assertTrue(Operations.contains(tree, 12), "Tree with leaf 12 should contain 12");
    }


    /*
                   7
                  / \
                 5   10
                    /  \
                   8    12
        */
    @Test
    void contains_treeWithoutEleven_returnsFalse() {
        Node<Integer> tree = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));
        assertFalse(Operations.contains(tree, 11), "Tree without leaf 11 should not contain 11");
    }


    @Test
    void add() {
    }

    @Test
    void remove() {
    }
}