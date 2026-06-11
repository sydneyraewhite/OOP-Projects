package systemImp;

import java.util.Arrays;

/**
 * Utility class providing search and sorting algorithms using generic types.
 * <p>
 * This class includes:
 * - Recursive Bidirectional Linear Sort
 * - Non-Recursive Bidirectional Bubble Sort
 * - Recursive Bidirectional Selection Sort
 * </p>
 *
 * 
 */
public class SearchAndSortUtil {
	
	// checks both ends of the array at the same time
	public static <T extends Comparable<T>> int bidirectionalLinearSearch(T[] arr, T key, int left, int right, StringBuilder log) {
		if (left > right) {
			return -1;
		}
		log.append("Checking index (left): ").append(left).append(", value: ").append(arr[left]).append("\n");
		
		if (arr[left].compareTo(key) == 0) {
			return left;
		}
		
		if (left != right) {
			log.append("Checking index (right): ").append(right).append(", value: ").append(arr[right]).append("\n");
			
			if (arr[right].compareTo(key) == 0) {
				return right;
			}
		}
		
		return bidirectionalLinearSearch(arr, key, left + 1, right - 1, log);
	}
	
	// performs bubble sort on an algorithm using generic types
	public static <T extends Comparable<T>> String bidirectionalBubbleSort(T[] arr) {

	    StringBuilder log = new StringBuilder();

	    int left = 0;
	    int right = arr.length - 1;
	    boolean swapped = true;

	    while (left < right && swapped) {

	        swapped = false;

	        // left → right
	        for (int i = left; i < right; i++) {

	            if (arr[i].compareTo(arr[i + 1]) > 0) {

	                T a = arr[i];
	                T b = arr[i + 1];

	                arr[i] = b;
	                arr[i + 1] = a;

	                log.append("Swapped ")
	                   .append(b)
	                   .append(" and ")
	                   .append(a)
	                   .append(": ")
	                   .append(Arrays.toString(arr))
	                   .append("\n");

	                swapped = true;
	            }
	        }

	        right--;

	        if (!swapped) break;

	        swapped = false;

	        // right → left
	        for (int i = right; i > left; i--) {

	            if (arr[i].compareTo(arr[i - 1]) < 0) {

	                T first = arr[i - 1];
	                T second = arr[i];

	                // swap
	                arr[i - 1] = second;
	                arr[i] = first;

	                log.append("Swapped ")
	                   .append(first)
	                   .append(" and ")
	                   .append(second)
	                   .append(": ")
	                   .append(Arrays.toString(arr))
	                   .append("\n");

	                swapped = true;
	            }
	        }

	        left++;
	    }

	    return log.toString();
	}
	
	// performs selection sort on an algorithm using generic types
	public static <T extends Comparable<T>> void recursiveBidirectionalSelectionSort(
	        T[] arr, int left, int right, StringBuilder log) {

	    if (left >= right) return;

	    int minIndex = left;
	    int maxIndex = left;

	    // Find min and max
	    for (int i = left; i <= right; i++) {
	        if (arr[i].compareTo(arr[minIndex]) < 0) minIndex = i;
	        if (arr[i].compareTo(arr[maxIndex]) > 0) maxIndex = i;
	    }

	    // If max is at left and min is not left, update maxIndex
	    if (maxIndex == left && minIndex != left) {
	        maxIndex = minIndex;
	    }

	    // Swap min with left
	    if (minIndex != left) {
	        T temp = arr[left];
	        arr[left] = arr[minIndex];
	        arr[minIndex] = temp;

	        log.append("Swapped ")
	           .append(temp)
	           .append(" and ")
	           .append(arr[left])
	           .append(": ")
	           .append(Arrays.toString(arr))
	           .append("\n");
	    }

	    // Swap max with right
	    if (maxIndex != right) {
	        T temp = arr[right];
	        arr[right] = arr[maxIndex];
	        arr[maxIndex] = temp;

	        log.append("Swapped ")
	           .append(temp)
	           .append(" and ")
	           .append(arr[right])
	           .append(": ")
	           .append(Arrays.toString(arr))
	           .append("\n");
	    }

	    // Recurse on the inner subarray
	    recursiveBidirectionalSelectionSort(arr, left + 1, right - 1, log);
	}
}
