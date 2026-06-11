package tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;


@FixMethodOrder(MethodSorters.NAME_ASCENDING)

public class PublicTests {

	@Test
	public void testHeapifyMinHeapWithLogging() {
		StringBuilder output = new StringBuilder();
		StringBuffer log = new StringBuffer(); // To capture logs of swaps

		Integer[] arr = new Integer[11];
		arr[0] = null;  // index 0 ignored
		arr[1] = 50;
		arr[2] = 20;
		arr[3] = 60;
		arr[4] = 10;
		arr[5] = 30;
		arr[6] = 70;
		arr[7] = 80;
		arr[8] = 5;
		arr[9] = 40;
		arr[10] = 90;

		// Perform min-heapify with logging enabled
		HeapUtils.heapify(arr, true, log);

		// Append the log to the output
		output.append("Heapified array (min-heap):\n");
		for (int i = 1; i <= 10; i++) {
			output.append(arr[i]).append(" ");
		}
		output.append("\n");
		output.append("-----------------\n");

		// Append the log of heapify swaps
		output.append("Log of swaps during heapify:\n");
		output.append(log.toString());
		output.append("-----------------\n");

		// Assert that the output matches the expected result
	    TestsSupport.assertCorrect("pubTest01.txt", output.toString());

	
	
	}


	@Test
	public void testHeapifyMaxHeapWithLogging() {
		StringBuilder output = new StringBuilder();
		StringBuffer log = new StringBuffer(); // To capture logs of swaps

		Integer[] arr = new Integer[11];
		arr[0] = null;  // index 0 ignored
		arr[1] = 50;
		arr[2] = 20;
		arr[3] = 60;
		arr[4] = 10;
		arr[5] = 30;
		arr[6] = 70;
		arr[7] = 80;
		arr[8] = 5;
		arr[9] = 40;
		arr[10] = 90;

		// Perform max-heapify with logging enabled
		HeapUtils.heapify(arr, false, log);

		// Append the heapified array to the output
		output.append("Heapified array (max-heap):\n");
		for (int i = 1; i <= 10; i++) {
			output.append(arr[i]).append(" ");
		}
		output.append("\n");
		output.append("-----------------\n");

		// Append the log of heapify swaps
		output.append("Log of swaps during heapify:\n");
		output.append(log.toString());
		output.append("-----------------\n");

		// Assert that the output matches the expected result
	    TestsSupport.assertCorrect("pubTest02.txt", output.toString());

	}
	
	 @Test
	    public void testHeapSortWithLogging() {
	        StringBuilder output = new StringBuilder();
	        StringBuffer log = new StringBuffer(); // For capturing swap log

	        Integer[] arr = new Integer[11];
	        arr[0] = null; // index 0 is ignored
	        arr[1] = 42;
	        arr[2] = 7;
	        arr[3] = 19;
	        arr[4] = 33;
	        arr[5] = 12;
	        arr[6] = 55;
	        arr[7] = 1;
	        arr[8] = 27;
	        arr[9] = 8;
	        arr[10] = 36;

	        // Perform heapsort with logging
	        HeapUtils.heapSort(arr, log);

	        // Append sorted array to output
	        output.append("Array after heapsort:\n");
	        for (int i = 1; i <= 10; i++) {
	            output.append(arr[i]).append(" ");
	        }
	        output.append("\n");
	        output.append("-----------------\n");

	        // Append the log of swaps
	        output.append("Log of swaps during heapsort:\n");
	        output.append(log.toString());
	        output.append("-----------------\n");

	        // Assert that output matches expected file
		    TestsSupport.assertCorrect("pubTest03.txt", output.toString());
   
	 }
	 

	 @Test
	    public void testPriorityQueueMinHeapOperations() {
	        StringBuilder output = new StringBuilder();
	        StringBuffer log = new StringBuffer(); // For capturing swaps

	        // Create the heap array
	        Integer[] arr = new Integer[11];
	        arr[0] = null; // index 0 must be null
	        arr[1] = 22;
	        arr[2] = 5;
	        arr[3] = 17;
	        arr[4] = 3;
	        arr[5] = 14;
	        arr[6] = 9;
	        arr[7] = 30;
	        arr[8] = 8;
	        arr[9] = 12;
	        arr[10] = 7;

	        // Create the PriorityQueue (min-heap)
	        PriorityQueue<Integer> pq = new PriorityQueue<>(arr, true);

	        output.append("Initial Min Heap:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        // Delete two elements
	        pq.remove(log);
	        output.append("After 1st remove:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        pq.remove(log);
	        output.append("After 2nd remove:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        // Insert one element
	        pq.insert(6, log);
	        output.append("After inserting 6:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        // Delete one more element
	        pq.remove(log);
	        output.append("After 3rd remove:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        // Insert two elements (success)
	        pq.insert(2, log);
	        output.append("After inserting 2:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        pq.insert(4, log);
	        output.append("After inserting 4:\n");
	        output.append(java.util.Arrays.toString(arr)).append("\n");
	        output.append("-----------------\n");

	        // Try inserting third element (should fail)
	        try {
	            pq.insert(1, log);
	            fail("Expected an exception when inserting into a full queue.");
	        } catch (IllegalStateException e) {
	            output.append("Caught expected exception: ").append(e.getMessage()).append("\n");
	        }
	        output.append("-----------------\n");

	        // Append full swap log
	        output.append("Log of swaps and changes:\n");
	        output.append(log.toString());

	        // Check output against expected file
		    TestsSupport.assertCorrect("pubTest04.txt", output.toString());
  
	 }    
	 
	     
}





