package p2.sorts;

import cse332.exceptions.NotYetImplementedException;

import java.util.Comparator;
import cse332.sorts.InsertionSort;

public class QuickSort {
    public static final int CUTOFF = 16;
    public static <E extends Comparable<E>> void sort(E[] array) {
        QuickSort.sort(array, (x, y) -> x.compareTo(y));
    }

    public static <E> void sort(E[] array, Comparator<E> comparator) {
        quickSort(array, 0, array.length - 1, comparator);
    }

    private static <E> void quickSort(E[] array, int low, int high, Comparator<E> comparator) {
        //if low < high
        if (high - low >= CUTOFF) {
            //determines the partition index and puts array[pi] at right place
            int pi = partition(array, low, high, comparator);
            //recursively sorts elements before pi
            quickSort(array, low, pi - 1, comparator);
            //recursively sorts elements after pi
            quickSort(array, pi + 1, high, comparator);
        } else {
            InsertionSort.sort(array, comparator);
        }
    }

    private static <E> int partition(E[] array, int low, int high, Comparator<E> comparator) {
        //int pivotIndex = medianOf3Pivot(array, low, high, comparator);
        E pivot = array[high];

        int index = (low - 1);

        for (int j = low; j < high; j++) {
            if (comparator.compare(array[j], pivot) < 0) {
                index++;

                swap(array, index, j);
            }
        }
        swap(array, index + 1, high);
        return index + 1;
    }

    private static <E> void swap(E[] array, int index1, int index2) {
        E temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }

//    private static <E> int medianOf3Pivot(E[] array, int low, int high, Comparator<E> comparator) {
//        int middle = ((low + high) / 2);
//        E lowVal = array[low];
//        E midVal = array[middle];
//        E highVal = array[high];
//        if (comparator.compare(highVal, midVal) > 0 ^ comparator.compare(highVal, lowVal) > 0) {
//            return high;
//        } else if (comparator.compare(midVal, highVal) < 0 ^ comparator.compare(midVal, lowVal) < 0) {
//            return middle;
//        } else {
//            return low;
//        }
//    }
}
