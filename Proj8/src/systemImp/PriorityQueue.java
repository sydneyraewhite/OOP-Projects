package systemImp;

import java.util.Arrays;

public class PriorityQueue<T extends Comparable<T>> {
    private T[] heap;
    private int size; //logical size of the heap
    private boolean isMinHeap;

 
    public PriorityQueue(T[] arr, boolean isMinHeap) {
    	if (arr == null || arr.length <= 1 || arr[0] != null) {
    		throw new IllegalArgumentException("Invalid array: must be non-null, length > 1, and arr[0] must be null.");
    	}
    	this.heap = arr;
    	this.isMinHeap = isMinHeap;
    	
    	this.size = 0;
    	for (int i = 1; i < heap.length; i++) {
    		if(heap[i] != null) size++;
    		else break;
    	}
    	
    	HeapUtils.heapify(heap,  isMinHeap, null);
    }
    
    public T peek() {
    	if (size == 0) {
    		throw new IllegalStateException("PriorityQueue is empty.");
    	}
    	return heap[1];
    }
    
    public T remove(StringBuffer log) {
        if (size == 0) throw new IllegalStateException("PriorityQueue is empty.");

        T removed = heap[1];

        // Swap root with last (even if they're the same index when size == 1)
        if (log != null) {
            log.append("Swapped index 1 (" + heap[1] + ") with index " + size + " (" + heap[size] + ")\n");
        }
        heap[1] = heap[size];
        heap[size] = null;  // set last to null (when size==1, this nulls index 1)
        size--;

        // Only sift down if heap still has more than one element
        if (size > 1) {
            HeapUtils.siftDownPQ(heap, 1, size, isMinHeap, log);
        }

        if (log != null) {
            log.append("Array after removal: " + Arrays.toString(heap) + "\n");
        }

        return removed;
    }
    
    public void insert(T item, StringBuffer log) {
    	if (size + 1 >= heap.length) {
    			throw new IllegalStateException("PriorityQueue is full. Cannot insert.");
    	}
    	
    	size++;
    	heap[size] = item;
    	
    	HeapUtils.siftUpPQ(heap, size, isMinHeap, log);
    	
    	if(log != null) {
    		log.append("Array after insertion: " + Arrays.toString(heap) + "\n");
    	}
    }
}