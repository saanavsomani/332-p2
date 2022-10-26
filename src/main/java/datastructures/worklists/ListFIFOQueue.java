package datastructures.worklists;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.worklists.FIFOWorkList;

import java.util.NoSuchElementException;

/**
 * See cse332/interfaces/worklists/FIFOWorkList.java
 * for method specifications.
 */
public class ListFIFOQueue<E> extends FIFOWorkList<E> {

    private int size;
    private ListNode<E> front;
    private ListNode<E> back;
    public ListFIFOQueue() {
        this.size = 0;
        this.front = null;
        this.back = null;
    }

    @Override
    public void add(E work) {
        if (work == null) throw new IllegalArgumentException();
        if(this.hasWork()) {
            this.back.next = new ListNode<E>(work);
            this.back = this.back.getNext();
        } else { //the worklist is empty
            this.front = new ListNode<E>(work);
            this.back = this.front;
        }
        size++;
    }

    @Override
    public E peek() {
        if (!this.hasWork()) throw new NoSuchElementException();
        return this.front.getData();
    }

    @Override
    public E next() {
        E data = peek();
        this.front = this.front.getNext();
        size--;
        return data;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void clear() {
        this.size = 0;
        this.front = null;
        this.back = null;
    }

    private class ListNode<E> {
        private E data;
        private ListNode next;

        public ListNode() {
            this(null, null);
        }

        public ListNode(E data) {
            this(data, null);
        }

        public ListNode(E data, ListNode next) {
            this.data = data;
            this.next = next;
        }

        public E getData() { return this.data;}
        public ListNode getNext() {return this.next;}
    }
}
