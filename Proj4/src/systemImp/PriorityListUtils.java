package systemImp;

/**
 * Utility class for working with {@code PriorityList} objects.
 * Provides method to merge two sorted priority lists while ensuring they are sorted
 * using natural ordering (Comparable) rather than a custom comparator.
 */
public class PriorityListUtils {

	// merges two Priority lists
    public static <T extends Comparable<T>> PriorityList<T> mergePriorityLists(PriorityList<T> list1, PriorityList<T> list2) {
    	if (!isNaturallySorted(list1) || !isNaturallySorted(list2)) {
    		throw new IllegalArgumentException("One or both lists are not sorted using Comparable");
    	}
    	
    	PriorityList<T> merged = new PriorityList<>(list1.size() + list2.size(), false, null);
    	
    	int i = 0;
    	int j = 0;
    	
    	while(i < list1.size() && j < list2.size()) {
    		if (list1.get(i).compareTo(list2.get(j)) <= 0) {
    			merged.add(list1.get(i));
    			i++;
    		} else {
    			merged.add(list2.get(j));
    			j++;
    		}
    	}
    	
    	while(i < list1.size()) {
    		merged.add(list1.get(i));
    		i++;
    	}
    	
    	while ( j < list2.size()) {
    		merged.add(list2.get(j));
    		j++;
    	}
    	
    	return merged;
    }
    
    // helper method to make sure the lists are sorted
    private static <T extends Comparable<T>> boolean isNaturallySorted(PriorityList<T> list) {
    	for (int i = 0; i < list.size() - 1; i++) {
    		if (list.get(i).compareTo(list.get(i + 1)) > 0) {
    			return false;
    		}
    	}
    	return true;
    }
}
