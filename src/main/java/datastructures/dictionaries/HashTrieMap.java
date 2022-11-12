package datastructures.dictionaries;

import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.Dictionary;
import cse332.interfaces.trie.TrieMap;
import cse332.types.BString;
import cse332.datastructures.containers.Item;
import datastructures.worklists.ArrayStack;

import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * See cse332/interfaces/trie/TrieMap.java
 * and cse332/interfaces/misc/Dictionary.java
 * for method specifications.
 */
public class HashTrieMap<A extends Comparable<A>, K extends BString<A>, V> extends TrieMap<A, K, V> {
    public class HashTrieNode extends TrieNode<ChainingHashTable<A, HashTrieNode>, HashTrieNode> {
        public HashTrieNode() {
            this(null);
        }

        public HashTrieNode(V value) {
            this.pointers = new ChainingHashTable<>(MoveToFrontList::new);
            this.value = value;
        }

        @Override
        public Iterator<Entry<A, HashTrieNode>> iterator() {
            Iterator<Item<A, HashTrieNode>> iterator = pointers.iterator();
            ArrayStack<Entry<A, HashTrieNode>> items = new ArrayStack<>();

            while(iterator.hasNext()) {
                Item<A, HashTrieNode> it = iterator.next();
                items.add(new SimpleEntry<>(it.key, it.value));
            }
            return items.iterator();
        }
    }

    public HashTrieMap(Class<K> KClass) {
        super(KClass);
        this.root = new HashTrieNode();
        this.size = 0;
    }

    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();
        if (this.root == null) this.root = new HashTrieNode();

        V returnVal;
        //no key is associated with the value meaning that the previous value is null
        if(key.isEmpty()) {
            returnVal = this.root.value;
            this.root.value = value;
        } else {
            HashTrieNode tracker = (HashTrieNode)this.root;

            for (A pointerKey: key) {
                if(tracker.pointers.find(pointerKey) == null) {
                    tracker.pointers.insert(pointerKey, new HashTrieNode());
                }
                tracker = tracker.pointers.find(pointerKey);
            }
            returnVal = tracker.value;
            tracker.value = value;
            if (returnVal == null) this.size++;
        }
        return returnVal ;
    }

    @Override
    public V find(K key) {
        if (key == null) throw new IllegalArgumentException();
        if (this.root == null) return null;

        HashTrieNode tracker = (HashTrieNode)this.root;
        for (A pointerKey: key) {
            tracker = tracker.pointers.find(pointerKey);
            if (tracker == null) return null;
        }
        return tracker.value;
    }

    @Override
    public boolean findPrefix(K key) {
        if (key == null) throw new IllegalArgumentException();
        if (this.root == null) return false;

        HashTrieNode tracker = (HashTrieNode)this.root;
        for (A pointerKey: key) {
            tracker = tracker.pointers.find(pointerKey);
            if (tracker == null) return false;
        }
        return true;
        //V val = this.find(key);
        //return (val != null);

    }

    @Override
    public void delete(K key) {
        throw new UnsupportedOperationException();
        //if (key == null) throw new IllegalArgumentException();
        //delete(key.iterator(), (HashTrieNode)this.root);
    }

    private boolean delete(Iterator<A> i, HashTrieNode temp) {
        throw new UnsupportedOperationException();
        /* if (!i.hasNext()) {
            temp.value = null;
            this.size--;
            return temp.pointers.isEmpty();
        }
        A keyTracker = i.next();
        boolean deleted = false;

        if (temp.pointers.containsKey(keyTracker)) {
            deleted = delete(i, temp.pointers.get(keyTracker));
        }
        if(deleted) {
            temp.pointers.remove(keyTracker);
        }
        if(temp.value == null && temp.pointers.isEmpty()) return true;
        return false; */
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
        //this.root = new HashTrieNode();
    }
}