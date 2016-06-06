package rajan;

import java.util.HashSet;
import java.util.Set;

public class Utils {
	
	public static double log2(double value) {
		return Math.log(value) / Math.log(2); 
	}
	
	public static boolean isInteger(double value) {
		return value % 1 == 0;
	}
	
	
	public static boolean isPowerOf2(int value) {
		// http://graphics.stanford.edu/~seander/bithacks.html 
		// v && !(v & (v - 1)) 
		return (value != 0) && ((value & (value - 1)) == 0);
	}
	
	public static int nextPowerOf2(int value) { 
		// http://graphics.stanford.edu/~seander/bithacks.html 
		value--;
		value |= value >> 1;
		value |= value >> 2;
		value |= value >> 4;
		value |= value >> 8;
		value |= value >> 16;
		value++;
		
		return value;
	}
	
	
	public static <E> Set<E> union(Set<E> a, Set<E> b) {
		Set<E> result = new HashSet<E>(a);
		result.addAll(b);
		return result;
	}
	
	public static <E> Set<E> difference(Set<E> a, Set<E> b) { 
		// a U b
		Set<E> result = new HashSet<E>(a);
		result.addAll(b);
		
		Set<E> intersection = new HashSet<>(a);
		intersection.retainAll(b);
		
		result.removeAll(intersection);
				
		return result;
	}
	
}
