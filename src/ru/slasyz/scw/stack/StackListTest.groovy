package ru.slasyz.scw.stack;

class StackListTest extends GroovyTestCase {
    private StackList<Integer> stack;

    void setUp() {
        super.setUp()

        stack = new StackList<>();
        stack.push(4);
        stack.push(8);
        stack.push(15);
        stack.push(16);
        stack.push(23);
        stack.push(42);
    }

    void testSize() {
        assertEquals(6, stack.size());
    }

    void testPop() {
        Integer el = stack.pop();
        assertEquals(42, el);
        assertEquals(5, stack.size());
    }

    void testPeek() {
        Integer el = stack.peek();
        assertEquals(42, el);
        assertEquals(6, stack.size());
    }

    void testIsEmpty() {
        assertEquals(false, stack.isEmpty())
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        stack.pop();
        assertEquals(true, stack.isEmpty())
    }
}
