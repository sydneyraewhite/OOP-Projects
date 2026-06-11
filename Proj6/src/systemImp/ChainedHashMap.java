package systemImp;

import java.util.LinkedList; 
import java.util.ArrayList; //ONLY use for return value of getValues method
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A HashMap implementation using separate chaining with LinkedLists.<br>
 * 
 * - Stores Integer keys and String values. <br>
 * - Uses a hash function with compression: ((A * key + B) % P) % table.length<br>
 * - Rehashes when the average chain length exceeds a threshold.<br>
 * - Does not allow duplicate keys (updates value instead), and keys must be 4-digit integers from 1000 (inclusive) to 9999 (inclusive).<br>
 * - Does not allows null values.<br>
 * - Uses an initial size of 2 to encourage collisions.<br>
 * - Since keys are 4-digit numbers,  integer overflow is not a concern.<br>
 */
public class ChainedHashMap implements Iterable<ChainedHashMap.Entry> {
    
    /** A large prime number for hashing */
    private static final int P = 104729;
    /** Large prime multiplier for hashing */
    private static final int A = 2347;
    /** Large prime offset for hashing */
    private static final int B = 7919;

    /** Initial size of the hash table */
    private static final int INITIAL_SIZE = 2;
    /** Threshold for rehashing: when avg chain length exceeds this, we rehash */
    private static final int RESIZE_THRESHOLD = 3;
    /** Prime sizes for resizing */
    private static final int[] PRIMES = {2, 5, 11, 23, 47, 97, 197, 397};

    /** The hash table, where each index contains a linked list of entries */
    private LinkedList<Entry>[] table;
    /** The number of key-value pairs stored */
    private int size;
    /** The index of the current prime in PRIMES */
    private int primeIndex;

    /**
     * Entry class for storing key-value pairs in the linked list.<br>
     * Do NOT MAKE ANY CHANGES to  public static class Entry
     */
    public static class Entry {
        int key;
        String value;
        
        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
        public int getKey() {  //used for testing only.  NOT needed in code you write
            return key;
        }
    }
    
    /**
     * Initializes the table with INITIAL_SIZE buckets, all null.
     * Buckets are lazily initialized — a LinkedList is only created
     * at a bucket when the first key hashes there, saving memory
     * when the table is sparsely populated.
     */
    @SuppressWarnings("unchecked")
    public ChainedHashMap() {
    	table = new LinkedList[INITIAL_SIZE];
    	size = 0;
    	primeIndex = 0;
    }
    
    /**
     * Maps a key to a bucket index using a multiply-add-mod hash function.
     * The formula ((A * key + B) % P) % table.length spreads keys more
     * uniformly than a plain modulo would, reducing clustering.
     * P, A, and B are large primes chosen to minimize collisions across
     * the valid key range of 1000–9999.
     */
    private int hash(int key) {
    	return ((A * key + B) % P) % table.length;
    }
    
    public int getSize() {
    	return size;
    }
    
    public int getTableLength() {
    	return table.length;
    }
    
    /**
     * Keys are restricted to 4-digit integers so the hash function
     * operates on a well-defined, bounded input range. Values outside
     * 1000–9999 are rejected early to prevent unpredictable hashing behavior.
     */
    private boolean isValidKey(int key) {
    	return key >= 1000 && key <= 9999;
    }
    
