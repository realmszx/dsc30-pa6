/*
 * Name: Rachit Jain
 * PID: A18942551
 * Sources used: None
 *
 * An order-statistic tree: a BST that stores subtree sizes so it can find
 * the key at any percentile.
 */

/**
 * Binary search tree of String keys in which every node stores the size of
 * its subtree, so the k-th smallest key can be found without a full traversal.
 *
 * @author Rachit Jain
 * @since 2026-11-17
 */
public class orderStatTree {

    private static final int MAX_PERCENTILE = 100;

    private int nelems;
    private orderStatNode root;

    /**
     * A node holding a key, its duplicate count, its subtree size, and its
     * children.
     */
    protected class orderStatNode {

        String key;
        int keyCount;
        int subtreeSize;
        orderStatNode left;
        orderStatNode right;

        /**
         * Creates a node with the given children and key. The subtree size is
         * this node plus the sizes of both children.
         *
         * @param left the left child
         * @param right the right child
         * @param key the key stored in this node
         */
        public orderStatNode(orderStatNode left, orderStatNode right, String key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.keyCount = 0;
            this.subtreeSize = 1 + sizeOf(left) + sizeOf(right);
        }

        /**
         * Returns the key of this node.
         *
         * @return the key
         */
        public String getKey() {
            return key;
        }

        /**
         * Returns the left child of this node.
         *
         * @return the left child, or null if none
         */
        public orderStatNode getLeft() {
            return left;
        }

        /**
         * Returns the right child of this node.
         *
         * @return the right child, or null if none
         */
        public orderStatNode getRight() {
            return right;
        }

        /**
         * Returns how many times this key was inserted again after the first.
         *
         * @return the duplicate insertion count
         */
        public int getNodeCount() {
            return keyCount;
        }

        /**
         * Sets the left child of this node.
         *
         * @param newleft the new left child
         */
        public void setLeft(orderStatNode newleft) {
            left = newleft;
        }

        /**
         * Sets the right child of this node.
         *
         * @param newright the new right child
         */
        public void setRight(orderStatNode newright) {
            right = newright;
        }
    }

    /**
     * Creates an empty tree.
     */
    public orderStatTree() {
        root = null;
        nelems = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root, or null if the tree is empty
     */
    public orderStatNode getRoot() {
        return root;
    }

    /**
     * Returns the number of distinct keys in the tree.
     *
     * @return the number of distinct keys
     */
    public int getSize() {
        return nelems;
    }

    /**
     * Returns the subtree size of a node, treating null as an empty subtree.
     *
     * @param node the node to measure
     * @return the size of the subtree rooted at node
     */
    private int sizeOf(orderStatNode node) {
        return node == null ? 0 : node.subtreeSize;
    }

    /**
     * Inserts a key, or increments its duplicate count if already present.
     *
     * @param key the key to insert
     * @return true once the key is inserted or its count is incremented
     * @throws NullPointerException if key is null
     */
    public boolean insert(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        root = insertHelper(root, key);
        return true;
    }

    /**
     * Recursively inserts a key and updates subtree sizes on the way back up.
     *
     * @param node the root of the current subtree
     * @param key the key to insert
     * @return the root of the updated subtree
     */
    private orderStatNode insertHelper(orderStatNode node, String key) {
        if (node == null) {
            nelems++;
            return new orderStatNode(null, null, key);
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(insertHelper(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(insertHelper(node.getRight(), key));
        } else {
            node.keyCount++;
        }
        node.subtreeSize = 1 + sizeOf(node.getLeft()) + sizeOf(node.getRight());
        return node;
    }

    /**
     * Checks whether a key is in the tree.
     *
     * @param key the key to search for
     * @return true if the key is found, false otherwise
     * @throws NullPointerException if key is null
     */
    public boolean findKey(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        return findNode(root, key) != null;
    }

    /**
     * Recursively finds the node holding a key.
     *
     * @param node the root of the current subtree
     * @param key the key to search for
     * @return the node holding key, or null if it is not in the subtree
     */
    private orderStatNode findNode(orderStatNode node, String key) {
        if (node == null) {
            return null;
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            return findNode(node.getLeft(), key);
        } else if (cmp > 0) {
            return findNode(node.getRight(), key);
        }
        return node;
    }

    /**
     * Returns the size of the subtree rooted at the node with the given key.
     *
     * @param key the key whose subtree to measure
     * @return the number of distinct keys in that subtree
     * @throws NullPointerException if key is null
     * @throws IllegalArgumentException if key is not in the tree
     */
    public int getSubtreeSize(String key) {
        if (key == null) {
            throw new NullPointerException();
        }
        orderStatNode node = findNode(root, key);
        if (node == null) {
            throw new IllegalArgumentException();
        }
        return node.subtreeSize;
    }

    /**
     * Returns the key at the given percentile, using
     * k = ceil(percentile / 100 * n) as the 1-based rank (0th gives the min).
     *
     * @param percentile the percentile, from 0 to 100 inclusive
     * @return the key at that percentile
     * @throws IllegalArgumentException if percentile is outside 0 to 100
     * @throws IllegalStateException if the tree is empty
     */
    public String getPercentile(int percentile) {
        if (percentile < 0 || percentile > MAX_PERCENTILE) {
            throw new IllegalArgumentException();
        }
        if (root == null) {
            throw new IllegalStateException();
        }
        // Integer ceiling of percentile * n / 100
        int rank = (percentile * nelems + MAX_PERCENTILE - 1) / MAX_PERCENTILE;
        rank = Math.max(rank, 1);
        return selectHelper(root, rank);
    }

    /**
     * Recursively finds the key with the given 1-based rank in a subtree.
     *
     * @param node the root of the current subtree
     * @param rank the rank to find within this subtree
     * @return the key with that rank
     */
    private String selectHelper(orderStatNode node, int rank) {
        int nodeRank = sizeOf(node.getLeft()) + 1;
        if (rank < nodeRank) {
            return selectHelper(node.getLeft(), rank);
        } else if (rank > nodeRank) {
            return selectHelper(node.getRight(), rank - nodeRank);
        }
        return node.getKey();
    }

    /**
     * Returns the median (50th percentile) key.
     *
     * @return the median key
     * @throws IllegalStateException if the tree is empty
     */
    public String median() {
        return getPercentile(MAX_PERCENTILE / 2);
    }

    /**
     * Returns the smallest key.
     *
     * @return the minimum key
     * @throws IllegalStateException if the tree is empty
     */
    public String min() {
        return getPercentile(0);
    }

    /**
     * Returns the largest key.
     *
     * @return the maximum key
     * @throws IllegalStateException if the tree is empty
     */
    public String max() {
        return getPercentile(MAX_PERCENTILE);
    }
}
