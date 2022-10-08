package Rotations;

import Node.Color;
import Node.Node;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RotationTest {

    /*
             1                                        2
              \                                     /   \
               2     -> AFTER LEFT ROTATION ->     1     3
                \
                 3
     */
    @Test
    void rotateLeft_RotatesVine_ItIsLeftRotated() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<Integer>(1, Color.Black),
                new Node<Integer>(3, Color.Black));

        Node<Integer> actual = new Node<Integer>(1, Color.Black, null,
                new Node<Integer>(2, Color.Black, null,
                        new Node<Integer>(3, Color.Black)));
        Rotation.rotateLeft(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
                   7                                            10
                  / \                                          /  \
                 5   10       -> AFTER LEFT ROTATION ->       7    12
                    /  \                                     / \
                   8    12                                  5   8
     */

    @Test
    void rotateLeft_RotatesMoreComplexTree_ItIsLeftRotated() {
        Node<Integer> expected = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));

        Node<Integer> actual = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color. Black,
                        new Node<Integer>(8, Color.Black),
                        new Node<Integer>(12, Color.Black)));

        Rotation.rotateLeft(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }


    /*
                 3                                      2
                /                                     /   \
               2      -> AFTER RIGHT ROTATION ->     1     3
              /
             1
     */
    @Test
    void rotateRight_RotatesVine_ItIsRightRotated() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<Integer>(1, Color.Black),
                new Node<Integer>(3, Color.Black));

        Node<Integer> actual = new Node<Integer>(3, Color.Black,
                new Node<Integer>(2, Color.Black,
                        new Node<Integer>(1, Color.Black), null), null);
        Rotation.rotateRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
    The example is the opposite of the left rotation.

                   7                                            10
                  / \                                          /  \
                 5   10       <- AFTER RIGHT ROTATION <-      7    12
                    /  \                                     / \
                   8    12                                  5   8
     */

    @Test
    void rotateRight_RotatesMoreComplexTree_ItIsRightRotated() {
        Node<Integer> expected = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color. Black,
                        new Node<Integer>(8, Color.Black),
                        new Node<Integer>(12, Color.Black)));

        Node<Integer> actual = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));

        Rotation.rotateRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
       r = red
       b = black

            r                               b
           / \     -> PUSHES BLACK ->      / \
          b   b                           r   r
     */

    @Test
    void pushBlack_MakesTheColorOfTheNodeBlack_ChangedToBlack() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<Integer>(1, Color.Red),
                new Node<Integer>(3, Color.Red));

        Node<Integer> actual = new Node<Integer>(2, Color.Red,
                new Node<Integer>(1, Color.Black),
                new Node<Integer>(3, Color.Black));

        Rotation.pushBlack(actual);
        assertEquals(expected,actual, "The colors are switched" );
    }

    /*
        r = red
        b = black
        the opposite of push black

            b                               r
           / \     -> PUSHES BLACK ->      / \
          r   r                           b   b

     */

    @Test
    void pullBlack_MakesTheColorOfTheNodeRed_ChangedToRed() {
        Node<Integer> expected = new Node<Integer>(2, Color.Red,
                new Node<Integer>(1, Color.Black),
                new Node<Integer>(3, Color.Black));


        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<Integer>(1, Color.Red),
                new Node<Integer>(3, Color.Red));

        Rotation.pullBlack(actual);
        assertEquals(expected,actual, "The colors are switched" );
    }

    /*
    [] = red node
    no brackets = black node

                  7                                            10
                 / \                                          /  \
                5  [10]     -> AFTER FlIP LEFT ROTATION->   [7]   12
                   /  \                                     / \
                  8    12                                  5   8
     */

    @Test
    void flipLeft() {
        Node<Integer> expected = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Red,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));

        Node<Integer> actual = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color.Red,
                        new Node<Integer>(8, Color.Black),
                        new Node<Integer>(12, Color.Black)));


        Rotation.flipLeft(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
     [] = red node
    no brackets = black node

    The example is the opposite of the flip left rotation.

                   7                                                 10
                  / \                                               /  \
                 5  [10]       <- AFTER FLIP RIGHT ROTATION <-    [7]    12
                    /  \                                          / \
                   8    12                                       5   8
              - expected -                                         - actual -
     */

    @Test
    void flipRight() {
        Node<Integer> expected = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color.Red,
                        new Node<Integer>(8, Color.Black),
                        new Node<Integer>(12, Color.Black)));

        Node<Integer> actual = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Red,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Black)),
                new Node<Integer>(12, Color.Black));

        Rotation.flipRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }
}