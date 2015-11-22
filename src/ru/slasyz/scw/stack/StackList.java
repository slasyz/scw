package ru.slasyz.scw.stack;

import java.util.ArrayList;
import java.util.List;

public class StackList<E> {
    private List<E> elements;

    public StackList() {
        elements = new ArrayList<>();
    }

    public E pop() {
        int index = elements.size()-1;
        E el = elements.get(index);
        elements.remove(index);
        return el;
    }

    public void push(E el) {
        elements.add(el);
    }

    public E peek() {
        int index = elements.size()-1;
        return elements.get(index);
    }

    public boolean isEmpty() {
        return elements.isEmpty();
    }

    public int size() {
        return elements.size();
    }
}
