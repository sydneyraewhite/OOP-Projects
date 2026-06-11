package tests;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTests {

    // Test 1: heapify min-heap correctness — root must be the minimum element
    @Test
    public void test1HeapifyMinHeapRootIsMin() {
        Integer[] arr = new Integer[8];
        arr[0] = null;
        arr[1] = 99;
        arr[2] = 45;
        arr[3] = 23;
        arr[4] = 67;
        arr[5] = 12;
        arr[6] = 55;
        arr[7] = 34;

        HeapUtils.heapify(arr, true, null);

        // Root (index 1) must be the minimum
        assertEquals(Integer.valueOf(12), arr[1]);
    }

    // Test 2: heapify max-heap correctness — root must be the maximum element
    @Test
    public void test2HeapifyMaxHeapRootIsMax() {
        Integer[] arr = new Integer[6];
        arr[0] = null;
        arr[1] = 10;
        arr[2] = 40;
        arr[3] = 20;
        arr[4] = 5;
        arr[5] = 30;

        HeapUtils.heapify(arr, false, null);

        assertEquals(Integer.valueOf(40), arr[1]);
    }

    // Test 3: heapSort produces ascending order
    @Test
    public void test3HeapSortAscending() {
        Integer[] arr = new Integer[7];
        arr[0] = null;
        arr[1] = 5;
        arr[2] = 3;
        arr[3] = 8;
        arr[4] = 1;
        arr[5] = 9;
        arr[6] = 2;

        HeapUtils.heapSort(arr, null);

        // After heapsort, array should be sorted ascending at indices 1–6
        for (int i = 1; i < arr.length - 1; i++) {
            assertTrue("Expected arr[" + i + "] <= arr[" + (i+1) + "]",
                arr[i] <= arr[i + 1]);
        }
    }

    // Test 4: PriorityQueue remove returns elements in ascending order (min-heap)
    @Test
    public void test4PQMinHeapRemoveOrder() throws Exception {
        Integer[] arr = new Integer[6];
        arr[0] = null;
        arr[1] = 15;
        arr[2] = 10;
        arr[3] = 20;
        arr[4] = 30;
        arr[5] = 5;

        PriorityQueue<Integer> pq = new PriorityQueue<>(arr, true);

        int first  = pq.remove(null);
        int second = pq.remove(null);
        int third  = pq.remove(null);

        // Min-heap removes should come out in ascending order
        assertTrue("first remove should be smallest",  first  <= second);
        assertTrue("second remove should be <= third", second <= third);
        assertEquals(5,  first);
        assertEquals(10, second);
        assertEquals(15, third);
    }

}