    /**
     * Inserts or updates a key-value pair using separate chaining.
     * If the key already exists in the chain at the computed index,
     * its value is updated in place — no duplicate entries are created.
     * If the key is new, a fresh Entry is appended to the end of the chain.
     * After insertion, if the average chain length exceeds RESIZE_THRESHOLD,
     * rehash() is called to redistribute entries across a larger table.
     */
    public void put (int key, String value) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key must be between 1000 and 9999.");
    	}
    	
    	if (value == null) {
    		throw new IllegalArgumentException("Value must not be null.");
    	}
    	
    	int index = hash(key);
    	
    	if (table[index] == null) {
    		table[index] = new LinkedList<>();
    	}
    	
    	for (Entry e : table[index]) {
    		if (e.key == key) {
    			e.value = value;
    			return;
    		}
    	}
    	
    	table[index].add(new Entry(key, value));
    	size++;
    	
    	if ((double) size / table.length > RESIZE_THRESHOLD) {
    		rehash();
    	}
    }
    
    public String get(int key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key must be between 1000 and 9999.");
    	}
    	
    	int index = hash(key);
    	
    	if (table[index] == null) return null;
    	
    	for (Entry e : table[index]) {
    		if(e.key == key) return e.value;
    	}
    	
    	return null;
    }
    	
    public boolean containsKey(int key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key must be between 1000 and 9999.");
    	}
    	return get(key) != null;
    }
    	
    public ArrayList<Integer> getValues(String target){
    	if (target == null) {
    		throw new IllegalArgumentException("Target must not be null.");
    	}
    	
    	ArrayList<Integer> result = new ArrayList<>();
    	
    	for (LinkedList<Entry> bucket : table) {
    		if (bucket == null) continue;
    		for (Entry e : bucket) {
    			if (e.value.equals(target)) {
    				result.add(e.key);
    			}
    		}
    	}
    	return result;
    }
    
    /**
     * Removes a key-value pair using the LinkedList iterator's remove() method.
     * Using iterator.remove() is required here because removing directly from
     * a LinkedList while iterating over it with a for-each loop would throw
     * a ConcurrentModificationException. The iterator's remove() is safe
     * because it is designed to modify the list mid-traversal.
     */
    public void remove(int key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key must be between 1000 and 9999.");
    	}
    	
    	int index = hash(key);
    	
    	if (table[index] == null) return;
    	
    	Iterator<Entry> it = table[index].iterator();
    	while(it.hasNext()) {
    		Entry e = it.next();
    		if (e.key == key) {
    			it.remove();
    			size--;
    			return;
    		}
    	}
    }
    
    /**
     * Resizes the table to the next prime in PRIMES and reinserts all entries.
     * A temporary newTable is built first because every key's bucket index
     * depends on table.length — we cannot rehash in place since changing
     * table.length mid-insertion would corrupt the indices of remaining entries.
     * Once all entries are reinserted into newTable, it replaces the old table.
     * Resizing stops permanently once the largest prime (397) is reached.
     */
    @SuppressWarnings("unchecked")
    private void rehash() {
    	primeIndex++;
    	if(primeIndex >= PRIMES.length) return;
    	
    	int newSize = PRIMES[primeIndex];
    	LinkedList<Entry>[] newTable = new LinkedList[newSize];
    	
    	for (LinkedList<Entry> bucket : table) {
    		if (bucket == null) continue;
    		for (Entry e : bucket) {
    			int newIndex = ((A * e.key + B) % P) % newSize;
    			if (newTable[newIndex] == null) {
    				newTable[newIndex] = new LinkedList<>();
    			}
    			newTable[newIndex].add(new Entry(e.key, e.value));
    		}
    	}
    	table = newTable;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for(int i = 0; i < table.length; i++) {
    		sb.append(i).append(" -> ");
    		if (table[i] != null) {
    			for(Entry e : table[i]) {
    				sb.append("(").append(e.key).append(", ").append(e.value).append(") ");
    			}
    		}
    		sb.append("\n");
    	}
    	return sb.toString();
    }
    
    /**
     * Returns an iterator that traverses all entries across every bucket in order.
     * bucketIndex tracks which bucket we are currently scanning, and bucketIterator
     * handles traversal within that bucket. hasNext() is responsible for advancing
     * bucketIndex whenever the current bucket is exhausted, so next() can simply
     * delegate to bucketIterator.next() without needing to manage bucket transitions
     * itself. This keeps the two responsibilities cleanly separated.
     */
    public Iterator<ChainedHashMap.Entry> iterator() {
    	return new Iterator<ChainedHashMap.Entry>() {
    		int bucketIndex = 0;
    		Iterator<Entry> bucketIterator = null;
    		
    		@Override
    		public boolean hasNext() {
    			if (bucketIterator != null && bucketIterator.hasNext()) {
    				return true;
    			}
    			
    			while(bucketIndex < table.length) {
    				if (table[bucketIndex] != null && !table[bucketIndex].isEmpty()) {
    					bucketIterator = table[bucketIndex].iterator();
    					bucketIndex++;
    					return true;
    				}
    				bucketIndex++;
    			}
    			return false;
    		}
    		
    		@Override
    		public ChainedHashMap.Entry next() {
    			if (!hasNext()) {
    				throw new NoSuchElementException("No more elements in the map.");
    			}
    			return bucketIterator.next();
    		}
    	};	
    }
   
}
    

