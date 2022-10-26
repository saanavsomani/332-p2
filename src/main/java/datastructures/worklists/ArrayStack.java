package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.LIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/LIFOWorkList.java
 * for method specifications.
 */
public class ArrayStack<E> extends LIFOWorkList<E> {
    private E[] elements;
    private int top;

    public ArrayStack() {
        this.elements = (E[])new Object[10];
        this.top = -1;
    }

    @Override
    public void add(E work) {
        //if (work == null) throw new IllegalArgumentException();
        if (top + 1 == elements.length) resize();
        this.elements[++top] = work;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) throw new NoSuchElementException();
        return elements[top];
    }

    @Override
    public E next() {
        if(!this.hasWork()) throw new NoSuchElementException();
        return elements[top--];
    }

    @Override
    public int size() {
        return top + 1;
    }

    @Override
    public void clear() {
        top = -1;
        elements = (E[])new Object[10];
    }

    private void resize() {
        E[] resizedArray = (E[])new Object[elements.length*2];
        for (int i = 0; i < elements.length; i++) {
            resizedArray[i] = elements[i];
        }
        elements = resizedArray;
    }
}
