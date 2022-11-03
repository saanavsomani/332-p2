package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeapComparable<E extends Comparable<E>> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;

    public MinFourHeapComparable() {
        this.size = 0;
        this.capacity = 10;
        this.data = (E[]) new Comparable[capacity];
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        resize();
        this.data[size++] = work;
    }

    private void resize() {
        if (this.size == this.capacity) {
            E[] temp = (E[]) new Comparable[capacity * 2];
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.data[i];
            }
            this.data = temp;
            this.capacity *= 2;
        }
    }
    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        return this.data[0];
    }

    @Override
    public E next() {
        throw new NotYetImplementedException();
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        E[] temp = (E[]) new Comparable[capacity];
        this.size = 0;
        this.data = temp;

    }
}
