package rotations;

import node.Color;
import node.Node;
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
        Node<Integer> expected = new Node<>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        Node<Integer> actual = new Node<>(1, Color.Black, null,
                new Node<>(2, Color.Black, null,
                        new Node<>(3, Color.Black)));
        actual = Rotation.rotateLeft(actual);
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
        Node<Integer> expected = new Node<>(10, Color.Black,
                new Node<>(7, Color.Black,
                        new Node<>(5, Color.Black),
                        new Node<>(8, Color.Black)),
                new Node<>(12, Color.Black));

        Node<Integer> actual = new Node<>(7, Color.Black,
                new Node<>(5, Color.Black),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Black),
                        new Node<>(12, Color.Black)));

        actual = Rotation.rotateLeft(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
                   5                                             5
                /     \                                       /     \
               3       7                                     3       9
              / \     / \         -> ROTATE LEFT 7 ->       / \     / \
             1   4   6   9                                 1   4   7   10
            / \         / \                               / \     / \
           0   2       8   10                            0   2   6   8
     */
    @Test
    void rotateLeft_rotateSubtree_changesOnlyTheSubtree() {
        Node<Integer> expected = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(9, Color.Black,
                        new Node<>(7, Color.Black,
                                new Node<>(6),
                                new Node<>(8)),
                        new Node<>(10)));

        Node<Integer> actual = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(7, Color.Black,
                        new Node<>(6),
                        new Node<>(9, Color.Black,
                                new Node<>(8),
                                new Node<>(10))));

        Rotation.rotateLeft(actual.right());
        assertEquals(expected, actual, "Rotate left should change only the subtree");
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
        Node<Integer> expected = new Node<>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        Node<Integer> actual = new Node<>(3, Color.Black,
                new Node<>(2, Color.Black,
                        new Node<>(1, Color.Black), null), null);
        actual = Rotation.rotateRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
    The example is the opposite of the left rotation.

                 10                                         7
                /  \                                       / \
               7    12     -> AFTER RIGHT ROTATION ->     5   10
              / \                                            /  \
             5   8                                          8    12
     */
    @Test
    void rotateRight_RotatesMoreComplexTree_ItIsRightRotated() {
        Node<Integer> expected = new Node<>(7, Color.Black,
                new Node<>(5, Color.Black),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Black),
                        new Node<>(12, Color.Black)));

        Node<Integer> actual = new Node<>(10, Color.Black,
                new Node<>(7, Color.Black,
                        new Node<>(5, Color.Black),
                        new Node<>(8, Color.Black)),
                new Node<>(12, Color.Black));

        actual = Rotation.rotateRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
                     5                                        5
                  /     \                                  /     \
                 3       9                                3       7
                / \     / \     -> ROTATE RIGHT 9 ->     / \     / \
               1   4   7   10                           1   4   6   9
              / \     / \                              / \         / \
             0   2   6   8                            0   2       8   10
     */
    @Test
    void rotateRight_rotateSubtree_changesOnlyTheSubtree() {
        Node<Integer> expected = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(7, Color.Black,
                        new Node<>(6),
                        new Node<>(9, Color.Black,
                                new Node<>(8),
                                new Node<>(10))));

        Node<Integer> actual = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(9, Color.Black,
                        new Node<>(7, Color.Black,
                                new Node<>(6),
                                new Node<>(8)),
                        new Node<>(10)));

        Rotation.rotateRight(actual.right());
        assertEquals(expected, actual, "Rotate right should change only the subtree");
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
        Node<Integer> expected = new Node<>(2, Color.Red,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        Node<Integer> actual = new Node<>(2, Color.Black,
                new Node<>(1, Color.Red),
                new Node<>(3, Color.Red));

        Rotation.pushBlack(actual);
        assertEquals(expected, actual, "The colors are switched");
    }

    /*
        r = red
        b = black
        the opposite of push black

            b                               r
           / \      -> PULL BLACK ->       / \
          r   r                           b   b

     */
    @Test
    void pullBlack_MakesTheColorOfTheNodeRed_ChangedToRed() {
        Node<Integer> expected = new Node<>(2, Color.Black,
                new Node<>(1, Color.Red),
                new Node<>(3, Color.Red));

        Node<Integer> actual = new Node<>(2, Color.Red,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        Rotation.pullBlack(actual);
        assertEquals(expected, actual, "The colors are switched");
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
    void flipLeft_flipAtRoot_treeIsFlipped() {
        Node<Integer> expected = new Node<>(10, Color.Black,
                new Node<>(7, Color.Red,
                        new Node<>(5, Color.Black),
                        new Node<>(8, Color.Black)),
                new Node<>(12, Color.Black));

        Node<Integer> actual = new Node<>(7, Color.Black,
                new Node<>(5, Color.Black),
                new Node<>(10, Color.Red,
                        new Node<>(8, Color.Black),
                        new Node<>(12, Color.Black)));


        actual = Rotation.flipLeft(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
    [_] = red node
     _  = black node
                 5                                             5
              /     \                                       /     \
             3       7                                     3       9
            / \     / \         -> FLIP LEFT 7 ->         / \     /  \
           1   4   6  [9]                                1   4  [7]  10
          / \         / \                               / \     / \
         0   2       8   10                            0   2   6   8
   */
    @Test
    void flipLeft_flipSubtree_treeIsFlipped() {
        Node<Integer> expected = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(9, Color.Black,
                        new Node<>(7, Color.Red,
                                new Node<>(6),
                                new Node<>(8)),
                        new Node<>(10)));

        Node<Integer> actual = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(7, Color.Black,
                        new Node<>(6),
                        new Node<>(9, Color.Red,
                                new Node<>(8),
                                new Node<>(10))));

        Rotation.flipLeft(actual.right());
        assertEquals(expected, actual, "Flip left should change only the subtree");
    }

    /*
     [] = red node
    no brackets = black node

    The example is the opposite of the flip left rotation.

                 10                                                   7
                /  \                                                 / \
              [7]    12      -> AFTER FLIP RIGHT ROTATION ->        5  [10]
              / \                                                      /  \
             5   8                                                    8    12
     */
    @Test
    void flipRight_flipAtRoot_treeIsFlipped() {
        Node<Integer> expected = new Node<>(7, Color.Black,
                new Node<>(5, Color.Black),
                new Node<>(10, Color.Red,
                        new Node<>(8, Color.Black),
                        new Node<>(12, Color.Black)));

        Node<Integer> actual = new Node<>(10, Color.Black,
                new Node<>(7, Color.Red,
                        new Node<>(5, Color.Black),
                        new Node<>(8, Color.Black)),
                new Node<>(12, Color.Black));

        actual = Rotation.flipRight(actual);
        assertEquals(expected, actual, "The tree should be left rotated");
    }

    /*
   [_] = red node
    _  = black node
                 5                                       5
              /     \                                 /     \
             3       9                               3       7
            / \     /  \     -> FLIP LEFT 7 ->      / \     / \
           1   4  [7]  10                          1   4   6  [9]
          / \     / \                             / \         / \
         0   2   6   8                           0   2       8   10
  */
    @Test
    void flipRight_flipSubtree_treeIsFlipped() {
        Node<Integer> expected = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(7, Color.Black,
                        new Node<>(6),
                        new Node<>(9, Color.Red,
                                new Node<>(8),
                                new Node<>(10))));

        Node<Integer> actual = new Node<>(5, Color.Black,
                new Node<>(3, Color.Black,
                        new Node<>(1, Color.Black,
                                new Node<>(0),
                                new Node<>(2)),
                        new Node<>(4)),
                new Node<>(9, Color.Black,
                        new Node<>(7, Color.Red,
                                new Node<>(6),
                                new Node<>(8)),
                        new Node<>(10)));

        Rotation.flipRight(actual.right());
        assertEquals(expected, actual, "Flip left should change only the subtree");
    }
}