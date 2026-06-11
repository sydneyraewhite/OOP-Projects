package tests;

import static org.junit.Assert.*;
import java.util.Iterator;
import java.util.NoSuchElementException;
import org.junit.Test;
import org.junit.FixMethodOrder;
import org.junit.runners.MethodSorters;

import systemImp.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class StudentTests {

    // TEST 01 — DoublyLinkedList: basic structure (addFirst, addLast, getSize,
    //           getFirst, getLast, toString)
    // Verifies that elements land in the correct positions when added to both
    // ends, that size is tracked accurately, and that toString formats correctly.
    @Test
    public void test01_DLL_BasicStructure() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();

        // Empty list should print as []
        assertEquals("Empty list toString", "[]", list.toString());
        assertEquals("Empty list size", 0, list.getSize());

        // addLast: [10, 20, 30]
        list.addLast(10);
        list.addLast(20);
        list.addLast(30);

        assertEquals("Size after 3 addLast", 3, list.getSize());
        assertEquals("getFirst after addLast", Integer.valueOf(10), list.getFirst());
        assertEquals("getLast after addLast",  Integer.valueOf(30), list.getLast());
        assertEquals("toString after addLast", "[10, 20, 30]", list.toString());

        // addFirst: [5, 10, 20, 30]
        list.addFirst(5);
        assertEquals("Size after addFirst",  4, list.getSize());
        assertEquals("getFirst after addFirst", Integer.valueOf(5), list.getFirst());
        assertEquals("getLast unchanged",       Integer.valueOf(30), list.getLast());
        assertEquals("toString after addFirst", "[5, 10, 20, 30]", list.toString());
    }

    // TEST 02 — DoublyLinkedList: removeFirst and removeLast, including
    //           the single-element edge case and exception on empty list.
    // Ensures the head/tail pointers are updated correctly after removal and
    // that the correct exception is thrown when the list is empty.
    @Test
    public void test02_DLL_RemoveFirstAndLast() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("A");
        list.addLast("B");
        list.addLast("C");  // [A, B, C]

        list.removeFirst();                         // [B, C]
        assertEquals("After removeFirst", "[B, C]", list.toString());
        assertEquals("head after removeFirst", "B", list.getFirst());

        list.removeLast();                          // [B]
        assertEquals("After removeLast", "[B]", list.toString());
        assertEquals("tail after removeLast", "B", list.getLast());

        // Removing the only element should leave an empty list
        list.removeFirst();                         // []
        assertEquals("After removing only element", "[]", list.toString());
        assertEquals("Size should be 0", 0, list.getSize());

        // Both getFirst and getLast must throw on an empty list
        assertThrows(NoSuchElementException.class, list::getFirst);
        assertThrows(NoSuchElementException.class, list::getLast);

        // removeFirst and removeLast must also throw on an empty list
        assertThrows(NoSuchElementException.class, list::removeFirst);
        assertThrows(NoSuchElementException.class, list::removeLast);
    }

    // TEST 03 — DoublyLinkedList: null-element guard on addFirst / addLast.
    // The spec requires a NullPointerException when null is passed;
    // the list must remain unchanged after the failed add.
    @Test
    public void test03_DLL_NullElementRejected() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("X");

        // addFirst(null) must throw NullPointerException
        assertThrows(NullPointerException.class, () -> list.addFirst(null));

        // addLast(null) must throw NullPointerException
        assertThrows(NullPointerException.class, () -> list.addLast(null));

        // List must be unmodified after the failed adds
        assertEquals("List unchanged after null add", "[X]", list.toString());
        assertEquals("Size unchanged after null add", 1, list.getSize());
    }

    // TEST 04 — DoublyLinkedList: iterator — hasNext, next, and traversal order.
    // Confirms that the iterator visits every element in insertion order and
    // that hasNext returns false once the list is exhausted.
    @Test
    public void test04_DLL_IteratorTraversal() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Cat");
        list.addLast("Dog");
        list.addLast("Fish"); // [Cat, Dog, Fish]

        Iterator<String> it = list.iterator();

        // Must visit in order
        assertTrue(it.hasNext());
        assertEquals("Cat",  it.next());
        assertTrue(it.hasNext());
        assertEquals("Dog",  it.next());
        assertTrue(it.hasNext());
        assertEquals("Fish", it.next());

        // Exhausted — hasNext must be false
        assertFalse(it.hasNext());

        // next() on an exhausted iterator must throw NoSuchElementException
        assertThrows(NoSuchElementException.class, it::next);
    }

    // TEST 05 — DoublyLinkedList: iterator remove() — correct exception handling.
    // remove() before next() must throw IllegalStateException.
    // remove() twice in a row (without an intervening next()) must also throw.
    @Test
    public void test05_DLL_IteratorRemoveExceptions() {
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(1);
        list.addLast(2);
        list.addLast(3); // [1, 2, 3]

        Iterator<Integer> it = list.iterator();

        // remove() before any next() → IllegalStateException
        assertThrows(IllegalStateException.class, it::remove);

        it.next(); // advance to 1
        it.remove(); // valid — removes 1

        // remove() again without next() → IllegalStateException
        assertThrows(IllegalStateException.class, it::remove);

        // Continue: remove 2
        it.next();
        it.remove();

        // Remove 3 as well
        it.next();
        it.remove();

        // List must now be empty
        assertEquals("[]", list.toString());
        assertFalse(it.hasNext());
    }

    // TEST 06 — DoublyLinkedList: removeAllInRange — various range scenarios,
    //           null arguments, and start > end exception.
    // Tests that elements are removed positionally (not by compareTo value),
    // that null args throw NullPointerException, and that start > end throws
    // NoSuchElementException.
    @Test
    public void test06_DLL_RemoveAllInRange() {
        DoublyLinkedList<String> list = new DoublyLinkedList<>();
        list.addLast("Ant");
        list.addLast("Bee");
        list.addLast("Cat");
        list.addLast("Dog");
        list.addLast("Eel"); // [Ant, Bee, Cat, Dog, Eel]

        // Remove middle range: Bee → Dog  →  [Ant, Eel]
        list.removeAllInRange("Bee", "Dog");
        assertEquals("[Ant, Eel]", list.toString());
        assertEquals("Ant", list.getFirst());
        assertEquals("Eel", list.getLast());

        // Rebuild: [Ant, Bee, Cat, Dog, Eel]
        DoublyLinkedList<String> list2 = new DoublyLinkedList<>();
        list2.addLast("Ant");
        list2.addLast("Bee");
        list2.addLast("Cat");
        list2.addLast("Dog");
        list2.addLast("Eel");

        // Remove entire list: Ant → Eel  →  []
        list2.removeAllInRange("Ant", "Eel");
        assertEquals("[]", list2.toString());
        assertThrows(NoSuchElementException.class, list2::getFirst);
        assertThrows(NoSuchElementException.class, list2::getLast);

        // Null args must throw NullPointerException
        DoublyLinkedList<String> list3 = new DoublyLinkedList<>();
        list3.addLast("X");
        assertThrows(NullPointerException.class, () -> list3.removeAllInRange(null, "X"));
        assertThrows(NullPointerException.class, () -> list3.removeAllInRange("X", null));

        // start > end (alphabetically) must throw NoSuchElementException
        DoublyLinkedList<String> list4 = new DoublyLinkedList<>();
        list4.addLast("Apple");
        list4.addLast("Banana");
        assertThrows(NoSuchElementException.class,
                () -> list4.removeAllInRange("Banana", "Apple")); // B > A
    }

    // TEST 07 — DoublyLinkedList: insertionSort — correctness of sorted output
    //           and trace format (values separated by spaces, | between sorted
    //           and unsorted, pipe always present).
    // Also verifies the trivial-sort edge case (single element).
    @Test
    public void test07_DLL_InsertionSort() {
        // Trivial case: single element — returns toString, no trace steps
        DoublyLinkedList<Integer> single = new DoublyLinkedList<>();
        single.addLast(42);
        assertEquals("[42]", single.insertionSort());

        // General case: [4, 2, 3, 1]
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>();
        list.addLast(4);
        list.addLast(2);
        list.addLast(3);
        list.addLast(1);

        String trace = list.insertionSort();

        // After sort the list must be fully sorted
        assertEquals("Sorted list", "[1, 2, 3, 4]", list.toString());

        // Trace must contain a | separator on every line
        String[] lines = trace.split("\n");
        for (String line : lines) {
            assertTrue("Every trace line must contain |", line.contains("|"));
        }

        // The final line must show a fully sorted side and an empty unsorted side
        // e.g. "1 2 3 4 | "
        String lastLine = lines[lines.length - 1];
        assertTrue("Last trace line starts with sorted values",
                lastLine.startsWith("1 2 3 4"));
    }

    // TEST 08 — Stack: push, pop, peek, isEmpty, size, and exception on empty.
    // Verifies LIFO ordering: last element pushed must be the first popped.
    // Also confirms IllegalStateException on pop/peek of an empty stack.
    @Test
    public void test08_Stack_Operations() {
        Stack<Integer> stack = new Stack<>();

        assertTrue("New stack must be empty", stack.isEmpty());
        assertEquals("New stack size is 0", 0, stack.size());

        // Push three elements
        stack.push(10);
        stack.push(20);
        stack.push(30); // top → 30

        assertEquals("Size after 3 pushes", 3, stack.size());
        assertFalse("Stack not empty after pushes", stack.isEmpty());

        // peek must return top without removing it
        assertEquals("peek returns top", Integer.valueOf(30), stack.peek());
        assertEquals("Size unchanged after peek", 3, stack.size());

        // pop must return elements in LIFO order
        assertEquals("pop #1", Integer.valueOf(30), stack.pop());
        assertEquals("pop #2", Integer.valueOf(20), stack.pop());
        assertEquals("pop #3", Integer.valueOf(10), stack.pop());

        assertTrue("Stack empty after all pops", stack.isEmpty());

        // pop and peek on empty stack must throw IllegalStateException
        assertThrows(IllegalStateException.class, stack::pop);
        assertThrows(IllegalStateException.class, stack::peek);
    }

    // TEST 09 — Queue: enqueue, dequeue, peek, isEmpty, size, and exception
    //           on empty.
    // Verifies FIFO ordering: first element enqueued must be the first dequeued.
    // Also confirms IllegalStateException on dequeue/peek of an empty queue.
    @Test
    public void test09_Queue_Operations() {
        Queue<String> queue = new Queue<>();

        assertTrue("New queue must be empty", queue.isEmpty());
        assertEquals("New queue size is 0", 0, queue.size());

        // Enqueue three elements
        queue.enqueue("First");
        queue.enqueue("Second");
        queue.enqueue("Third");

        assertEquals("Size after 3 enqueues", 3, queue.size());
        assertFalse("Queue not empty", queue.isEmpty());

        // peek must return front without removing it
        assertEquals("peek returns front", "First", queue.peek());
        assertEquals("Size unchanged after peek", 3, queue.size());

        // dequeue must return elements in FIFO order
        assertEquals("dequeue #1", "First",  queue.dequeue());
        assertEquals("dequeue #2", "Second", queue.dequeue());
        assertEquals("dequeue #3", "Third",  queue.dequeue());

        assertTrue("Queue empty after all dequeues", queue.isEmpty());

        // dequeue and peek on empty queue must throw IllegalStateException
        assertThrows(IllegalStateException.class, queue::dequeue);
        assertThrows(IllegalStateException.class, queue::peek);
    }

    // TEST 10 — Deque: addFirst, addLast, removeFirst, removeLast, peekFirst,
    //           peekLast, isEmpty, size, and exceptions on empty deque.
    // Confirms that both ends behave independently and that the deque can act
    // as both a stack (LIFO) and a queue (FIFO) depending on which end is used.
    @Test
    public void test10_Deque_Operations() {
        Deque<Integer> deque = new Deque<>();

        assertTrue("New deque must be empty", deque.isEmpty());
        assertEquals("New deque size is 0", 0, deque.size());

        // Build: addFirst(1), addLast(2), addFirst(0), addLast(3)
        // Result: [0, 1, 2, 3]
        deque.addFirst(1);
        deque.addLast(2);
        deque.addFirst(0);
        deque.addLast(3);

        assertEquals("Size after 4 adds", 4, deque.size());

        // peekFirst / peekLast must not modify the deque
        assertEquals("peekFirst", Integer.valueOf(0), deque.peekFirst());
        assertEquals("peekLast",  Integer.valueOf(3), deque.peekLast());
        assertEquals("Size unchanged after peeks", 4, deque.size());

        // Remove from front (FIFO behaviour)
        assertEquals("removeFirst #1", Integer.valueOf(0), deque.removeFirst());
        assertEquals("removeFirst #2", Integer.valueOf(1), deque.removeFirst());

        // Remove from back (Stack behaviour on remaining [2, 3])
        assertEquals("removeLast #1", Integer.valueOf(3), deque.removeLast());
        assertEquals("removeLast #2", Integer.valueOf(2), deque.removeLast());

        assertTrue("Deque empty after all removes", deque.isEmpty());

        // All four access/remove methods must throw IllegalStateException when empty
        assertThrows(IllegalStateException.class, deque::removeFirst);
        assertThrows(IllegalStateException.class, deque::removeLast);
        assertThrows(IllegalStateException.class, deque::peekFirst);
        assertThrows(IllegalStateException.class, deque::peekLast);
    }
}