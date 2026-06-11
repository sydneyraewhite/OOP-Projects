package systemImp;

/**
 * A class that implements a Queue using your custom Doubly Linked List.
 * All operations must be O(1).
 */
public class Queue<T extends Comparable<T>> {
    private DoublyLinkedList<T> list; // The doubly linked list that backs the queue.

    // Initializes the DOublyLinkedList object backing this queue
    public Queue() {
    	list = new DoublyLinkedList<>();
    }
    
    // Adds an element to the rear of the queue
    public void enqueue(T data) {
    	list.addLast(data);
    }
    
    // Removes and returns the front element of the queue
    public T dequeue() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Queue is empty.");
    	}
    	T data = list.getFirst();
    	list.removeFirst();
    	return data;
    }
    
    // Returns first element without removing it
    public T peek() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Queue is empty.");
    	}
    	return list.getFirst();	
    }
    
    // Checks if the queue is empty
    public boolean isEmpty() {
    	return list.getSize() == 0;
    }
    
    // Returns the number of the element in the queue
    public int size() {
    	return list.getSize();
    }
}

