package tests;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Comparator;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;



@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PublicTests {
	
	
	/*---------Linear Tests -------------------------*/


    /**
     * Tests three searches in one method.
     */
	@Test
    public void testMultipleSearches() {
    	
    	String []words = new String[]{"mango", "apple", "quince", "banana", "strawberry",
                 "date", "kiwi", "peach", "fig", "raspberry",
                 "cherry", "nectarine", "lemon", "grape", "orange"};

    	
        StringBuilder log1 = new StringBuilder();
        StringBuilder log2 = new StringBuilder();
        StringBuilder log3 = new StringBuilder();

        // Search for "peach" (expected index 7)
        int index1 = SearchAndSortUtil.bidirectionalLinearSearch(words, "peach", 0, words.length - 1, log1);
        assertEquals("Searching for 'peach' should return index 7", 7, index1);

        // Search for "mango" (expected index 0)
        int index2 = SearchAndSortUtil.bidirectionalLinearSearch(words, "mango", 0, words.length - 1, log2);
        assertEquals("Searching for 'mango' should return index 0", 0, index2);

        // Search for "blueberry" (not in array, should return -1)
        int index3 = SearchAndSortUtil.bidirectionalLinearSearch(words, "blueberry", 0, words.length - 1, log3);
        assertEquals("Searching for 'blueberry' should return -1", -1, index3);
      
        String result = log1.toString() + log2.toString()+ log3.toString();
        TestsSupport.assertCorrect("pubTest00.txt", result);

        
    }
	
	
	/*---------Bubble Tests -------------------------*/
	
	 /**
     * Test bidirectional bubble sort with integers.
     */
	@Test
    public void testBidirectionalBubbleSort() {
        Integer[] arr = {5, 2, 9, 1, 5, 7};
        

        // Perform sorting
        String result = SearchAndSortUtil.bidirectionalBubbleSort(arr);
        result+= Arrays.toString(arr);

        // Verify expected output
        TestsSupport.assertCorrect("pubTest01.txt", result);

    }
	
	
	
	 /*---------Selection Tests -------------------------*/
	 @Test
	    public void testRecursiveBidirectionalSelectionSortWithPersonsAndStudents() {
	        // Create an array with mixed Persons and Students
	        Person[] people = {
	            new Student("Alice", 5, 3.8),
	            new Person("Bob", 2),
	            new Student("Charlie", 8, 3.6),
	            new Person("David", 4),
	            new Student("Eve", 1, 3.9),
	            new Person("Frank", 7),
	            new Student("Grace", 3, 3.7),
	            new Person("Hank", 10),
	            new Student("Ivy", 6, 3.5),
	            new Person("Jack", 9)
	        };

	        // StringBuilder to log swaps
	        StringBuilder log = new StringBuilder();

	        // Perform sorting
	        SearchAndSortUtil.recursiveBidirectionalSelectionSort(people, 0, people.length - 1, log);

	        // Append final sorted array to log
	        log.append("Final sorted array: ").append(Arrays.toString(people)).append("\n");

	        // Verify sorting by id
	        assertEquals(1, people[0].getId());
	        assertEquals(10, people[9].getId());

	        // Verify expected output (log of swaps + final sorted array)
	        TestsSupport.assertCorrect("pubTest02.txt", log.toString());

	 }
	
	
    /*---------PriorityList Tests -------------------------*/
     
	/**
     * Tests the add method when T implements Comparable<T>.
     * 
     * Verifies that:
     * - Elements are inserted in sorted order.
     * - The size updates correctly after multiple insertions.
     * - 2 binary search method work
     */
    @Test
    public void testAddComparable() {
        PriorityList<Integer> list = new PriorityList<>(10, false, null); // Using natural ordering

        // Insert elements in an unsorted order
        list.add(50);
        list.add(20);
        list.add(40);
        list.add(10);
        list.add(60);
        list.add(30);
        list.binarySearchInsertionPoint(35);
        
      

        // Verify size after insertion
        assertEquals("Size should be 6 after adding six elements", 6, list.size());

        // Verify that elements are sorted
        assertEquals("First element should be 10", (Integer) 10, list.get(0));
        assertEquals("Second element should be 20", (Integer) 20, list.get(1));
        assertEquals("Third element should be 30", (Integer) 30, list.get(2));
        assertEquals("Fourth element should be 40", (Integer) 40, list.get(3));
        assertEquals("Fifth element should be 50", (Integer) 50, list.get(4));
        assertEquals("Sixth element should be 60", (Integer) 60, list.get(5));

        assertEquals(list.binarySearchInsertionPoint(35), 3);
        assertEquals(list.binarySearchInsertionPoint(61), 6);
        assertEquals(list.binarySearchFind(61), -1);
        assertEquals(list.binarySearchFind(60), 5);

    }


    /**
     * Tests the add method of PriorityList when using a custom Comparator.
     */
   

        @Test
        public void testAddWithComparator() {
            // Comparator for descending order
            Comparator<Integer> descendingOrder = (a, b) -> Integer.compare(b, a);
            
            // Create a PriorityList using the comparator
            PriorityList<Integer> list = new PriorityList<>(10, true, descendingOrder);
            
            // Add elements
            list.add(50);
            list.add(20);
            list.add(40);
            list.add(10);
            list.add(30);
            list.add(60);

            // Verify order (descending)
            assertEquals("First element should be 60", (Integer) 60, list.get(0));
            assertEquals("Last element should be 10", (Integer) 10, list.get(list.size() - 1));
            assertEquals("List size should be 6", 6, list.size());

            // Verify expected output
            TestsSupport.assertCorrect("pubTest03.txt", list.toString());

        }
   

/**
 * Tests the remove method of PriorityList with a mix of Student and Person objects.
 */
        @Test
        public void testRemoveWithMixedTypes() {
            
            PriorityList<Person> list = new PriorityList<>(3, false, null);

            // Add mixed Person and Student objects
            Person p1 = new Person("Alice", 3);
            Student s1 = new Student("Bob", 1, 3.8);
            Student s2 = new Student("Charlie", 2, 3.2);

            list.add(p1);
            list.add(s1);
            list.add(s2);

            // List should now be full
            assertTrue("List should be full", list.isFull());
            assertEquals("Size should be 3", 3, list.size());

            // Verify order (by ID)
            assertEquals("First should be Bob (id=1)", "Bob", list.get(0).getName());
            assertEquals("Second should be Charlie (id=2)", "Charlie", list.get(1).getName());
            assertEquals("Third should be Alice (id=3)", "Alice", list.get(2).getName());

            // Remove Bob (id=1)
            assertTrue("Removing Bob should return true", list.remove(s1));
            assertEquals("Size should be 2", 2, list.size());
            assertEquals("First should now be Charlie (id=2)", "Charlie", list.get(0).getName());

            // Remove Charlie (id=2)
            assertTrue("Removing Charlie should return true", list.remove(s2));
            assertEquals("Size should be 1", 1, list.size());
            assertEquals("Only element left should be Alice", "Alice", list.get(0).getName());

            // Remove Alice (id=3)
            assertTrue("Removing Alice should return true", list.remove(p1));
            assertEquals("Size should be 0", 0, list.size());
            assertTrue("List should be empty", list.isEmpty());

            // Try removing from empty list
            assertFalse("Removing from empty list should return false", list.remove(p1));

            // List should still be empty
            assertEquals("Size should still be 0", 0, list.size());
        }
     
        /*-----merge------*/
        @Test
        public void testMergePriorityLists() {
            // Create two PriorityLists using natural ordering (Comparable)
            PriorityList<Integer> list1 = new PriorityList<>(10, false, null);
            PriorityList<Integer> list2 = new PriorityList<>(10, false, null);

            // Populate list1
            list1.add(5);
            list1.add(15);
            list1.add(25);
            list1.add(35);
            list1.add(45);
            list1.add(55);
            list1.add(65);
            list1.add(75);
            list1.add(85);
            list1.add(95);

            // Populate list2
            list2.add(10);
            list2.add(20);
            list2.add(30);
            list2.add(40);
            list2.add(50);
            list2.add(60);
            list2.add(70);
            list2.add(80);
            list2.add(90);
            list2.add(100);

            // Merge the lists
            PriorityList<Integer> mergedList = PriorityListUtils.mergePriorityLists(list1, list2);

            // Expected sorted order after merging
            Integer[] expectedOrder = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 100};

            // Verify merged list size
            assertEquals("Merged list should have 20 elements", 20, mergedList.size());

            // Verify the entire sorted order
            for (int i = 0; i < expectedOrder.length; i++) {
                assertEquals("Element at index " + i + " should be " + expectedOrder[i], expectedOrder[i], mergedList.get(i));
            }

     

            // Create a PriorityList using a Comparator (which should cause an exception when merging)
            Comparator<Integer> descendingOrder = (a, b) -> Integer.compare(b, a);
            PriorityList<Integer> invalidList = new PriorityList<>(10, true, descendingOrder);
            invalidList.add(90);
            invalidList.add(70);
            invalidList.add(50);
            invalidList.add(30);
            invalidList.add(10);
         

            // Ensure merging with a comparator-based list throws an exception
            try {
                PriorityListUtils.mergePriorityLists(list1, invalidList);
                fail("Expected IllegalArgumentException when merging with a comparator-based list");
            } catch (IllegalArgumentException e) {
                assertEquals("One or both lists are not sorted using Comparable", e.getMessage());
            }
        }
	
}