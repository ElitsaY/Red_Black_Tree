package tree;

import org.junit.jupiter.api.Test;

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
    }

    @Test
    void contains() {
    }

    @Test
    void insert() {
    }

    @Test
    void erase_emptyTree_SizeNotChanged() {
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        tree.erase(new ComplexType(3));
        assertEquals(0, tree.size(), "size remains 0");
    }

    @Test
    void erase_treeWithOneValueExists_theNodeWithThisValueIsRemoved(){
        RedBlackTree<ComplexType> tree = new RedBlackTree<>();

        ComplexType element = new ComplexType(19, false);
        tree.insert(element);

        tree.erase(element);
        assertFalse(tree.contains(element));
        assertEquals(0, tree.size(), "After erase the tree is empty");
    }

    //@Test


    @Test
    void clear() {
    }

    @Test
    void empty() {
    }

    @Test
    void size() {
    }

    @Test
    void height() {
    }
}