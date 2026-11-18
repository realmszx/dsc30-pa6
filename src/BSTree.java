/*
 * Name: Rachit Jain
 * PID: A18942551
 * Sources used: None
 *
 * A binary search tree of String keys that tracks duplicate insertions.
 */

import java.util.ArrayList;

/**
 * Binary search tree of String keys. Duplicate insertions of a key are counted
 * on its node instead of creating a new node.
 *
 * @author Rachit Jain
 * @since 2026-11-17
 */
public class BSTree {

    private int nelems;
    private BSTNode root;

    /**
     * A single node of the BST holding a key, its duplicate count, and its
     * children.
     */
    protected class BSTNode {

        String key;
        int keyCount;
        BSTNode left;
        BSTNode right;

        /**
         * Creates a node with the given children and key. The duplicate count
         * starts at 0 because the first insertion is not a duplicate.
         *
         * @param left the left child
         * @param right the right child
         * @param key the key stored in this node
         */
        public BSTNode(BSTNode left, BSTNode right, String key) {
            this.left = left;
            this.right = right;
            this.key = key;
            this.keyCount = 0;
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
        public BSTNode getLeft() {
            return left;
        }

        /**
         * Returns the right child of this node.
         *
         * @return the right child, or null if none
         */
        public BSTNode getRight() {
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
        public void setLeft(BSTNode newleft) {
            left = newleft;
        }

        /**
         * Sets the right child of this node.
         *
         * @param newright the new right child
         */
        public void setRight(BSTNode newright) {
            right = newright;
        }
    }

    /**
     * Creates an empty tree.
     */
    public BSTree() {
        root = null;
        nelems = 0;
    }

    /**
     * Returns the root of the tree.
     *
     * @return the root, or null if the tree is empty
     */
    public BSTNode getRoot() {
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
     * Recursively inserts a key into the subtree rooted at node.
     *
     * @param node the root of the current subtree
     * @param key the key to insert
     * @return the root of the updated subtree
     */
    private BSTNode insertHelper(BSTNode node, String key) {
        if (node == null) {
            nelems++;
            return new BSTNode(null, null, key);
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            node.setLeft(insertHelper(node.getLeft(), key));
        } else if (cmp > 0) {
            node.setRight(insertHelper(node.getRight(), key));
        } else {
            node.keyCount++;
        }
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
        return findHelper(root, key);
    }

    /**
     * Recursively searches the subtree rooted at node for a key.
     *
     * @param node the root of the current subtree
     * @param key the key to search for
     * @return true if the key is found, false otherwise
     */
    private boolean findHelper(BSTNode node, String key) {
        if (node == null) {
            return false;
        }
        int cmp = key.compareTo(node.getKey());
        if (cmp < 0) {
            return findHelper(node.getLeft(), key);
        } else if (cmp > 0) {
            return findHelper(node.getRight(), key);
        }
        return true;
    }

    /**
     * Fills keyArray with the distinct keys of the tree in ascending order.
     *
     * @param keyArray the array to fill, assumed large enough
     * @throws NullPointerException if the tree is empty
     */
    public void inOrderTraversal(String[] keyArray) {
        if (root == null) {
            throw new NullPointerException();
        }
        inOrderHelper(root, keyArray, 0);
    }

    /**
     * Recursively writes the keys of a subtree in order, starting at index.
     *
     * @param node the root of the current subtree
     * @param keyArray the array to fill
     * @param index the next free position in keyArray
     * @return the next free position after this subtree is written
     */
    private int inOrderHelper(BSTNode node, String[] keyArray, int index) {
        if (node == null) {
            return index;
        }
        index = inOrderHelper(node.getLeft(), keyArray, index);
        keyArray[index] = node.getKey();
        index++;
        return inOrderHelper(node.getRight(), keyArray, index);
    }

    /**
     * Returns the height of the tree in edges. An empty tree has height -1.
     *
     * @return the height of the tree
     */
    public int findHeight() {
        return heightHelper(root);
    }

    /**
     * Recursively computes the height of the subtree rooted at node.
     *
     * @param node the root of the current subtree
     * @return the height of the subtree, or -1 if it is empty
     */
    private int heightHelper(BSTNode node) {
        if (node == null) {
            return -1;
        }
        return 1 + Math.max(heightHelper(node.getLeft()),
                heightHelper(node.getRight()));
    }

    /**
     * Returns the number of leaf nodes in the tree.
     *
     * @return the number of leaves
     */
    public int leafCount() {
        return leafHelper(root);
    }

    /**
     * Recursively counts the leaves of the subtree rooted at node.
     *
     * @param node the root of the current subtree
     * @return the number of leaves in the subtree
     */
    private int leafHelper(BSTNode node) {
        if (node == null) {
            return 0;
        }
        if (node.getLeft() == null && node.getRight() == null) {
            return 1;
        }
        return leafHelper(node.getLeft()) + leafHelper(node.getRight());
    }

    /**
     * Returns the keys found in both trees, in ascending order.
     *
     * @param tree1 the first tree
     * @param tree2 the second tree
     * @return the sorted keys common to both trees
     */
    public ArrayList<String> intersection(BSTree tree1, BSTree tree2) {
        ArrayList<String> common = new ArrayList<>();
        if (tree1 == null || tree2 == null) {
            return common;
        }
        intersectionHelper(tree1.getRoot(), tree2, common);
        return common;
    }

    /**
     * Walks a subtree in order and collects the keys also present in other.
     *
     * @param node the root of the current subtree of the first tree
     * @param other the tree to check each key against
     * @param common the list collecting shared keys
     */
    private void intersectionHelper(BSTNode node, BSTree other,
                                    ArrayList<String> common) {
        if (node == null) {
            return;
        }
        intersectionHelper(node.getLeft(), other, common);
        if (other.findKey(node.getKey())) {
            common.add(node.getKey());
        }
        intersectionHelper(node.getRight(), other, common);
    }

    /**
     * Returns the largest key at the given level, where the root is level 0.
     *
     * @param level the level to search, assumed to be at least 0
     * @return the largest key at that level, or null if the level is empty
     */
    public String levelMax(int level) {
        return levelMaxHelper(root, level);
    }

    /**
     * Recursively finds the largest key at a given depth below node.
     *
     * @param node the root of the current subtree
     * @param level the remaining depth to descend
     * @return the largest key at that depth, or null if there is none
     */
    private String levelMaxHelper(BSTNode node, int level) {
        if (node == null) {
            return null;
        }
        if (level == 0) {
            return node.getKey();
        }
        String leftMax = levelMaxHelper(node.getLeft(), level - 1);
        String rightMax = levelMaxHelper(node.getRight(), level - 1);
        if (leftMax == null) {
            return rightMax;
        }
        if (rightMax == null) {
            return leftMax;
        }
        return leftMax.compareTo(rightMax) >= 0 ? leftMax : rightMax;
    }
}
