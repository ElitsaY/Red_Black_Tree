package tree.operations;

import node.Color;
import node.Node;
import org.junit.jupiter.api.Test;
import tree.operations.Operations;

import static org.junit.jupiter.api.Assertions.*;

class OperationsTest {

    @Test
    void height_nullTree_isZero() {
        assertEquals(0, Operations.height(null), "Null tree has zero height");
    }

    @Test
    void height_singleNode_isOne() {
        assertEquals(1, Operations.height(new Node<>(3)), "Single node's height is 1");
    }

    /*
        [] = red node
        _  = black node
                7
               / \
              5   10
                 /  \
               [8]  [12]
     */
    @Test
    void height_ofTreeWithLongestPathOfThree_isThree() {
        Node<Integer> tree = new Node<>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Black) ,
                        new Node<Integer>(8, Color.Red)),
                new Node<Integer>(12, Color.Red));
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
       [] = red node
        _ = black node

                   7
                  / \
                 5   10
                    /  \
                  [8]  [12]
        */
    @Test
    void contains_treeWithLeafTwelve_containsTwelve() {
        Node<Integer> tree = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color. Black,
                        new Node<Integer>(8, Color.Red),
                        new Node<Integer>(12, Color.Red)));
        assertTrue(Operations.contains(tree, 12), "Tree with leaf 12 should contain 12");
    }


    /*
         [] = red node
          _ = black node

                   10
                  /  \
                 7   12
                / \
              [8] [12]
        */
    @Test
    void contains_treeWithoutEleven_returnsFalse() {
        Node<Integer> tree = new Node<Integer>(10, Color.Black,
                new Node<Integer>(7, Color.Black,
                        new Node<Integer>(5, Color.Red),
                        new Node<Integer>(8, Color.Red)),
                new Node<Integer>(12, Color.Black));
        assertFalse(Operations.contains(tree, 11), "Tree without leaf 11 should not contain 11");
    }


    @Test
    void add_addingTheRoot_theRootShouldBeBlack() {
        Node<Integer> actual = null;
        Node<Integer> expected = new Node<Integer>(3);
        actual = Operations.add(actual, 3);
        assertEquals(expected, actual,
                "The tree root of actual should have been changed to black");
    }

    /*
         [] = red
          _ = black

             3                                   2
            /       -> AFTER ADDING 1 ->       /   \
          [2]                                [1]   [3]
     */
    @Test
    void add_addingToTheLeft_theTreeIsBalancedWithTwoRedDescendants() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<Integer>(1, Color.Red),
                new Node<Integer>(3, Color.Red));

        Node<Integer> actual = new Node<Integer>(3, Color.Black,
                new Node<Integer>(2, Color.Red),
                null);

        actual = Operations.add(actual, 1);
        assertEquals(expected, actual, "Actual is left rotated and has 2 red descendants" );
    }



    /*
            LEFT LEANING = IF LEFT IS BLACK THEN RIGHT IS ALSO BLACK <3

            1 -> AFTER ADDING 2 ->      2
                                       /
                                     [1]
     */
   @Test
   void add_addToTheRight_LeftLeaningPropertyMaintained() {
        Node<Integer> actual = new Node<Integer>(1, Color.Black);

        Node<Integer> expected = new Node<Integer>(2, Color.Black,
            new Node<Integer>(1, Color.Red), null);

        actual = Operations.add(actual, 2);

        assertEquals(expected, actual,
                "The tree is rotated and recolored according to the left leaning property");
   }

    /*
        [] = red node
        _ = black node

                    10
                   /  \
                  7    12
                /   \
              [5]   [8]
    */
    @Test
    void add_addDuplicateValue_TheValueIsNotAdded(){
       Node<Integer> actual = new Node<Integer>(10, Color.Black,
               new Node<Integer>(7, Color.Black,
                       new Node<Integer>(5, Color.Red) ,
                       new Node<Integer>(8, Color.Red)),
               new Node<Integer>(12, Color.Black));

       Node<Integer> expected = new Node<Integer>(10, Color.Black,
               new Node<Integer>(7, Color.Black,
                       new Node<Integer>(5, Color.Red) ,
                       new Node<Integer>(8, Color.Red)),
               new Node<Integer>(12, Color.Black));

       actual = Operations.add(actual, 10);
       
       assertEquals(expected, actual, "Duplicate elements do not change the tree.");

   }

       /*
         [] = red
          _ = black

             2                                    2
            /  \     -> AFTER ADDING 3 ->       /   \
          [1]  [4]                             1     4
                                                    /
                                                  [3]
     */
    @Test
    void add_addingToTheRightWithRedParent_PushBlackAndChangeColorOfRoot() {
        Node<Integer> expected = new Node<>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(4, Color.Black,
                        new Node<>(3, Color.Red), null));

        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Red),
                new Node<>(4, Color.Red));

        actual = Operations.add(actual, 3);
        assertEquals(expected, actual,
                "After push black operation of the actual tree, only the newly added node is red");
    }


    /*
        [] = red node
         _ = black node

        5                                     5
       / \                                   / \
     [3] [6]        -> AFTER ADDING 1 ->    3   6
                                           /
                                         [1]
     */
    @Test
    void add_AddingToTheLeftWithRedParent_PushBlackAndChangeColorOfRoot() {
        Node<Integer> actual = new Node<Integer>(5, Color.Black,
                new Node<Integer> (3, Color.Red),
                new Node<Integer>(6, Color.Red));

        Node<Integer> expected = new Node<Integer>(5, Color.Black,
                new Node<Integer> (3, Color.Black,
                        new Node<Integer>(1, Color.Red),
                        null),
                new Node<Integer>(6, Color.Black));

        actual = Operations.add(actual, 1);

        assertEquals(expected, actual,
                "After push black operation of the actual tree. Only the newly added node is red");
    }


    /*
         Adding elements as following: 32, 24, 18, 16, 12, 8, 4
         []  = red node
          _  = black node

                 24
               /    \
            [16]     32
            /  \
           8    18
          /  \
       [4]   [12]
     */

    @Test
    void add_AddingMultiple_theTreeIsValidRedBlackTree() {
        Node<Integer> expected = new Node<Integer>(24, Color.Black,
                new Node<Integer>(16, Color.Red,
                        new Node<Integer>(8, Color.Black,
                                new Node<Integer>(4, Color.Red),
                                new Node<Integer>(12, Color.Red)),
                        new Node<Integer>(18, Color.Black)),
                new Node<Integer>(32, Color.Black));

        //starting only with the root
        Node<Integer> actual = new Node<Integer>(32, Color.Black);
        actual = Operations.add(actual, 24);
        actual = Operations.add(actual, 18);
        actual = Operations.add(actual, 16);
        actual = Operations.add(actual, 12);
        actual = Operations.add(actual, 8);
        actual = Operations.add(actual, 4);

        assertEquals(expected, actual,
                "After multiples adds the actual tree is valid RB tree. All operations tested");
    }


    /*
       [] = red node
        _ = black node

               5                                    5
              / \                                  / \
             3   6    -> AFTER REMOVING 1 ->      3   6
            /
          [1]
    */
    @Test
    void remove_redLeaf_leavesTheTreeBalanced() {
        Node<Integer> expected = new Node<Integer>(5, Color.Black,
                new Node<Integer> (3, Color.Black),
                new Node<Integer>(6, Color.Black));

        Node<Integer> actual = new Node<Integer>(5, Color.Black,
                new Node<Integer> (3, Color.Black,
                        new Node<Integer>(1, Color.Red),
                        null),
                new Node<Integer>(6, Color.Black));

        actual = Operations.remove(actual, 1);

        assertEquals(expected, actual,
                "Removing the leaf doesn't violate any property!");
    }

    /*
            2                                      3
           / \          -> AFTER REMOVING 2 ->    /
          1   3                                 [1]
     */

    @Test
    void remove_root_theTreesBalanced() {
        Node<Integer> expected = new Node<Integer>(3, Color.Black,
                new Node<>(1, Color.Red),
                null);

        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        actual = Operations.remove(actual, 2);
        assertEquals(expected, actual, "Removing root should leave the tree balanced");
    }



    /*
            2                                      2
           / \          -> AFTER REMOVING 3 ->    /
          1   3                                 [1]
     */
    @Test
    void remove_rightLeaf_onlyTheReferenceShouldChange() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Red),
                null);

        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        actual = Operations.remove(actual, 3);

        assertEquals(expected, actual,
                "When removing the right leaf we don't violate any other property and the root is balanced");
    }

    /*
            2
           /        -> AFTER REMOVING 2 ->    1
          1
     */
    @Test
    void remove_rootWithLeftChildOnly_treeIsBalanced() {
        Node<Integer> expected = new Node<Integer>(1, Color.Black);

        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Black),
                null);

        actual = Operations.remove(actual, 2);

        assertEquals(expected, actual);
    }

    /*
            2
             \       -> AFTER REMOVING 2 ->    3
              3
     */
    @Test
    void remove_rootWithRightChildOnly_treeIsBalanced() { // test calls fixUpCase3
        Node<Integer> expected = new Node<Integer>(3, Color.Black);

        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                null,
                new Node<>(3, Color.Black));

        actual = Operations.remove(actual, 2);

        assertEquals(expected, actual);
    }


    @Test
    void remove_rootWithNoChildren_treeIsNull() {
        Node<Integer> expected = null;

        Node<Integer> actual = new Node<>(1);

        actual = Operations.remove(actual, 1);

        assertEquals(expected, actual, "tree should be null after root is removed");
    }

    /*
            5                                       5
           / \                                     / \
         [4]  6      -> AFTER REMOVING 4 ->       3   6
         /
        3
    */
    @Test
    void remove_removeNodeWithLeftChildOnly_treeIsBalanced() {
        Node<Integer> expected = new Node<Integer>(5, Color.Black,
                new Node<>(3, Color.Black),
                new Node<>(6, Color.Black));

        Node<Integer> actual = new Node<Integer>(5, Color.Black,
                new Node<>(4, Color.Red,
                        new Node<>(3, Color.Black),
                        null),
                new Node<>(6, Color.Black));

        actual = Operations.remove(actual, 4);

        assertEquals(expected, actual);
    }

    /*
            5                                    5
           / \                                  / \
          4  [6]   ->  AFTER REMOVING 6 ->     4   7
              \
              7
     */
    @Test
    void remove_removeNodeWithRightChildOnly_treeIsBalanced() { // test calls fixUp case 3
        Node<Integer> expected = new Node<>(5, Color.Black,
                new Node<>(4, Color.Black),
                new Node<>(7, Color.Black));

        Node<Integer> actual = new Node<>(5, Color.Black,
                new Node<>(4, Color.Black),
                new Node<>(6, Color.Red,
                    null,
                    new Node<>(7, Color.Black)));

        actual = Operations.remove(actual, 6);

        assertEquals(expected, actual);
    }


   /*
    [] = red node
    _ = black node

            7                                         8
           / \                                       / \
          5   10      -> AFTER REMOVING 7  ->       5   12
             /  \                                      /
           [8]  [12]                                 [10]
    */

    @Test
    void remove_rootOfLargerTree_theTreeIsBalanced() {
        Node<Integer> expected = new Node<Integer>(8, Color.Black,
                new Node<>(5, Color.Black),
                new Node<>(12, Color.Black,
                        new Node<>(10, Color.Red),
                        null));

        Node<Integer> actual = new Node<Integer>(7, Color.Black,
                new Node<Integer>(5, Color.Black),
                new Node<Integer>(10, Color. Black,
                        new Node<Integer>(8, Color.Red),
                        new Node<Integer>(12, Color.Red)));

        actual = Operations.remove(actual, 7);
        assertEquals(expected, actual, "");
    }

    /*
                6                                               6
            /       \                                       /       \
           4         10                                    4         10
         /   \     /    \    -> AFTER REMOVING 11 ->     /   \     /    \
       [2]    5  [8]     12                            [2]    5  [8]     12
       / \       / \     /                             / \       / \
      1   3     7   9  [11]                           1   3     7   9
     */
    @Test
    void remove_complexTreeRemoveRedLeaf_DoesNotChangeTheStructure() {
        Node<Integer> expected = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black)));
        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        actual = Operations.remove(actual, 11);
        assertEquals(expected, actual);
    }

    /*
                6                                               7
            /       \                                       /       \
           4         10                                    4         10
         /   \     /    \    -> AFTER REMOVING 6 ->      /   \     /    \
       [2]    5  [8]     12                            [2]    5   9      12
       / \       / \     /                             / \       /       /
      1   3     7   9  [11]                           1   3    [8]     [11]
     */
    @Test
    void remove_complexTreeRemoveRootCaseTwo_removalIsSuccessful() {
        Node<Integer> expected = new Node<>(7, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(9, Color.Black,
                                new Node<>(8, Color.Red),
                                null),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));
        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        actual = Operations.remove(actual, 6);
        assertEquals(expected, actual);
    }

    /*
               6                                               6
           /       \                                       /       \
          4         10                                    4         10
        /   \     /    \    -> AFTER REMOVING 9 ->      /   \     /    \
      [2]    5  [8]     12                            [2]    5   8      12
      / \       / \     /                             / \       /       /
     1   3     7   9  [11]                           1   3    [7]     [11]
    */
    @Test
    void remove_complexTreeRemoveBlackLeaf_DoesNotChangeTheStructure() {
        Node<Integer> expected = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Black,
                                new Node<>(7, Color.Red),
                                null),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));
        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        actual = Operations.remove(actual, 9);
        assertEquals(expected, actual);
    }

    @Test
    void remove_valueNotInTree_noChange() {
        Node<Integer> expected = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));
        Node<Integer> actual = new Node<Integer>(2, Color.Black,
                new Node<>(1, Color.Black),
                new Node<>(3, Color.Black));

        actual = Operations.remove(actual, 5);
        assertEquals(expected, actual,
                "Removing element that is not in the tree shouldn't do anything");
    }


    /*
               6                                               6
           /       \                                       /       \
          4         10                                    2         10
        /   \     /    \    -> AFTER REMOVING 4 ->      /   \     /    \
      [2]    5  [8]     12                             1    5  [8]     12
      / \       / \     /                                  /   / \     /
     1   3     7   9  [11]                               [3]  7  9   [11]
    */
    @Test
    void remove_complexTreeRemoveBlackNodeWithBlackChild_FixUpCases1and3() {
        Node<Integer> expected = new Node<>(6, Color.Black,
                new Node<>(2, Color.Black,
                        new Node<>(1, Color.Black),
                        new Node<>(5, Color.Black,
                                new Node<Integer>(3, Color.Red),
                                null)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        actual = Operations.remove(actual, 4);
        assertEquals(expected, actual);
    }


    /*

               6                                               6
           /       \                                       /       \
          4         10                                    4          10
        /   \     /    \    -> AFTER REMOVING 12 ->      / \       /    \
      [2]    5  [8]     12                             [2]  5    [8]    11
      / \       / \     /                              / \       / \
     1   3     7   9  [11]                            1   3     7   9

     */

    @Test
    void remove_removeNodeWithLeftChild_theTreeIsBalanceAfterSplice(){
        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(9, Color.Black)),
                        new Node<>(12, Color.Black,
                                new Node<>(11, Color.Red),
                                null)));

        Node<Integer> expected = new Node<Integer>(6, Color.Black,
                new Node<Integer>(4, Color.Black,
                        new Node<Integer>(2, Color.Red,
                                new Node<Integer>(1, Color.Black),
                                new Node<Integer>(3, Color.Black)),
                        new Node<Integer>(5, Color.Black)),
                new Node<Integer>(10,Color.Black,
                        new Node<Integer>(8, Color.Red,
                                new Node<Integer>(7, Color.Black),
                                new Node<Integer>(9, Color.Black)),
                        new Node<Integer>(11, Color.Black)));

        actual = Operations.remove(actual, 12);
        assertEquals(expected, actual, "The tree is balanced");
    }



    /*
             10                                           8
           /   \                                         / \
          5    11                                       5   10
         / \    \        -> AFTER REMOVING 11 ->       / \    \
       [3] [8]  12                                   [3]   6   12
       /   /                                         /
      1   6                                         1
    */
    @Test
    void remove_fixUpCase3RedRed_treeIsBalanced() {
        Node<Integer> expected = new Node<Integer>(8,Color.Black,
                new Node<Integer>(5, Color.Black,
                        new Node<Integer>(3, Color.Red,
                                new Node<>(1),
                                null),
                        new Node<>(6, Color.Black)),
                new Node<Integer>(10, Color.Black,
                        null,
                        new Node<>(12, Color.Black)));

        Node<Integer> actual = new Node<Integer>(10, Color.Black,
                new Node<Integer>(5, Color.Black,
                        new Node<Integer>(3, Color.Red,
                                new Node<>(1, Color.Black),
                                null),
                        new Node<Integer>(8, Color.Red,
                                new Node<>(6, Color.Black),
                                null)),
                new Node<Integer>(11,Color.Black,
                        null,
                        new Node<Integer>(12, Color.Black)));

        actual = Operations.remove(actual, 11);

        assertEquals(expected, actual);
    }

    /*
                 6                                                 7
            /        \                                        /        \
           4          15                                     4          15
         /   \      /     \    -> AFTER REMOVING 6 ->      /   \      /     \
       [2]    5   [9]      14                            [2]    5   [10]     14
       / \        / \      /                             / \        / \      /
      1   3     7    11  [13]                           1   3      9   12  [13]
                    /  \                                              /
                 [10]  [12]                                         [11]
     */
    @Test
    void remove_rootFixUpCase2RedParentAndChild_removalIsSuccessful()
    {
        Node<Integer> expected = new Node<>(7, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(15, Color.Black,
                        new Node<>(10, Color.Red,
                                new Node<>(9, Color.Black),
                                new Node<>(12, Color.Black,
                                        new Node<>(11, Color.Red),
                                        null)),
                        new Node<>(14, Color.Black,
                                new Node<>(13, Color.Red),
                                null)));

        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Black,
                        new Node<>(2, Color.Red,
                                new Node<>(1, Color.Black),
                                new Node<>(3, Color.Black)),
                        new Node<>(5, Color.Black)),
                new Node<>(15, Color.Black,
                        new Node<>(9, Color.Red,
                                new Node<>(7, Color.Black),
                                new Node<>(11, Color.Black,
                                    new Node<>(10, Color.Red),
                                    new Node<>(12, Color.Red))),
                        new Node<>(14, Color.Black,
                                new Node<>(13, Color.Red),
                                null)));

        actual = Operations.remove(actual, 6);

        assertEquals(expected, actual);
    }

/*

               6                                              6
           /       \                                        /    \
         [4]        10                                   [3]      10
        /   \     /   \    -> AFTER REMOVING 12 ->      / \     /  \
       2    5   [8]    [12]                            2   4  [8]  [12]
      / \                                              /
    [1] [3]                                           [1]


*/
    @Test
    void remove_blackRightChild_fixUpCase3SiblingWithRedChild() {
        Node<Integer> expected = new Node<>(6, Color.Black,
                new Node<>(3, Color.Red,
                        new Node<>(2, Color.Black,
                                new Node<>(1, Color.Red),
                                null),
                        new Node<>(4, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red),
                        new Node<>(12, Color.Red)));

        Node<Integer> actual = new Node<>(6, Color.Black,
                new Node<>(4, Color.Red,
                        new Node<>(2, Color.Black,
                                new Node<>(1, Color.Red),
                                new Node<>(3, Color.Red)),
                        new Node<>(5, Color.Black)),
                new Node<>(10, Color.Black,
                        new Node<>(8, Color.Red),
                        new Node<>(12, Color.Red)));

        actual = Operations.remove(actual, 5);

        assertEquals(expected, actual);
    }
}