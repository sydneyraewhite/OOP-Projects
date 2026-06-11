package systemImp;

/**
 * A class that implements a Stack using your custom Doubly Linked List.  All operations must be O(1).
 */
public class Stack<T extends Comparable<T>> {
    private DoublyLinkedList<T> list; // The doubly linked list that will back the stack.

    // Initializes the DoublyLinkedList object backing this stack 
    public Stack() {
    	list = new DoublyLinkedList<>();
    }
    
    // Pushes an element onto the top of the stack
    public void push (T data) {
    	list.addFirst(data);
    }
    
    // Removes and returns the element at the top of the stack
    public T pop() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Stack is empty.");
    	}
    	T data = list.getFirst();
    	list.removeFirst();
    	return data;
    }
    
    // Peeks at the top element of the stack without removing it
    public T peek() {
    	if (isEmpty()) {
    		throw new IllegalStateException("Stack is empty.");
    	}
    	return list.getFirst();
    }
    
    // Checks if the stack is empty
    public boolean isEmpty() {
    	return list.getSize() == 0;
    }
    
    // Returns the number of elements in the stack
    public int size() {
    	return list.getSize();
    }
 
}

