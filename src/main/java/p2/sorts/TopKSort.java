package p2.sorts;

import cse332.exceptions.NotYetImplementedException;
import datastructures.worklists.MinFourHeap;

import java.util.Comparator;

public class TopKSort {
    public static <E extends Comparable<E>> void sort(E[] array, int k) {
        sort(array, k, (x, y) -> x.compareTo(y));
    }

    /**
     * Behaviour is undefined when k > array.length
     */
    public static <E> void sort(E[] array, int k, Comparator<E> comparator) {
        if (k > array.length) k = array.length;
        MinFourHeap<E> heap = new MinFourHeap<>(comparator);

        //inserts the first k items from the array into the heap
        for (int i = 0; i < k; i++) {
            if (heap.size() < k) heap.add(array[i]);
        }

        //checks if any of the values in the remaining array are greater than the first element
        //if greater, removes minimum element in array and adds the greater element
        for (int i = k; i < array.length; i++) {
            if (comparator.compare(array[i], heap.peek()) > 0) {
                heap.next();
                heap.add(array[i]);
            }
            //sets the rest of the elements in the array to null
            array[i] = null;
        }

        //puts the top k sorted elements into the first k indexes of the given array
        for (int i = 0; i < k; i++) {
            if (!heap.hasWork()) throw new IllegalArgumentException();
            array[i] = heap.next();
        }
    }
}
