package datastructures.dictionaries;

import cse332.datastructures.containers.Item;
import cse332.exceptions.NotYetImplementedException;
import cse332.interfaces.misc.DeletelessDictionary;
import cse332.interfaces.misc.Dictionary;

import java.util.Iterator;
import java.util.function.Supplier;

/**
 * 1. You must implement a generic chaining hashtable. You may not
 * restrict the size of the input domain (i.e., it must accept
 * any key) or the number of inputs (i.e., it must grow as necessary).
 * 3. Your HashTable should rehash as appropriate (use load factor as
 * shown in class!).
 * 5. HashTable should be able to resize its capacity to prime numbers for more
 * than 200,000 elements. After more than 200,000 elements, it should
 * continue to resize using some other mechanism.
 * 6. We suggest you hard code some prime numbers. You can use this
 * list: http://primes.utm.edu/lists/small/100000.txt
 * NOTE: Do NOT copy the whole list!
 * 7. When implementing your iterator, you should NOT copy every item to another
 * dictionary/list and return that dictionary/list's iterator.
 */
public class ChainingHashTable<K, V> extends DeletelessDictionary<K, V> {
    private Supplier<Dictionary<K, V>> newChain;
    private static final int[] SIZES = {19,41,89,191,367,751,1523,3119,6247,12553,25171,50503,101837,204511,411703,823831};

    //load factor threshold before resizing hash table
    private static final double RESIZE_LOAD_FACTOR = 0.75;
    private int sizesTracker;
    private Dictionary<K,V>[] hashTable;


    public ChainingHashTable(Supplier<Dictionary<K, V>> newChain) {
        this.newChain = newChain;
        this.size = 0;
        this.sizesTracker = 0;
        this.hashTable = (Dictionary<K,V>[]) new Dictionary[SIZES[this.sizesTracker]];
        for (int i = 0; i < this.hashTable.length; i++) {
            hashTable[i] = newChain.get();
        }
    }

    private void rehash() {
        int newSize = (15 * (this.hashTable.length - 1) / 6); //what should i set this to?
        if (this.sizesTracker < SIZES.length - 1) {
            this.sizesTracker++;
            newSize = SIZES[this.sizesTracker];
        }
        Dictionary<K,V>[] temp = this.hashTable;
        this.hashTable = new Dictionary[newSize];
        this.size = 0;
        for (Dictionary<K,V> t: temp) {
            if (t != null) {
                Iterator<Item<K,V>> iterator = t.iterator();
                while (iterator.hasNext()) {
                    Item<K,V> item = iterator.next();
                    this.insert(item.key, item.value);
                }
            }
        }

    };

    private int getHashVal(K key) {
        //if (key == null) throw new IllegalArgumentException();
        int hashVal = Math.abs(key.hashCode());
        hashVal /= this.hashTable.length;
        return hashVal;
    }

    private double getLoadFactor() {
        return this.size/(double)this.hashTable.length;
    }

    private Dictionary<K,V> findValue(K key) {
        if (key == null) throw new IllegalArgumentException();

        int hashVal = getHashVal(key);
        Dictionary<K,V> returnVal= this.hashTable[hashVal];
        if (returnVal == null) {
            returnVal = newChain.get();
            this.hashTable[hashVal] = returnVal;
        }

        return returnVal;
    }
    @Override
    public V insert(K key, V value) {
        if (key == null || value == null) throw new IllegalArgumentException();

        if (getLoadFactor() >= RESIZE_LOAD_FACTOR) rehash();

        Dictionary<K,V> temp = findValue(key);
        V prevVal = temp.insert(key, value);
        if (prevVal != null) this.size++;
        return prevVal;
    }

    @Override
    public V find(K key) {
        if (key == null) throw new IllegalArgumentException();
        Dictionary<K,V> temp = findValue(key);
        return temp.find(key);
    }

    @Override
    public Iterator<Item<K, V>> iterator() {
        if (this.hashTable[0] == null) this.hashTable[0] = newChain.get();

    }

    /**
     * Temporary fix so that you can debug on IntelliJ properly despite a broken iterator
     * Remove to see proper String representation (inherited from Dictionary)
     */
    @Override
    public String toString() {
        return "ChainingHashTable String representation goes here.";
    }
}
