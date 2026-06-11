package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;

import java.util.ArrayList;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTests {

    // ===================== ChainedHashMap Tests =====================

    /**
     * Tests that put() correctly updates the value of an existing key
     * instead of inserting a duplicate entry.
     */
    @Test
    public void test01PutUpdatesExistingKey() {
        ChainedHashMap map = new ChainedHashMap();
        map.put(1234, "first");
        map.put(1234, "second");

        // Size should still be 1 — no duplicate should be added
        assertEquals(1, map.getSize());
        // Value should reflect the update
        assertEquals("second", map.get(1234));
    }

    /**
     * Tests that remove() correctly deletes a key-value pair,
     * decrements the size, and that get() returns null after removal.
     */
    @Test
    public void test02RemoveDecreasesSize() {
        ChainedHashMap map = new ChainedHashMap();
        map.put(2222, "hello");
        map.put(3333, "world");
        map.remove(2222);

        // Size should drop by 1
        assertEquals(1, map.getSize());
        // Removed key should no longer be retrievable
        assertNull(map.get(2222));
    }

    /**
     * Tests that getValues() returns all keys that map to a given value,
     * including when multiple keys share the same value.
     */
    @Test
    public void test03GetValuesMultipleKeys() {
        ChainedHashMap map = new ChainedHashMap();
        map.put(1111, "blue");
        map.put(2222, "blue");
        map.put(3333, "red");

        ArrayList<Integer> blueKeys = map.getValues("blue");

        // Both keys mapping to "blue" should appear in the result
        assertEquals(2, blueKeys.size());
        assertTrue(blueKeys.contains(1111));
        assertTrue(blueKeys.contains(2222));
    }

    /**
     * Tests that rehashing occurs when the load factor threshold is exceeded,
     * and that all entries are still accessible after rehashing.
     */
    @Test
    public void test04RehashPreservesAllEntries() {
        ChainedHashMap map = new ChainedHashMap();

        // Insert enough entries to trigger rehashing (threshold is avg chain > 3)
        int[] keys = {1001, 2002, 3003, 4004, 5005, 6006, 7007, 8008};
        for (int k : keys) {
            map.put(k, "val" + k);
        }

        // All entries must still be retrievable after rehashing
        for (int k : keys) {
            assertEquals("val" + k, map.get(k));
        }
    }

    /**
     * Tests that invalid keys and null values are rejected by put(),
     * and that containsKey() throws on invalid input.
     */
    @Test
    public void test05InvalidInputsChainedHashMap() {
        ChainedHashMap map = new ChainedHashMap();

        // Key below 1000 should throw
        assertThrows(IllegalArgumentException.class, () -> map.put(999, "val"));
        // Key above 9999 should throw
        assertThrows(IllegalArgumentException.class, () -> map.put(10000, "val"));
        // Null value should throw
        assertThrows(IllegalArgumentException.class, () -> map.put(1234, null));
        // containsKey with invalid key should throw
        assertThrows(IllegalArgumentException.class, () -> map.containsKey(42));
    }

    // ===================== MyHashSet Tests =====================

    /**
     * Tests that add() rejects invalid keys — null, keys longer than 5 chars,
     * and keys containing non-lowercase characters.
     */
    @Test
    public void test06AddRejectsInvalidKeys() {
        MyHashSet set = new MyHashSet();

        // Null key should throw
        assertThrows(IllegalArgumentException.class, () -> set.add(null));
        // Key longer than 5 characters should throw
        assertThrows(IllegalArgumentException.class, () -> set.add("toolong"));
        // Key with uppercase letters should throw
        assertThrows(IllegalArgumentException.class, () -> set.add("Apple"));
    }

    /**
     * Tests that adding a duplicate key does not increase the size of the set.
     */
    @Test
    public void test07AddDuplicateDoesNotIncreaseSize() {
        MyHashSet set = new MyHashSet();
        set.add("cat");
        set.add("cat"); // duplicate

        // Size should remain 1
        assertEquals(1, set.size());
    }

    /**
     * Tests that remove() marks a slot as DELETED and that a key
     * re-inserted afterward is still findable via contains().
     */
    @Test
    public void test08AddIntoDeletedSlot() {
        MyHashSet set = new MyHashSet();
        set.add("dog");
        set.add("fox");
        set.remove("dog");

        // Re-add into what may be a DELETED slot
        set.add("dog");

        // dog should be findable again
        assertTrue(set.contains("dog"));
        assertEquals(2, set.size());
    }

    /**
     * Tests that containsAll() returns false when the input array is null,
     * contains a missing key, or contains an invalid key.
     */
    @Test
    public void test09ContainsAllEdgeCases() {
        MyHashSet set = new MyHashSet();
        set.add("ant");
        set.add("bee");

        // Null array should return false
        assertFalse(set.containsAll(null));
        // Array with a key not in the set should return false
        assertFalse(set.containsAll(new String[]{"ant", "cat"}));
        // Array with an invalid key should return false
        assertFalse(set.containsAll(new String[]{"ant", "INVALID"}));
        // All valid and present should return true
        assertTrue(set.containsAll(new String[]{"ant", "bee"}));
    }

    /**
     * Tests that setLoadFactorThreshold() rejects invalid values and
     * accepts only the four predefined allowed values.
     */
    @Test
    public void test10SetLoadFactorThresholdValidation() {
        MyHashSet set = new MyHashSet();

        // Invalid threshold values should throw
        assertThrows(IllegalArgumentException.class, () -> set.setLoadFactorThreshold(0.5));
        assertThrows(IllegalArgumentException.class, () -> set.setLoadFactorThreshold(1.0));

        // All four valid thresholds should be accepted without exception
        set.setLoadFactorThreshold(0.45);
        set.setLoadFactorThreshold(0.60);
        set.setLoadFactorThreshold(0.75);
        set.setLoadFactorThreshold(0.85);
    }
}