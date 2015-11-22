package ru.slasyz.scw.stack;

class StackArrayTest extends GroovyTestCase {
    private StackArray<Integer> stack;

    void setUp() {
        super.setUp()

        stack = new StackArray<>(Integer.class, 10);
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

    void testFullStack() {
        stack.push(4);
        stack.push(8);
        stack.push(15);
        stack.push(16);

        boolean success = false;
        try {
            stack.push(23);
        } catch (FullStackException e) {
            success = true;
        }
        assertTrue(success);
    }
}
