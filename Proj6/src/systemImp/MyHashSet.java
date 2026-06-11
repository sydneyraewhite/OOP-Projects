package systemImp;

/**
 * A simple implementation of a HashSet using open addressing with linear probing.<br>
 * This HashSet works specifically with Strings.<br>
 * 
 * It enforces the following constraints:<br>
 * - Strings must not be null, no longer than 5 characters, and only use a to z, otherwise an exception is thrown.<br>
 * - A custom hash code function uses hash codes based on the prime number 19.<br>
 * - The initial size of the table is 2, and it dynamically resizes to the next prime when necessary.<br>
 * 
 * <p>
 * The set uses open addressing and linear probing for collision resolution.<br>
 * When the load factor is >= a customized threshold (default is 0.75), the table is rehashed to a larger prime size.</p>
 * 
 * <p>
 * This class ensures that the set operates efficiently and does not allow duplicate entries.
 * </p>
 */
public class MyHashSet {

    private static final String DELETED = "DELETED";  // Sentinel for deleted entries
    private static final String EMPTY = null;         // Sentinel for empty entries
    
    // Array of primes for resizing the hash table
    // These primes are chosen to ensure that the table size grows by a "double prime" strategy.
    private static final int[] PRIMES = {2, 5, 11, 23, 47, 97, 197, 397, 797, 1597};
    
    private String[] table;      // The actual table where the strings are stored
    private int size;            // The current number of elements in the set
    private int primeIndex;      // Index of the current prime number used for table size
    private double loadFactorThreshold;  // The threshold at which to rehash the table
    
    
    public MyHashSet() {
    	table = new String[PRIMES[0]];
    	size = 0;
    	primeIndex = 0;
    	loadFactorThreshold = 0.75;
    }
    
    public void setLoadFactorThreshold(double threshold) {
    	if (threshold != 0.45 && threshold != 0.60 && threshold != 0.75 && threshold != 0.85) {
    		throw new IllegalArgumentException("Invalid threshold value");
    	}
    	loadFactorThreshold = threshold;
    }
    
    public double getLoadFactor() {
    	return size / (double) table.length;
    }
    
    public String toString() {
    	StringBuilder sb = new StringBuilder();
    	for (int i = 0; i < table.length; i++) {
    		if (table[i] == null) {
    			sb.append("Index ").append(i).append(": null\n");
    		} else if (table[i] == DELETED) {
    			sb.append("Index ").append(i).append(": DELETED\n");
    		} else {
    			sb.append("Index ").append(i).append(": ").append(table[i]).append("\n");
    		}
    	}
    	return sb.toString();
    }
    
    /**
     * Validates that a key is non-null, at most 5 characters long,
     * and contains only lowercase letters. Keys must meet all three
     * conditions — failing any one of them makes the key unusable
     * as a hash input and invalid for storage in this set.
     */
    private boolean isValidKey(String key) {
    	if (key == null || key.length() > 5 || key.length() == 0) return false;
    	for (char c : key.toCharArray()) {
    		if (c < 'a' || c > 'z') return false;
    	}
    	return true;
    }
    
    /**
     * Computes a hash code using a polynomial rolling hash with prime 19.
     * Each character's ASCII value is folded in by multiplying the running
     * hash by 19 before adding the next character. This makes the result
     * sensitive to character order — "bat" and "tab" produce different codes.
     * We use 19 instead of Java's default 31 to differentiate our implementation.
     */
    public int myHashCode(String key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key is not valid.");
    	}
    	int hash = 0;
    	int prime = 19;
    	for (char c : key.toCharArray()) {
    		hash = hash * prime + (int) c;
    	}
    	return hash;
    	
    }
    
    /**
     * Maps a key to a valid table index using the division method.
     * We add table.length before the final modulo to guard against
     * negative results — Java's % operator preserves the sign of the
     * dividend, so a negative hash code would produce a negative index
     * without this correction.
     */
    private int hash(String key) {
    	return (myHashCode(key) % table.length + table.length) % table.length;
    }
    
    /**
     * Inserts a key using linear probing to resolve collisions.
     * If a DELETED slot is encountered during probing, we record it
     * but continue scanning to confirm the key isn't already present
     * further down the chain. Only after ruling out a duplicate do we
     * insert at the first DELETED slot (or the first null if none was seen).
     * This reuse of DELETED slots keeps the table compact over time.
     */
    public void add(String key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key is not valid.");
    	}
    	
    	int index = hash(key);
    	int firstDeleted = -1;
    	
    	for(int i = 0; i < table.length; i++) {
    		int probe = (index + i) % table.length;
    		
    		if(table[probe] == null) {
    			if(firstDeleted != -1) {
    				table[firstDeleted] = key;
    			} else {
    				table[probe] = key;
    			}
    			size++;
    			if(getLoadFactor() > loadFactorThreshold) rehash();
    			return;
    		} else if (table[probe] == DELETED) {
    			if (firstDeleted == -1) firstDeleted = probe;
    		} else if (table[probe].equals(key)) {
    			return;
    		}
    	}
    	
    	if (firstDeleted != -1) {
    		table[firstDeleted] = key;
    		size++;
    		if(getLoadFactor() > loadFactorThreshold) rehash();
    	}
    }
    
    public void remove(String key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key is not valid.");
    	}
    	
    	int index = hash(key);
    	
    	for(int i = 0; i < table.length; i++) {
    		int probe = (index + i) % table.length;
    		
    		if(table[probe] == null) return;
    		if (table[probe] != DELETED && table[probe].equals(key)) {
    			table[probe] = DELETED;
    			size--;
    			return;
    		}
    	}
    }
    
    /**
     * Rehashes all active entries into a larger table.
     * DELETED tombstones are intentionally not copied over — rehashing
     * is a clean slate, so old tombstones would just waste space.
     * We call add() on each live entry so the hash indices are
     * recomputed correctly for the new table size.
     */
    private void rehash() {
    	primeIndex++;
    	if (primeIndex >= PRIMES.length) {
    		throw new IllegalStateException("Cannot resize further.");
    	}
    	
    	String[] oldTable = table;
    	table = new String[PRIMES[primeIndex]];
    	size = 0;
    	
    	for (String entry : oldTable) {
    		if (entry != null && entry != DELETED) {
    			add(entry);
    		}
    	}
    }
    
    public int size() {
    	return size;
    }
    
    /**
     * Searches for a key using linear probing, skipping DELETED slots.
     * Probing stops early at a null slot because a null means the key
     * was never inserted past this point — if it existed, it would have
     * occupied this slot or a slot before it.
     */
    public boolean contains(String key) {
    	if (!isValidKey(key)) {
    		throw new IllegalArgumentException("Key is not valid.");
    	}
    	
    	int index = hash(key);
    	
    	for(int i = 0; i < table.length; i++) {
    		int probe = (index + i) % table.length;
    		
    		if(table[probe] == null) return false;
    		if(table[probe] != DELETED && table[probe].equals(key)) return true;
    	}
    	return false;
    }
    
    public boolean containsAll(String[] keys) {
    	if (keys == null) return false;
    	for(String key : keys) {
    		if(!isValidKey(key)) return false;
    		if(!contains(key)) return false;
    	}
    	return true;
    }
    
    
    
}

