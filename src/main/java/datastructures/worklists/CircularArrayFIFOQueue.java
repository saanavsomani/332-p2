package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FixedSizeFIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FixedSizeFIFOWorkList.java
 * for method specifications.
 */
public class CircularArrayFIFOQueue<E extends Comparable<E>> extends FixedSizeFIFOWorkList<E> {
    private E[] elements;
    private int front;
    private int back;
    private int size;

    public CircularArrayFIFOQueue(int capacity) {
        super(capacity);
        this.elements = (E[])new Comparable[capacity];
        this.front = 0;
        this.back = 0;
        this.size = 0;
    }

    @Override
    public void add(E work) {
        if(work == null) throw new IllegalArgumentException();
        if (this.isFull()) throw new IllegalStateException();
        this.elements[back] = work;
        back = (back + 1) % this.elements.length;
        size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) throw new NoSuchElementException();
        return this.elements[front];
    }

    @Override
    public E peek(int i) {
        if (!hasWork()) throw new NoSuchElementException();
        if (i < 0 || i >= this.size()) throw new IndexOutOfBoundsException();
        return this.elements[(front + i) % elements.length];
    }

    @Override
    public E next() {
        E data = this.peek();
        front = (front + 1) % this.elements.length;
        size--;
        return data;
    }

    @Override
    public void update(int i, E value) {
        if (value == null) throw new IllegalArgumentException();
        if (!hasWork()) throw new NoSuchElementException();
        if (i < 0 || i >= this.size()) throw new IndexOutOfBoundsException();
        this.elements[(front + i) % elements.length] = value;
    }

    @Override
    public int size() { return size; }

    @Override
    public void clear() {
        front = 0;
        back = 0;
        size = 0;
    }

    @Override
    public int compareTo(FixedSizeFIFOWorkList<E> other) {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int min = Math.min(this.size(), other.size());
        int diff = 0;
        for (int i = 0; i < min; i++) {
            diff = this.peek(i).compareTo(this.peek(i));
            if (diff != 0) return diff;
        }

        return this.size() - other.size();
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean equals(Object obj) {
        // You will finish implementing this method in project 2. Leave this method unchanged for project 1.
        if (this == obj) {
            return true;
        } else if (!(obj instanceof FixedSizeFIFOWorkList<?>)) {
            return false;
        } else {
            // Uncomment the line below for p2 when you implement equals
            FixedSizeFIFOWorkList<E> other = (FixedSizeFIFOWorkList<E>) obj;

            if (this.size() != other.size()) return false;

            return (this.compareTo(other) == 0);
        }
    }

    @Override
    public int hashCode() {
        // You will implement this method in project 2. Leave this method unchanged for project 1.
        int hashCode = 1;
        for (E item: elements) {
            hashCode = 31*hashCode + (item == null ? 0 : item.hashCode());
        }
        return hashCode;
    }
}
