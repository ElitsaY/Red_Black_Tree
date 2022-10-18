package tree;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class RedBlackTreeTest {
    class ComplexType implements Comparable<ComplexType> {
        private int num;
        private boolean isCorgi;

        public ComplexType(int num, boolean isCorgi) {
            this.num = num;
            this.isCorgi = isCorgi;
        }

        public ComplexType(int num) {
            this.num = num;
            isCorgi = true;
        }

        @Override
        public int compareTo(ComplexType o) {
            return Integer.compare(num, o.num);
        }

        public int getNum() {
            return num;
        }

        public boolean isCorgi() {
            return isCorgi;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ComplexType that = (ComplexType) o;
            return num == that.num && isCorgi == that.isCorgi;
        }

        @Override
        public int hashCode() {
            return Objects.hash(num, isCorgi);
        }
    }

    @Test
    void contains_emptyTree_doesNotContainElement() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        assertFalse(tree.contains(new ComplexType(4)),
                "Tree is empty and shouldn't contain anything");
    }

    @Test
    void contains_emptyTree_doesNotContainNullElement() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        assertFalse(tree.contains(null),
                "Tree is empty and shouldn't contain anything");
    }

    @Test
    void contains_treeWith5_contains5() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(6));
        tree.insert(new ComplexType(5));
        assertTrue(tree.contains(new ComplexType(5)),
                "The tree should contain added value");
    }

    @Test
    void insert_valueTwice_treeContainsIt() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(5));
        tree.insert(new ComplexType(5));
        assertTrue(tree.contains(new ComplexType(5)), "Inserted value should be contained");
    }

    @Test
    void insert_valueTwice_treeSizeIncreasesOnce() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(5));
        tree.insert(new ComplexType(5));
        assertEquals(1, tree.size(), "The size of the tree should increase just once");
    }

    @Test
    void erase_emptyTree_SizeNotChanged() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        tree.erase(new ComplexType(3));
        assertEquals(0, tree.size(), "size remains 0");
    }

    @Test
    void erase_treeWithOneValueExists_theNodeWithThisValueIsRemoved() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        ComplexType element = new ComplexType(19, false);
        tree.insert(element);

        tree.erase(element);
        assertFalse(tree.contains(element), "Erased element is not contained");
        assertEquals(0, tree.size(), "After erase the tree is empty");
    }

    @Test
    void erase_treeWithFiveEraseFive_fiveIsNotContained() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        ComplexType element = new ComplexType(5, false);
        tree.insert(element);
        tree.insert(new ComplexType(3));

        tree.erase(element);
        assertFalse(tree.contains(element), "Erased element is not contained");
    }

    @Test
    void erase_treeWithTwoValuesEraseOne_theOtherIsContained() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        ComplexType element = new ComplexType(5, false);
        tree.insert(element);
        tree.insert(new ComplexType(3));

        tree.erase(element);
        assertTrue(tree.contains(new ComplexType(3)), "Not erased element is still contained");
    }

    @Test
    void erase_treeWithElements_sizeDecreasesByOne() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        for (int i = 0; i < 10; i++) {
            tree.insert(new ComplexType(i));
        }

        tree.erase(new ComplexType(2));
        assertEquals(9, tree.size(), "After erasing, tree size decreases with one");
    }

    @Test
    void clear_tree_sizeBecomesZero() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(19, false));

        tree.clear();
        assertEquals(0, tree.size(), "After clear the size should be zero");
    }

    @Test
    void clear_treeWith5_doesNotContain5() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(5));

        tree.clear();
        assertFalse(tree.contains(new ComplexType(5)),
                "Cleared tree shouldn't contain anything");
    }

    @Test
    void empty_treeIsEmpty_returnsTrue() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        assertTrue(tree.empty(), "Newly created tree is empty");
    }


    @Test
    void empty_treeWithElements_returnsFalse() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(3));

        assertFalse(tree.empty(), "Non empty tree is not empty!");
    }


    @Test
    void height_ofTreeWithOneNode_isOne() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        tree.insert(new ComplexType(3));

        assertEquals(1, tree.size(), "Tree with one node has height 1");
    }

    @Test
    void height_ofTreeWithTenNodes_hasHeightNoMoreThan2LogN() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        for (int i = 0; i < 10; i++) {
            tree.insert(new ComplexType(i));
        }

        int height = tree.height();
        double maxHeight = (Math.log(10 + 1) / Math.log(2)) * 2;
        assertTrue(height <= maxHeight, "Height should be less than 2*logN");
    }

    @Test
    @Disabled
    void height_ofTreeWithOneMillionElements_hasHeightNoMoreThan2LogN() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        for (int i = 0; i < 1_000_000; i++) {
            tree.insert(new ComplexType(i));
        }
        int height = tree.height();
        double maxHeight = (Math.log(1_000_000 + 1) / Math.log(2)) * 2;
        assertTrue(height <= maxHeight, "Height should be less than 2*logN");
    }

    @Test
    @Disabled
    void insert_oneMillionElements_elementsAreContained() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();
        for (int i = 0; i < 1_000_000; i++) {
            tree.insert(new ComplexType(i));
        }
        for (int i = 0; i < 1_000_000; i++) {
            assertTrue(tree.contains(new ComplexType(i)), "Tree should contain inserted element");
        }
        assertEquals(1_000_000, tree.size(),
                "After adding 1_000_000 elements, size should be 1_000_000");
    }
}