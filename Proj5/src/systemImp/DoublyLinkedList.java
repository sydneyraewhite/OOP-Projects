package systemImp;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A doubly linked list implementation for storing elements.
 * This list supports basic operations like adding and removing elements at both ends.
 * @class Inner class description.
 * @param <E> The type of elements in this list, which must extend Comparable.
 */
public class DoublyLinkedList<E extends Comparable<E>> implements Iterable<E> {
    private Node head; //points to the first node
    private Node tail; //points to the last node
    private int size;  //number of elements in the list

    /**
     * Node represents a single element in the doubly linked list.
     * It contains references to the data and the next and previous nodes in the list.
     */
    private class Node {
        E data;
        Node next, prev;

        /**
         * Constructor for creating a new node.
         * 
         * @param data The data to be stored in the node.
         */
        Node(E data) {
            this.data = data;
            this.next = null;
            this.prev = null;
        }
    }
    
    // Constructor for creating an empty doubly linked list
    public DoublyLinkedList() {
    	head = null;
    	tail = null;
    	size = 0;
    }
    
    // Returns the size of the linked list
    public int getSize() {
    	return size;
    }
    
    // Return the first element of the list
    public E getFirst() {
    	if (size == 0) {
    		throw new NoSuchElementException("List is empty!");
    	}
    	return head.data;
    }
    
    // Returns the last element of the list
    public E getLast() {
    	if (size == 0) {
    		throw new NoSuchElementException("List is empty!");
    	}
    	return tail.data;
    }
    
    // Inserts the given element at the front of the list
    public void addFirst(E e) {
    	if (e == null) {
    		throw new NullPointerException("Element cannot be null");
    	}
    	Node newNode = new Node(e);
    	if (size == 0) {
    		head = tail = newNode;
    	} else {
    		newNode.next = head;
    		head.prev = newNode;
    		head = newNode;
    	}
    	size++;
    }
    
    // Appends the given element to the end of the list
    public void addLast(E e) {
    	if (e == null) {
    		throw new NullPointerException("Element cannot be null");
    	}
    	Node newNode = new Node(e);
    	if (size == 0) {
    		head = tail = newNode;
    	} else {
    		tail.next = newNode;
    		newNode.prev = tail;
    		tail = newNode;
    	}
    	size++;
    }
    
    // Removes and returns the first element (O(1))
    public void removeFirst() {
    	if (size == 0) {
    		throw new NoSuchElementException("List is empty!");
    	}
    	
    	if (size == 1) {
    		head = tail = null;
    	} else {
    		Node oldHead = head;
    		head = head.next;
    		head.prev = null;
    		oldHead.next = null;
    	}
    	size--;
    }
    
    // Removes the first element of the list (O(1))
    public void removeLast() {
    	if (size == 0) {
    		throw new NoSuchElementException("List is empty!");
    	}
    	
    	if (size == 1) {
    		tail = head = null;
    	} else {
    		Node oldTail = tail;
    		tail = tail.prev;
    		tail.next = null;
    		oldTail.prev = null;
    	}
    	size--;
    }
    
    // Helper method
    private void removeNode(Node node) {
    	if (node == head && node == tail) {
    		head = tail = null;
    	} else if (node == head) {
    		head = head.next;
    		head.prev = null;
    	} else if (node == tail) {
    		tail = tail.prev;
    		tail.next = null;
    	} else {
    		node.prev.next = node.next;
    		node.next.prev = node.prev;
    	}
    	size--;
    }
    
    // Returns string representation of the list
    @Override
    public String toString() {
    	StringBuilder sb = new StringBuilder("[");
    	Node current = head;
    	while (current != null) {
    		sb.append(current.data);
    		if (current.next != null) sb.append(", ");
    		current = current.next;
    	}
    	sb.append("]");
    	return sb.toString();
    }
    
    // Removes all elements in the list that between start and end values
    public void removeAllInRange(E start, E end) {
    	if (start == null || end == null) {
    		throw new NullPointerException("Range bounds cannot be null.");
    	}
    	
    	if (start.compareTo(end) > 0) {
    		throw new NoSuchElementException("No elements exists within the range.");
    	}
    	
    	Node startNode = null;
    	Node current = head;
    	
    	while(current != null) {
    		if (current.data.equals(start)) {
    			startNode = current;
    			break;
    		}
    		current = current.next;
    	}
    	if (startNode == null) 
    		throw new NoSuchElementException("No elements found in the specified range.");
    	
    	current = startNode;
    	while (current != null) {
    		Node next = current.next;
    		boolean isEnd = current.data.equals(end);
    		removeNode(current);
    		if(isEnd) break;
    		current = next;
    	}
    }
    
    // Builds one trace line showing [sorted | unsorted]
    private String buildTrace(Node sortedEnd, Node unsortedStart) {
    	StringBuilder sb = new StringBuilder();
    	
    	Node current = head;
    	while(current != null && current != unsortedStart) {
    		sb.append(current.data);
    		if (current.next != null && current.next != unsortedStart) {
    			sb.append(" ");
    		}
    		current = current.next;
    	}
    	
    	sb.append(" | ");
    	Node u = unsortedStart;
    	while (u != null) {
    		sb.append(u.data);
    		if (u.next != null) sb.append(" ");
    		u = u.next;
    	}
    	
    	sb.append("\n");
    	return sb.toString();
    }
    
    // Sorts the doubly linked list using the insertion sort algorithm
    public String insertionSort() {
    	if (size <= 1) {
    		return toString();
    	}
    	
    	StringBuilder trace = new StringBuilder();
    	Node sortedEnd = null;
    	Node unsortedStart = head;
    	
    	while (unsortedStart != null) {
    		Node key = unsortedStart;
    		unsortedStart = unsortedStart.next;
    		
    		if(sortedEnd == null) {
    			sortedEnd = key;
    		} else {
    			Node insertBefore = null;
    			Node search = head;
    			
    			while (search != unsortedStart) {
    				if (search.data.compareTo(key.data) > 0) {
    					insertBefore = search;
    					break;
    				}
    				search = search.next;
    			}
    			if (insertBefore == null) {
    				sortedEnd = key;
    			} else {
    				sortedEnd.next = key.next;
    				if (key.next != null) {
    					key.next.prev = sortedEnd;
    				}
    				
    				// Null out key's stale pointers before reinserting 
    				key.prev = null;
    				key.next = null;
    				
    				key.next = insertBefore;
    				key.prev = insertBefore.prev;
    				
    				if (insertBefore.prev != null) {
    					insertBefore.prev.next = key;
    				} else {
    					head = key;
    				}
    				insertBefore.prev = key;
    			}
    		}
    		trace.append(buildTrace(sortedEnd, unsortedStart));
    	}
    	
    	Node t = head;
    	while(t.next != null) t = t.next;
    	tail = t;
    	
    	return trace.toString();
    }
    
    // Returns an iterator for the doubly linked list
    @Override
    public Iterator<E> iterator() {
    	return new Iterator<E>() {
    		private Node current = head;
    		private Node lastReturned = null;
    		
    		@Override
    		public boolean hasNext() {
    			return current != null;
    		}
    		
    		@Override
    		public E next() {
    			if (!hasNext()) {
    				throw new NoSuchElementException("No more elements.");
    			}
    			lastReturned = current; 
    			current = current.next;
    			return lastReturned.data;
    		}
    		
    		@Override
    		public void remove() {
    			if (lastReturned == null) {
    				throw new IllegalStateException("next() must be called before remove().");
    			}
    			removeNode(lastReturned);
    			lastReturned = null;
    		}
    	};
    }
}
