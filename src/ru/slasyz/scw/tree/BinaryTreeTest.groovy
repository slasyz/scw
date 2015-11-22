package ru.slasyz.scw.tree

class BinaryTreeTest extends GroovyTestCase {
    private BinaryTree<Integer, String> tree;

    void setUp() {
        super.setUp()

        tree = new BinaryTree<>();
        tree.add(8, "acht");
        tree.add(3, "drei");
        tree.add(10, "zehn");
        tree.add(1, "einer");
        tree.add(6, "sechs");
        tree.add(14, "vierzehn");
        tree.add(4, "vier");
        tree.add(7, "sieben");
        tree.add(13, "dreizehn");
    }

    void testAdd() {
        assertEquals("sechs", tree.getLeftSubtree().getRightSubtree().getValue())
    }

    void testGet() {
        assertEquals("sechs", tree.get(6));
    }

    void testRemoveNone() {
        tree.remove(1);
        assertEquals(null, tree.getLeftSubtree().getLeftSubtree())
    }

    void testRemoveOnlyLeft() {
        tree.remove(14);
        assertEquals("dreizehn", tree.getRightSubtree().getRightSubtree().getValue())
    }

    void testRemoveBoth() {
        tree.remove(3);
        assertEquals("vier", tree.getLeftSubtree().getValue())
    }

    void testRemoveWithoutFour() {
        tree.remove(4);
        tree.remove(3);
        assertEquals("sechs", tree.getLeftSubtree().getValue())
    }

    void testRemoveNotExisting() {
        assertEquals(false, tree.remove(100))
    }
}
