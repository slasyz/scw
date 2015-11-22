package ru.slasyz.scw.tree;

// Реализовать бинарное дерево поиска
// Методы такие:
// [v] Добавить: на вход - пара (ключ,значение),
// [v] Найти: на вход - ключ, вывод — значение,
// Удалить: на вход - ключ
//
// Все это должно работать для любого типа ключа, у которого есть компаратор, и любого типа значения

public class BinaryTree<K extends Comparable<K>, V> {
    private K key = null;
    private V value = null;

    private BinaryTree<K, V> left;
    private BinaryTree<K, V> right;

    public enum SubtreeType {
        SUBTREE_LEFT, SUBTREE_RIGHT
    }

    public BinaryTree() { }

    public BinaryTree(K key, V value) {
        this.key = key;
        this.value = value;
    }

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

    public boolean removeInSubtree(K key, BinaryTree<K, V> subtree, SubtreeType subtreeType) {
        if (subtree == null)
            return false;

        int cmp = key.compareTo(subtree.key);
        if (cmp == 0) {
            if (subtree.left == null) {
                if (subtree.right == null) {
                    // Если нет обоих детей
                    if (subtreeType == SubtreeType.SUBTREE_LEFT)
                        left = null;
                    else
                        right = null;
                } else {
                    // Нет только левого
                    this.left = subtree.right;
                }
            } else {
                if (subtree.right == null) {
                    // Нет только правого
                    this.right = subtree.left;
                } else {
                    // Оба ребёнка на месте
                    // http://neerc.ifmo.ru/wiki/index.php?title=%D0%94%D0%B5%D1%80%D0%B5%D0%B2%D0%BE_%D0%BF%D0%BE%D0%B8%D1%81%D0%BA%D0%B0,_%D0%BD%D0%B0%D0%B8%D0%B2%D0%BD%D0%B0%D1%8F_%D1%80%D0%B5%D0%B0%D0%BB%D0%B8%D0%B7%D0%B0%D1%86%D0%B8%D1%8F

                    // subtree = 3
                    // current = 6
                    // current.left = 4
                    BinaryTree<K, V> current = subtree.right;
                    if (current.left == null) {
                        // Если бы у шестёрки не было четвёрки

                        // Записываем шестёрку на место тройки
                        subtree.key = current.key;
                        subtree.value = current.value;

                        // перемещаем правого ребёнка шестёрки
                        // на место шестёрки
                        subtree.right = current.right;
                    } else {
                        while (current.left.left != null)
                            current = current.left;

                        // записываем четвёрку на место тройки
                        subtree.key = current.left.key;
                        subtree.value = current.left.value;

                        // перемещаем правого ребёнка четвёрки
                        // на место четвёрки
                        current.left = current.left.right;
                    }
                }
            }
            return true;
        } else
            return subtree.remove(key);
    }

    public boolean remove(K key) {
        if (this.key == null)
            return false;

        int cmp = key.compareTo(this.key);
        if (cmp == 0) {
            // Такого не может быть
            assert false;
            return false;
        } else if (cmp < 0) {
            // Ищем удаляемый элемент в левом поддереве
            return removeInSubtree(key, left, SubtreeType.SUBTREE_LEFT);
        } else { // if (cmp > 0)
            // Ищем удаляемый элемент в правом поддереве
            return removeInSubtree(key, right, SubtreeType.SUBTREE_RIGHT);
        }
    }
}
