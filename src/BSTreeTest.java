import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

public class BSTreeTest {

    private BSTree empty;
    private BSTree tree;

    @BeforeEach
    public void setUp() {
        empty = new BSTree();
        tree = new BSTree();
        // Builds:        m
        //              /   \
        //             f     t
        //            / \     \
        //           c   h     x
        for (String s : new String[] {"m", "f", "t", "c", "h", "x"}) {
            tree.insert(s);
        }
    }

    @Test
    public void testNodeConstructorAndGetters() {
        BSTree.BSTNode leaf = tree.new BSTNode(null, null, "a");
        BSTree.BSTNode parent = tree.new BSTNode(leaf, null, "b");
        assertEquals("a", leaf.getKey());
        assertEquals("b", parent.getKey());
        assertSame(leaf, parent.getLeft());
        assertNull(parent.getRight());
        assertEquals(0, leaf.getNodeCount());
    }

    @Test
    public void testNodeSetters() {
        BSTree.BSTNode node = tree.new BSTNode(null, null, "k");
        BSTree.BSTNode left = tree.new BSTNode(null, null, "a");
        BSTree.BSTNode right = tree.new BSTNode(null, null, "z");
        node.setLeft(left);
        node.setRight(right);
        assertSame(left, node.getLeft());
        assertSame(right, node.getRight());
        node.setLeft(null);
        assertNull(node.getLeft());
    }

    @Test
    public void testNodeCountDuplicates() {
        assertEquals(0, tree.getRoot().getNodeCount());
        tree.insert("m");
        assertEquals(1, tree.getRoot().getNodeCount());
        tree.insert("m");
        assertEquals(2, tree.getRoot().getNodeCount());
    }

    @Test
    public void testConstructor() {
        assertNull(empty.getRoot());
        assertEquals(0, empty.getSize());
        assertEquals(-1, empty.findHeight());
    }

    @Test
    public void testGetRoot() {
        assertNull(empty.getRoot());
        assertEquals("m", tree.getRoot().getKey());
        assertEquals("f", tree.getRoot().getLeft().getKey());
        assertEquals("t", tree.getRoot().getRight().getKey());
    }

    @Test
    public void testGetSize() {
        assertEquals(6, tree.getSize());
        tree.insert("m");
        assertEquals(6, tree.getSize());
        tree.insert("a");
        assertEquals(7, tree.getSize());
    }

    @Test
    public void testInsert() {
        assertTrue(empty.insert("q"));
        assertEquals("q", empty.getRoot().getKey());
        assertTrue(empty.insert("b"));
        assertEquals("b", empty.getRoot().getLeft().getKey());
        assertTrue(empty.insert("q"));
        assertEquals(2, empty.getSize());
    }

    @Test
    public void testInsertNull() {
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> empty.insert(null));
    }

    @Test
    public void testFindKey() {
        assertTrue(tree.findKey("m"));
        assertTrue(tree.findKey("h"));
        assertTrue(tree.findKey("x"));
        assertFalse(tree.findKey("a"));
        assertFalse(empty.findKey("m"));
    }

    @Test
    public void testFindKeyNull() {
        assertThrows(NullPointerException.class, () -> tree.findKey(null));
        assertThrows(NullPointerException.class, () -> empty.findKey(null));
    }

    @Test
    public void testInOrderTraversal() {
        String[] keys = new String[tree.getSize()];
        tree.inOrderTraversal(keys);
        assertArrayEquals(new String[] {"c", "f", "h", "m", "t", "x"}, keys);

        tree.insert("f");
        String[] again = new String[tree.getSize()];
        tree.inOrderTraversal(again);
        assertArrayEquals(new String[] {"c", "f", "h", "m", "t", "x"}, again);

        BSTree one = new BSTree();
        one.insert("solo");
        String[] single = new String[1];
        one.inOrderTraversal(single);
        assertEquals("solo", single[0]);
    }

    @Test
    public void testInOrderTraversalEmpty() {
        assertThrows(NullPointerException.class,
                () -> empty.inOrderTraversal(new String[1]));
    }

    @Test
    public void testFindHeight() {
        assertEquals(-1, empty.findHeight());
        assertEquals(2, tree.findHeight());
        empty.insert("a");
        assertEquals(0, empty.findHeight());
        tree.insert("z");
        assertEquals(3, tree.findHeight());
    }

    @Test
    public void testLeafCount() {
        assertEquals(0, empty.leafCount());
        assertEquals(3, tree.leafCount());
        empty.insert("a");
        assertEquals(1, empty.leafCount());
        tree.insert("a");
        assertEquals(3, tree.leafCount());
    }

    @Test
    public void testIntersection() {
        BSTree other = new BSTree();
        for (String s : new String[] {"h", "x", "a", "m", "z"}) {
            other.insert(s);
        }
        assertEquals(new ArrayList<>(Arrays.asList("h", "m", "x")),
                tree.intersection(tree, other));
        assertTrue(tree.intersection(tree, empty).isEmpty());
        assertEquals(new ArrayList<>(Arrays.asList("c", "f", "h", "m", "t", "x")),
                tree.intersection(tree, tree));
    }

    @Test
    public void testLevelMax() {
        assertEquals("m", tree.levelMax(0));
        assertEquals("t", tree.levelMax(1));
        assertEquals("x", tree.levelMax(2));
        assertNull(tree.levelMax(3));
        assertNull(empty.levelMax(0));
    }
}
