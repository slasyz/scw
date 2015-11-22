package ru.slasyz.scw.list;

import java.util.function.UnaryOperator

class MyListTest extends GroovyTestCase {
    private MyList<Integer> list;

    void setUp() {
        super.setUp()

        list = new MyList<Integer>();
        list.add(4);
        list.add(4);
        list.add(4);
        list.add(8);
        list.add(15);
        list.add(16);
        list.add(23);
        list.add(42);
        list.add(15);
        list.add(16);
        list.add(23);
    }

    void testAdd() {
        list.add(1);
        list.add(1);
        list.add(2);

        assertEquals(14, list.size());
    }

    void testAddByIndex() {
        list.add(1, 3);

        assertEquals(12, list.size());
        assertEquals(3, list.get(1));
    }

    void testGet() {
        list.add(1);
        list.add(3);
        list.add(2);

        assertEquals(1, list.get(11));
        assertEquals(3, list.get(12));
        assertEquals(2, list.get(13));
    }

    void testIndexOf() {
        assertEquals(5, list.indexOf(16));
    }

    void testLastIndexOf() {
        assertEquals(9, list.lastIndexOf(16));
    }

    void testSubList() {
        MyList<Integer> subList = list.subList(2, 8);
        assertEquals(6, subList.size());
        assertEquals(4, subList.get(0));
        assertEquals(42, subList.get(5));
    }

    void testIterator() {
        List<Integer> testList = new ArrayList<>();
        for (Integer item : list)
            testList.add(item);

        assertEquals(11, testList.size());  // check size
        assertEquals(4, testList.get(0));   // check first element
        assertEquals(23, testList.get(10)); // check last element
    }

    void testMap() {
        UnaryOperator<Integer> op = {el -> el*2} as UnaryOperator;
        MyList<Integer> testList = list.map(op);

        assertEquals(11, testList.size());  // check size
        assertEquals(8, testList.get(0));   // check first element
        assertEquals(32, testList.get(5));
        assertEquals(46, testList.get(10)); // check last element
    }

    void testNull() {
        MyList<Integer> newList = new MyList<>();

        newList.add(10);
        newList.add(null);
        assertEquals(null, newList.get(1));

        newList.remove(1);
        assertEquals(1, newList.size());
        assertEquals(10, newList.get(0));

        boolean success = false;
        try {
            newList.remove(1);
        } catch (MyOutOfRangeException e) {
            success = true;
        }
        assertTrue(success);
    }
}
