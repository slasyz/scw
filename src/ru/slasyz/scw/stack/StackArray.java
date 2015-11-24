package ru.slasyz.scw.stack;

import java.util.EmptyStackException;

public class StackArray<E> {
    private E[] elements;
    private int length;

    @SuppressWarnings("unchecked")
    public StackArray(int length) {
        elements = (E[]) new Object[length];
    }

    public E pop() throws EmptyStackException {
        if (length == 0)
            throw new EmptyStackException();

        int index = length - 1;
        length--;
        return elements[index];
    }

    public void push(E el) throws FullStackException {
        if (length == elements.length)
            throw new FullStackException();

        int index = length;
        length++;

        elements[index] = el;
    }

    public E peek() throws EmptyStackException {
        if (length == 0)
            throw new EmptyStackException();

        return elements[length - 1];
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

}
