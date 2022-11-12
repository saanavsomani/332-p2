package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.PriorityWorkList;

import java.util.Comparator;
import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/PriorityWorkList.java
 * for method specifications.
 */
public class MinFourHeap<E> extends PriorityWorkList<E> {
    /* Do not change the name of this field; the tests rely on it to work correctly. */
    private E[] data;
    private int size;
    private int capacity;
    private Comparator c;

    public MinFourHeap(Comparator<E> c) {
        this.size = 0;
        this.capacity = 10;
        this.data = (E[]) new Object[capacity];
        this.c = c;
    }

    @Override
    public boolean hasWork() {
        return super.hasWork();
    }

    @Override
    public void add(E work) {
        resize();
        this.size++;
        int index = percolateUp(this.size - 1, work);
        this.data[index] = work;
    }

    @Override
    public E peek() {
        if (!hasWork()) throw new NoSuchElementException();
        return this.data[0];
    }

    @Override
    public E next() {
        E min = peek();
        int index = percolateDown(0, this.data[this.size - 1]);
        this.data[index] = this.data[this.size - 1];
        this.size--;
        return min;
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

    private int percolateDown(int hole, E key) {
        while (4 * hole + 1 <= this.size) {
            int target = 4 * hole + 1;
            int second = target + 1;
            int third = second + 1;
            int fourth = third + 1;

            if (target >= this.size) break;

            //find index of the smallest child
            if (second < this.size && c.compare(this.data[target], this.data[second]) > 0) {
                target = second;
            }
            if (third < this.size && c.compare(this.data[target], this.data[third]) > 0) {
                target = third;
            }
            if (fourth < this.size && c.compare(this.data[target], this.data[fourth]) > 0) {
                target = fourth;
            }
            //swaps the smallest child with the parent
            if (c.compare(key, this.data[target]) > 0) {
                this.data[hole] = this.data[target];
                hole = target;
            } else {
                break;
            }

        }
        return hole;
    }

    private int percolateUp(int hole, E key) {
        while (hole > 0 && c.compare(key, this.data[(hole - 1) / 4]) < 0) {
            this.data[hole] = this.data[(hole - 1) / 4];
            hole = (hole - 1) / 4;
        }

        return hole;
    }

    private void resize() {
        if (this.size == this.capacity) {
            E[] temp = (E[]) new Object[capacity * 2];
            for (int i = 0; i < this.size; i++) {
                temp[i] = this.data[i];
            }
            this.data = temp;
            this.capacity *= 2;
        }
    }
}
