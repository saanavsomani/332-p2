package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.SimpleIterator;

import java.util.Iterator;

/**
 * 1. The list is typically not sorted.
 * 2. Add new items to the front of the list.
 * 3. Whenever find or insert is called on an existing key, move it
 * to the front of the list. This means you remove the node from its
 * current position and make it the first node in the list.
 * 4. You need to implement an iterator. The iterator SHOULD NOT move
 * elements to the front.  The iterator should return elements in
 * the order they are stored in the list, starting with the first
 * element in the list. When implementing your iterator, you should
 * NOT copy every item to another dictionary/list and return that
 * dictionary/list's iterator.
 */
public class MoveToFrontList<K, V> extends DeletelessDictionary<K, V> {
    private ListNode head;

    private class ListNode {
        private Item<K,V> data;
        private ListNode next;

        public ListNode() { this(null,null); }

        public ListNode(Item<K,V> data) {
            this(data,null);
        }
        public ListNode(Item<K,V> data, ListNode next) {
            this.data = data;
            this.next = next;
        }
    }

    public MoveToFrontList() {
        this(null);
    }

    public MoveToFrontList(Item<K,V> item) {
        this.head = new ListNode(item);
        if (item == null || item.key == null || item.value == null) {
            this.size = 0;
        } else {
            this.size = 1;
        }
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();

        ListNode current = new ListNode(new Item(key, value), this.head);
        V currentVal = find(key);
        if (currentVal == null) {
            this.head = current;
            this.size++;
            return null;
        }
        this.head.data.value = value;
        return currentVal;
    }

    @Override
    public V find(K key) {
        if (key == null) throw new IllegalArgumentException();
        if (this.size == 0) return null;

        ListNode tracker = this.head;
        V val = null;

        if (tracker != null && tracker.data != null) {
            if (tracker.data.key.equals(key)) return this.head.data.value;

            while (tracker.next != null && tracker.next.data != null && !tracker.next.data.key.equals(key)) {
                tracker = tracker.next;
            }

            if (tracker.next != null && tracker.next.data != null) {
                val = tracker.next.data.value;
                ListNode old = tracker.next;
                tracker.next = old.next;
                old.next = this.head;
                this.head = old;
            }
        }
        return val;
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        return new MoveToFrontListIterator();
    }

    private class MoveToFrontListIterator extends SimpleIterator<Item<K,V>> {
        private ListNode tracker;

        public MoveToFrontListIterator() {
            this.tracker = MoveToFrontList.this.head;
        }

        public boolean hasNext() {
            return tracker != null && tracker.next != null;
        }

        public Item<K,V> next() {
            Item<K,V> returnItem = tracker.data;
            tracker = tracker.next;
            return returnItem;
        }
    }
}
