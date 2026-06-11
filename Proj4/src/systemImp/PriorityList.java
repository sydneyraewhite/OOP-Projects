package systemImp;

import java.util.Comparator;

/**
 * A generic Priority List that maintains elements in sorted order.
 * Supports both natural ordering (Comparable) and custom ordering (Comparator).
 *
 * @param <T> The type of elements stored in the list. Must implement Comparable<T>.
 */
public class PriorityList<T extends Comparable<T>> {
    private final T[] data;           // backing array
    private int size;                 // number of elements in list
    private final boolean useComparator; // whether to use a custom comparator
    private final Comparator<T> comparator; // optional custom comparator

    /**
     * Constructs a PriorityList with a given capacity.
     *
     * @param capacity Maximum number of elements the list can hold (>=1)
     * @param useComparator Whether to use the provided Comparator instead of natural ordering
     * @param comparator The custom comparator (can be null if useComparator is false)
     */
    @SuppressWarnings("unchecked")
    public PriorityList(int capacity, boolean useComparator, Comparator<T> comparator) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be at least 1");
        }
        this.data = (T[]) new Comparable[capacity]; // create array
        this.size = 0;
        this.useComparator = useComparator;
        this.comparator = comparator;
    }

    /** Must be O(1) */
    public int size() {
        return size;
    }

    /** Must be O(1) */
    public boolean isFull() {
        return size == data.length;
    }

    /** Must be O(1) */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Retrieves an element at a specific index.
     * Must be O(1)
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index out of bounds: " + index);
        }
        return data[index];
    }

    /** Helper compare method, uses comparator if specified */
    private int compare(T a, T b) {
        if (useComparator && comparator != null) {
            return comparator.compare(a, b);
        }
        return a.compareTo(b);
    }

    /**
     * Uses binary search to find the correct insertion point.
     * Assumes no duplicates and no null input.
     * Must be O(log n)
     */
    public int binarySearchInsertionPoint(T item) {
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            int cmp = compare(item, data[mid]);
            if (cmp > 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return left;
    }

    /**
     * Inserts an element into the list while maintaining sorted order.
     * Must be O(n) worst-case.
     */
    public void add(T item) {
        if (isFull()) {
            throw new IllegalStateException("Priority List is full");
        }
        int index = binarySearchInsertionPoint(item);

        // Shift elements right to make space
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = item;
        size++;
    }

    /**
     * Uses binary search to find the index of an element.
     * Returns -1 if not found. Must be O(log n)
     */
    public int binarySearchFind(T item) {
        int left = 0, right = size - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (data[mid].equals(item)) {
                return mid;
            }
            int cmp = compare(item, data[mid]);
            if (cmp > 0) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }

    /**
     * Removes an item while keeping the list sorted.
     * Returns true if the element was found and removed.
     * Must be O(n) worst-case.
     */
    public boolean remove(T item) {
        int index = binarySearchFind(item);
        if (index == -1) return false;

        // Shift elements left to fill the gap
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        return true;
    }

    /**
     * Returns a string representation of the priority list.
     * Elements displayed in sorted order: [ x y z ]
     * Must be O(n)
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (int i = 0; i < size; i++) {
            sb.append(data[i]);
            if (i < size - 1) sb.append(" ");
        }
        sb.append(" ]");
        return sb.toString();
    }
}