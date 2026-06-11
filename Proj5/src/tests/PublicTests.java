package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PublicTests {
	
	
	/**
	 * Tests basic operations on a DoublyLinkedList, including:
	 * - Adding elements to the front and back (addFirst, addLast)
	 * - Removing elements from the front and back (removeFirst, removeLast)
	 * - Checking the size of the list
	 * - Verifying the correct order of elements
	 * - Ensuring toString() produces the expected output
	 */
	@Test
	public void testAddFirstAddLastRemoveFirstAndLast() {
	    // Create a DoublyLinkedList to test
	    DoublyLinkedList<String> list = new DoublyLinkedList<>();

	    /**
	     * Add elements using addFirst and addLast:
	     * List should be: [Sunflower, Daisy, Rose, Tulip, Lily, Orchid]
	     */
	    list.addFirst("Rose");
	    list.addLast("Tulip");
	    list.addFirst("Daisy");
	    list.addLast("Lily");
	    list.addFirst("Sunflower");
	    list.addLast("Orchid");

	    // Verify size
	    assertEquals("List size should be 6", 6, list.getSize());
	    
	    
	    String result = list.toString()+"\n";
	    list.removeFirst();
	    list.removeLast();
	    list.removeLast();
	    list.removeFirst();
	    result += list.toString()+"\n";
	  
       TestsSupport.assertCorrect("pubTest01.txt", result);
    }
	
	/**
     * Tests iterator functionality with an enhanced for-loop
     * using a DoublyLinkedList of flower names.
     */
    @Test
    public void testIteratorWithEnhancedForLoop() {
        // Create a DoublyLinkedList to test
        DoublyLinkedList<String> list = new DoublyLinkedList<>();

        /**
         * Add elements using addFirst and addLast:
         * List should be: [Sunflower, Daisy, Rose, Tulip, Lily, Orchid]
         */
        list.addFirst("Rose");
        list.addLast("Tulip");
        list.addFirst("Daisy");
        list.addLast("Lily");
        list.addFirst("Sunflower");
        list.addLast("Orchid");

        // Use an enhanced for-loop to iterate over the list
        StringBuilder result = new StringBuilder();
        for (String flower : list) {
            result.append(flower).append(" ");
        }
        result.append("\n");
        assertEquals("The first element should be Sunflower", "Sunflower", list.getFirst());
        assertEquals("The last element should be Orchid", "Orchid", list.getLast());

        // Verify expected output
        TestsSupport.assertCorrect("pubTest02.txt", result.toString());
    }

	   
	/**
     * Tests iterator calling next() and remove() multiple times
     */
    @Test
    public void testIteratorNextAndRemove() {
        // Create a DoublyLinkedList to test
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Apple");
        list.addLast("Banana");
        list.addLast("Cherry");
        
        String result = list.toString()+"\n";
        
        // Create an iterator
        Iterator<String> iterator = list.iterator();
        
        assertEquals("The first element should be Apple", "Apple", list.getFirst());
        assertEquals("The last element should be Cherry", "Cherry", list.getLast());

        
        // Check if the first element is "Apple"
        assertTrue(iterator.hasNext());
        assertEquals("Apple", iterator.next());
        
        // Call remove() on the first element ("Apple")
        iterator.remove();
        
       result += list.toString()+"\n";
       
       assertEquals("The first element should be Banana", "Banana", list.getFirst());
       assertEquals("The last element should be Cherry", "Cherry", list.getLast());

        
        // Verify that the first element is now "Banana"
        assertTrue(iterator.hasNext());
        assertEquals("Banana", iterator.next());
        
        // Call remove() on the second element ("Banana")
        iterator.remove();
        result += list.toString()+"\n";
        
        assertEquals("The first element should be Cherry", "Cherry", list.getFirst());
        assertEquals("The last element should be Cherry", "Cherry", list.getLast());
    
        // Verify that the first element is now "Cherry"
        assertTrue(iterator.hasNext());
        assertEquals("Cherry", iterator.next());
        
       
        // Call remove() on the third element ("Cherry")
        iterator.remove();
        result += list.toString()+"\n";
        // Use assertThrows to check if a NoSuchElementException is thrown
        assertThrows(NoSuchElementException.class, () -> {
            list.getFirst();  // This should throw an exception since the list is empty
        });
        // Use assertThrows to check if a NoSuchElementException is thrown
        assertThrows(NoSuchElementException.class, () -> {
            list.getLast();  // This should throw an exception since the list is empty
        });
        
        // Verify that the list is empty
        assertFalse(iterator.hasNext());
        
        TestsSupport.assertCorrect("pubTest03.txt", result);

        
    }
    /**
     * Tests calling next() after running out of elements
     * and calling remove() before calling next().
     */
    @Test
    public void testIteratorNextAfterNoElementsAndRemoveBeforeNext() {
        // Create a DoublyLinkedList and add some elements
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Apple");
        list.addLast("Banana");
        list.addLast("Cherry");

        // Create an iterator
        Iterator<String> iterator = list.iterator();
        
     
        // Call remove() before next(), should throw IllegalStateException
        try {
            iterator.remove();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Exception is expected, no need to check the message
        }

        // Remove all elements using the iterator
        iterator.next(); // "Apple"
        iterator.remove();
        iterator.next(); // "Banana"
        iterator.remove();
        // Call remove() before next(), should throw IllegalStateException
        try {
            iterator.remove();
            fail("Expected IllegalStateException to be thrown");
        } catch (IllegalStateException e) {
            // Exception is expected, no need to check the message
        }
        iterator.next(); // "Cherry"
        iterator.remove();

        // Now the list is empty, and calling next() should throw NoSuchElementException
        try {
            iterator.next();
            fail("Expected NoSuchElementException to be thrown");
        } catch (NoSuchElementException e) {
            // Exception is expected, no need to check the message
        }

    }
    
    
 // Helper method to create a DoublyLinkedList with bird names
    private DoublyLinkedList<String> createBirdList(List<String> birds) {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        for (String bird : birds) {
            list.addLast(bird);  // Add each bird name to the list
        }
        return list;
    }

    /**
     * Test for removeAll where some birds or all birds are within the specified range.
     */
    @Test
    public void testRemoveAll() {
        // Bird names to be added to the list
        List<String> birds = Arrays.asList("Eagle", "Falcon", "Hawk", "Owl", "Penguin");
        
        // Create a list of birds
        DoublyLinkedList<String> list = createBirdList(birds);

        // Case 1: Remove all birds in range ("Eagle" to "Owl")
        list.removeAllInRange("Eagle", "Owl");
        // The remaining list should only contain "Penguin" in this case
        assertEquals("[Penguin]", list.toString());
        assertEquals("The first element should be Penguin", "Penguin", list.getFirst());
        assertEquals("The last element should be Penguin", "Penguin", list.getLast());
    
        
        // Re-create the list for the second case (reset the list)
        list = createBirdList(birds);

        // Case 2: Remove birds in range ("Falcon" to "Owl") 
        list.removeAllInRange("Falcon", "Owl");
        // The remaining list should only contain "Eagle" and "Penguin"
        assertEquals("[Eagle, Penguin]", list.toString());
        assertEquals("The first element should be Eagle", "Eagle", list.getFirst());
        assertEquals("The last element should be Penguin", "Penguin", list.getLast());
    
        // Re-create the list for the third case (reset the list)
        DoublyLinkedList<String> list1 = createBirdList(birds);
     // Case 3: Remove ALL birds in range 
        list1.removeAllInRange("Eagle", "Penguin");
        // The remaining list should contain nothing
        assertEquals("[]", list1.toString());
        
        // Use assertThrows to check if a NoSuchElementException is thrown
        assertThrows(NoSuchElementException.class, () -> {
            list1.getFirst();  // This should throw an exception since the list is empty
        });
        // Use assertThrows to check if a NoSuchElementException is thrown
        assertThrows(NoSuchElementException.class, () -> {
            list1.getLast();  // This should throw an exception since the list is empty
        });

    }
    
    @Test
    public void testInsertionSort() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        // Add elements in unsorted order
        list.addLast(3);
        list.addLast(1);
        list.addLast(4);
        list.addLast(2);

        // Sort the list
	    String result = list.insertionSort();


        TestsSupport.assertCorrect("pubTest04.txt", result);

        
        // Check full list string representation (optional)
        assertEquals("List should be sorted", "[1, 2, 3, 4]", list.toString());
    }
	

	 
}

	
