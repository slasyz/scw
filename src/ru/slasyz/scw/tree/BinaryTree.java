package ru.slasyz.scw.tree;

public class BinaryTree<K extends Comparable<K>, V> {
    private K key = null;
    private V value = null;

    private BinaryTree<K, V> left;
    private BinaryTree<K, V> right;

    public BinaryTree() { }

    public BinaryTree(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() { return this.key; }

    public V getValue() {
        return this.value;
    }

    public BinaryTree<K, V> getLeftSubtree() {
        return this.left;
    }

    public BinaryTree<K, V> getRightSubtree() {
        return this.right;
    }

    public void add(K key, V value) {
        if (this.key == null) {
            this.key = key;
            this.value = value;
            return;
        }

        int cmp = key.compareTo(this.key);

        if (cmp == 0) {
            this.value = value;
        } else if (cmp < 0) {
            if (left != null)
                left.add(key, value);
            else
                left = new BinaryTree<>(key, value);
        } else { // if (cmp > 0)
            if (right != null)
                right.add(key, value);
            else
                right = new BinaryTree<>(key, value);
        }
    }

    public V get(K key) {
        if (this.value == null)
            return null;

        int cmp = key.compareTo(this.key);

        if (cmp == 0)
            return value;
        else if (cmp < 0)
            return left.get(key);
        else // if (cmp > 0)
            return right.get(key);
    }

    // Returns true if element was found, false otherwise.
    public boolean remove(K key) {
        if (this.key == null)
            return false;

        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            if (left == null) {
                if (right == null) {
                    // It has no children.
                    this.left = null;
                    this.right = null;
                    this.key = null;
                    this.value = null;
                } else {
                    // There is right child only.
                    this.key = this.right.key;
                    this.value = this.right.value;
                    this.left = this.right.left;
                    this.right = this.right.right;
                }
            } else {
                if (right == null) {
                    // There is left child only.
                    this.key = this.left.key;
                    this.value = this.left.value;
                    this.right = this.left.right;
                    this.left = this.left.left;
                } else {
                    // It has both children.
                    // http://neerc.ifmo.ru/wiki/index.php?title=%D0%94%D0%B5%D1%80%D0%B5%D0%B2%D0%BE_%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0,_%D0%BD%D0%B0%D0%B8%D0%B2%D0%BD%D0%B0%D1%8F_%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F

                    // this = 3
                    // current = 6
                    // current.left = 4
                    BinaryTree<K, V> current = right;
                    if (current.left == null) {
                        // If "six" has no "four"

                        // Put "six" value on "three"'s place.
                        this.key = current.key;
                        this.value = current.value;

                        // Move right child of "six" to "six" place.
                        this.right = current.right;
                    } else {
                        while (current.left.left != null)
                            current = current.left;

                        // Put "four" on "three" place.
                        this.key = current.left.key;
                        this.value = current.left.value;

                        // Move right child of "four" to "four" place.
                        current.left = current.left.right;
                    }
                }
            }

            return true;
        } else if (cmp < 0) {
            if (left == null)
                return false;

            // Search for the element in left subtree.
            if ((left.getKey() == key) && (left.getLeftSubtree() == null) && (left.getRightSubtree() == null)) {
                left = null;
                return true;
            } else
                return left.remove(key);
        } else { // if (cmp > 0)
            if (right == null)
                return false;

            // Search for the element in right subtree.
            if ((right.getKey() == key) && (right.getLeftSubtree() == null) && (right.getRightSubtree() == null)) {
                right = null;
                return true;
            } else
                return right.remove(key);
        }
    }
}
