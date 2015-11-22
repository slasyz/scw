package ru.slasyz.scw.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.UnaryOperator;

public class MyList<E> implements Iterable<E> {
    private Element head;
    private int size;

    public MyList() {
        size = 0;
    }

    public void add(E item) {
        size++;

        if (head == null) {
            head = new Element(item, null);
            return;
        }

        Element el = head;
        while (el.next != null)
            el = el.next;

        el.next = new Element(item, null);
    }

    public void add(int index, E item) throws MyOutOfRangeException {
        if ((index < 0) || (index > size))
            throw new MyOutOfRangeException();

        size++;

        if (index == 0) {
            head = new Element(item, head);
            return;
        }

        // insert new element between prev and prev.next
        Element prev = head;
        for (int i = 1; i < index; i++)
            prev = prev.next;

        prev.next = new Element(item, prev.next);
    }

    public void remove(int index) throws MyOutOfRangeException {
        if (index >= size)
            throw new MyOutOfRangeException();

        if (index == 0)
            head = head.next;

        Element el = head;
        for (int i = 1; i < index; i++)
            el = el.next;

        el.next = el.next.next;
        size--;
    }

    public E get(int index) throws MyOutOfRangeException {
        if ((index < 0) || (index >= size))
            throw new MyOutOfRangeException();

        Element el = head;
        for (int i = 0; i < index; i++)
            el = el.next;

        return el.value;
    }

    public int indexOf(E obj) {
        Element el = head;
        int index = 0;

        while (el != null) {
            if (el.value.equals(obj))
                return index;

            el = el.next;
            index++;
        }

        return -1;
    }

    public int lastIndexOf(E obj) {
        Element el = head;
        int lastIndex = -1;
        int index = 0;

        while (el != null) {
            if (el.value.equals(obj))
                lastIndex = index;

            el = el.next;
            index++;
        }

        return lastIndex;
    }

    public int size() {
        return size;
    }

    public MyList<E> subList(int from, int to) throws MyOutOfRangeException {
        if ((from < 0) || (to > size) || (from > to))
            throw new MyOutOfRangeException();

        // search for first element
        Element currentElement = head;
        for (int i = 0; i < from; i++)
            currentElement = currentElement.next;

        MyList<E> subListResult = new MyList<E>();
        for (int i = from; i < to; i++) {
            subListResult.add(currentElement.value);
            currentElement = currentElement.next;
        }

        return subListResult;
    }

    public Iterator<E> iterator() {
        return new MyListIterator();
    }

    public MyList<E> map(UnaryOperator<E> operator) {
        MyList<E> newList = new MyList<E>();

        for (E el : this)
            newList.add(operator.apply(el));

        return newList;
    }

    class Element {
        public E value;
        public Element next;

        public Element(E value, Element next) {
            this.value = value;
            this.next = next;
        }
    }

    class MyListIterator implements Iterator<E> {
        Element current;

        public MyListIterator() {
            current = head;
        }

        public boolean hasNext() {
            return current != null;
        }

        public E next() {
            if (!hasNext())
                throw new NoSuchElementException();

            E res = current.value;
            current = current.next;
            return res;
        }
    }
}
