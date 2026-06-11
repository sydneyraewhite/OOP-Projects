package systemImp;

/**
 * A class that implements a Deque (double-ended queue) using a custom Doubly Linked List.
 * All operations (insertion and removal from both ends) must be O(1).
 */
public class Deque<T extends Comparable<T>> {
    private DoublyLinkedList<T> list; // The doubly linked list backing the deque.

    // Initializes an empty deque
    public Deque() {
    	list = new DoublyLinkedList<>();
    }
    
    // Adds an element to the front if the deque
    public void addFirst(T data) {
    	list.addFirst(data);
    }
    
    // Adds an element to the back of the deque
    public void addLast(T data) {
    	list.addLast(data);
    }
    
    // Removes and returns the element from the front of the deque
    public T removeFirst() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Deque is empty.");
    	}
    	T data = list.getFirst();
    	list.removeFirst();
    	return data;
    }
    
    // Removes and returns the element from the back of the deque
    public T removeLast() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Deque is empty.");
    	}
    	T data = list.getLast();
    	list.removeLast();
    	return data;
    }
    
    // Returns the element at the front without removing it
    public T peekFirst() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Deque is empty.");
    	}
    	return list.getFirst();
    }
    
    // Returns the element at the back without removing it
    public T peekLast() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Deque is empty.");
    	}
    	return list.getLast();
    }
    
    // Checks if the deque is empty
    public boolean isEmpty() {
    	return list.getSize() == 0;
    }
    
    // Returns the number of elements in the deque
    public int size() {
    	return list.getSize();
    }
}