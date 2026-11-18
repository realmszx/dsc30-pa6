import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class orderStatTreeTest {

    private orderStatTree empty;
    private orderStatTree tree;

    @BeforeEach
    public void setUp() {
        empty = new orderStatTree();
        tree = new orderStatTree();
        // Example from the write-up: sorted order is ant, bee, cat, dog, eel
        for (String s : new String[] {"bee", "dog", "cat", "ant", "eel", "dog"}) {
            tree.insert(s);
        }
    }

    @Test
    public void testNodeConstructorAndGetters() {
        orderStatTree.orderStatNode leaf = tree.new orderStatNode(null, null, "a");
        orderStatTree.orderStatNode parent = tree.new orderStatNode(leaf, null, "b");
        assertEquals("a", leaf.getKey());
        assertSame(leaf, parent.getLeft());
        assertNull(parent.getRight());
        assertEquals(0, leaf.getNodeCount());
    }

    @Test
    public void testNodeSetters() {
        orderStatTree.orderStatNode node = tree.new orderStatNode(null, null, "k");
        orderStatTree.orderStatNode left = tree.new orderStatNode(null, null, "a");
        orderStatTree.orderStatNode right = tree.new orderStatNode(null, null, "z");
        node.setLeft(left);
        node.setRight(right);
        assertSame(left, node.getLeft());
        assertSame(right, node.getRight());
        node.setRight(null);
        assertNull(node.getRight());
    }

    @Test
    public void testNodeCount() {
        assertEquals(0, tree.getRoot().getNodeCount());
        assertEquals(1, tree.getRoot().getRight().getNodeCount());
        tree.insert("bee");
        assertEquals(1, tree.getRoot().getNodeCount());
    }

    @Test
    public void testConstructor() {
        assertNull(empty.getRoot());
        assertEquals(0, empty.getSize());
        assertFalse(empty.findKey("a"));
    }

    @Test
    public void testGetRoot() {
        assertNull(empty.getRoot());
        assertEquals("bee", tree.getRoot().getKey());
        assertEquals("ant", tree.getRoot().getLeft().getKey());
        assertEquals("dog", tree.getRoot().getRight().getKey());
    }

    @Test
    public void testGetSize() {
        assertEquals(5, tree.getSize());
        tree.insert("ant");
        assertEquals(5, tree.getSize());
        tree.insert("fox");
        assertEquals(6, tree.getSize());
    }

    @Test
    public void testInsert() {
        assertTrue(empty.insert("m"));
        assertTrue(empty.insert("a"));
        assertTrue(empty.insert("m"));
        assertEquals(2, empty.getSize());
        assertEquals(2, empty.getSubtreeSize("m"));
    }

    @Test
    public void testInsertNull() {
        assertThrows(NullPointerException.class, () -> tree.insert(null));
        assertThrows(NullPointerException.class, () -> empty.insert(null));
    }

    @Test
    public void testFindKey() {
        assertTrue(tree.findKey("bee"));
        assertTrue(tree.findKey("cat"));
        assertTrue(tree.findKey("eel"));
        assertFalse(tree.findKey("cow"));
    }

    @Test
    public void testFindKeyNull() {
        assertThrows(NullPointerException.class, () -> tree.findKey(null));
        assertThrows(NullPointerException.class, () -> empty.findKey(null));
    }

    @Test
    public void testGetSubtreeSize() {
        assertEquals(5, tree.getSubtreeSize("bee"));
        assertEquals(3, tree.getSubtreeSize("dog"));
        assertEquals(1, tree.getSubtreeSize("ant"));
        assertEquals(1, tree.getSubtreeSize("eel"));
    }

    @Test
    public void testGetSubtreeSizeExceptions() {
        assertThrows(NullPointerException.class, () -> tree.getSubtreeSize(null));
        assertThrows(IllegalArgumentException.class, () -> tree.getSubtreeSize("cow"));
        assertThrows(IllegalArgumentException.class, () -> empty.getSubtreeSize("a"));
    }

    @Test
    public void testGetPercentile() {
        assertEquals("cat", tree.getPercentile(50));
        assertEquals("bee", tree.getPercentile(23));
        assertEquals("ant", tree.getPercentile(0));
        assertEquals("eel", tree.getPercentile(100));
        assertEquals("dog", tree.getPercentile(61));
    }

    @Test
    public void testGetPercentileExceptions() {
        assertThrows(IllegalArgumentException.class, () -> tree.getPercentile(-1));
        assertThrows(IllegalArgumentException.class, () -> tree.getPercentile(101));
        assertThrows(IllegalStateException.class, () -> empty.getPercentile(50));
    }

    @Test
    public void testMedian() {
        assertEquals("cat", tree.median());
        tree.insert("fox");
        assertEquals("cat", tree.median());
        empty.insert("solo");
        assertEquals("solo", empty.median());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().median());
    }

    @Test
    public void testMin() {
        assertEquals("ant", tree.min());
        tree.insert("aardvark");
        assertEquals("aardvark", tree.min());
        empty.insert("x");
        assertEquals("x", empty.min());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().min());
    }

    @Test
    public void testMax() {
        assertEquals("eel", tree.max());
        tree.insert("zebra");
        assertEquals("zebra", tree.max());
        empty.insert("x");
        assertEquals("x", empty.max());
        assertThrows(IllegalStateException.class, () -> new orderStatTree().max());
    }
}
