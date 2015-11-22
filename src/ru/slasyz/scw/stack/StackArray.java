package ru.slasyz.scw.stack;

import java.lang.reflect.Array;
import java.util.EmptyStackException;

public class StackArray<E> {
    private E[] elements;
    private int length;

    @SuppressWarnings("unchecked")
    public StackArray(Class<E> clazz, int length) {
        // Так делать можно, т.к. в push() проверяется тип добавляемого элемента
        elements = (E[]) Array.newInstance(clazz, length);
    }

    public E pop() throws EmptyStackException {
        if (length == 0)
            throw new EmptyStackException();

        int index = length - 1;
        length--;
        //elements = Arrays.copyOf(elements, index); // all but last element
        return elements[index];
    }

    public void push(E el) throws FullStackException {
        if (length == elements.length)
            throw new FullStackException();

        int index = length;
        length++;
        //elements = Arrays.copyOf(elements, index+1);

        elements[index] = el;
    }

    public E peek() {
        return elements[length - 1];
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

}
