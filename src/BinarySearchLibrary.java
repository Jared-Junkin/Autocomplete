import java.util.*;

/**
 * Facilitates using fast binary search with
 * a Comparator. The methods firstIndex and lastIndex
 * run in time ceiling(1 + log(n)) where n is size of list.
 * @author ola
 *
 */
public class BinarySearchLibrary {
	
	/**
	 * Return smallest index of target in list using comp
	 * @param list is list of Items being searched
	 * @param target is Item searched for
	 * @param comp how Items are compared for binary search
	 * @return smallest index k such that list.get(k).equals(target)
	 */
	public static <T>
	    int firstIndexSlow(List<T> list, 
	    		           T target, Comparator<T> comp) {
		int index = Collections.binarySearch(list, target,comp);
		
		if (index < 0) return index;
		
		while (0 <= index && comp.compare(list.get(index),target) == 0) {
			index -= 1;
		}
		return index+1;
	}
	
	/**
	 * Return smallest index of target in list using comp. 
	 * Guaranteed to make ceiling(1 + log(list.size())) comparisons
	 * @param list is list of Items being searched
	 * @param target is Item searched for
	 * @param comp how Items are compared for binary search
	 * @return smallest index k such that list.get(k).equals(target),
	 * Return -1 if there is no such object in list.               
	 */
	public static <T>
    	int firstIndex(List<T> list, 
	               	   T target, Comparator<T> comp) {
		if(list.size() == 0) return -1;
		int low = -1;
		int high = list.size()-1;

		while (low + 1 != high) {
			int mid = (low + high)/2;
			int cmp = comp.compare(list.get(mid),target);
			if (cmp < 0)
				low = mid;
			if (cmp >= 0)
				high = mid;
		}
// first index of target found. Idk if this meets log(N). But I personally don't see how the original code doesn't
//The root issue here is that I have no clue what the fuck a comparator actually is. It just hasn't been fucking explained to us.
//I truly do not understand how the source code isn't Log(N) already. It is, I think.
		if(comp.compare(list.get(high),target) == 0) return high;
		return -1;
	}

	 /**                                                                                          
     * Return the index of the last object in parameter                      
     * list -- the first object o such that 
     * comp.compare(o,target) == 0.                         
     *                                                                                           
     * This method should not call comparator.compare() more 
     * than 1+log n times, where n is list.size()                  
     * @param list is the list of objects being searched                                         
     * @param target is the object being searched for                                            
     * @param comp is how comparisons are made                                                   
     * @return index i such that comp.compare(list.get(i),target) == 0                           
     * and there is no index > i such that this is true. Return -1                               
     * if there is no such object in list.                                                       
     */
	public static <T>
	int lastIndex(List<T> list, 
               	  T target, Comparator<T> comp) {
		if(list.size() == 0) return -1;
		int low = 0;
		int high = list.size();

		while (low + 1 != high) {
			int mid = (low + high) / 2;
			int cmp = comp.compare(list.get(mid), target);
			if (cmp <= 0)
				low = mid;
			if (cmp > 0)
				high = mid;
		}
		if(comp.compare(list.get(low), target) == 0) return low;
		return -1;
	}
	
}
/*
The true way to do a binary search is to do with only two if statements, sliding low and high along until they are only one apart.
The reason this way works is because, once a match in general is found, you make low mid again and that gives you a higher match.
And then you do that again and it gives you a higher match.
IF you have [1,2,2,3,4] and target = 2 you will end with low = 2 and mid = 3 and high = 3. That's okay that mid != target.
Low will always == target
My code is less efficient becaue once it hits the target it goes from Log(N) time to O(N) time.
 */