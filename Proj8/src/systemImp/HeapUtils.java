package systemImp;

public class HeapUtils {
	
	public HeapUtils() {}
	
	private static <T extends Comparable<T>> boolean hasPriority(T a, T b, boolean isMinHeap) {
		int cmp = a.compareTo(b);
		return isMinHeap ? cmp < 0 : cmp > 0;
	}
	
	private static <T> void swap(T[] arr, int i, int j, StringBuffer log, String prefix){
		if (log != null) {
			log.append(prefix + "Swapped index " + i + " (" + arr[i] + ") with index " + j + " (" + arr[j] +")\n");
		}
		
		T tmp = arr[i];
		arr[i] = arr[j];
		arr[j] = tmp;
	}
	
	private static <T extends Comparable <T>> void siftDown(T[] arr, int i, int size, boolean isMinHeap, StringBuffer log, String prefix) {
		while (true) {
			int left = 2 * i;
			int right = 2 * i + 1;
			int target = i;
			
			if (left <= size && arr[left] != null && hasPriority(arr[left], arr[target], isMinHeap)) {
				target = left;
			}
			
			if(right <= size && arr[right] != null && hasPriority(arr[right], arr[target], isMinHeap)) {
				target = right;
			}
			
			if (target == i) break;
			
			swap(arr, i, target, log, prefix);
			i = target;
		}
	}
	
	private static <T extends Comparable<T>> void siftUp(T[] arr, int i, boolean isMinHeap, StringBuffer log, String prefix) {
		while (i > 1) {
			int parent = i / 2;
			if (hasPriority(arr[i], arr[parent], isMinHeap)) {
				swap(arr, i, parent, log, prefix);
				i = parent;
			} else {
				break;
			}
		}
	}
	
	public static <T extends Comparable<T>> void heapify(T[] arr, boolean isMinHeap, StringBuffer log) {
		int size = arr.length - 1;
		String prefix = "Heapify swap: ";
			
		for (int i = size / 2; i >= 1; i--) {
			siftDown(arr, i, size, isMinHeap, log, prefix);
		}
	}
		
	public static <T extends Comparable<T>> void heapSort(T[] arr, StringBuffer log) {
		int size = arr.length - 1;
		heapify(arr,false, null);
		
		String prefix = "HeapSort swap: ";
		for (int end = size; end > 1; end--) {
			swap(arr, 1, end, log, prefix);
			siftDown(arr, 1, end - 1, false, log, prefix);	
		}		
	}
	
	static <T extends Comparable<T>> void siftDownPQ(T[] arr, int i, int size, boolean isMinHeap, StringBuffer log) {
		siftDown(arr, i, size, isMinHeap, log, "");
	}
	
	static <T extends Comparable<T>> void siftUpPQ(T[] arr, int i, boolean isMinHeap, StringBuffer log) {
		siftUp(arr, i, isMinHeap, log, "");
	}
  
}

