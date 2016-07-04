package transform;

import java.util.HashSet;
import java.util.Set;

/** 
 * Contains usefull math methods 
 * @author Maxime
 */
public class Utils {
	
	/**
	 * Returns the smallest representative of a value modulo n 
	 * @param p the value 
	 * @param n th emodulo 
	 * @return the smallest representative of the value modulo n 
	 */
	public static int mod(int p, int n) {
		return ((p % n) + n) % n;
	}
	
	/**
	 * Returns the log of a given value 
	 * @param base the base of the log 
	 * @param value the value 
	 * @return the log bas "base" of the value 
	 */
	public static double logN(int base, double value) {
		// TODO : verifier base > 0 
		if(base == 10) return Math.log(value);
		return Math.log(value) / Math.log(base); 
	}
	
	/**
	 * Resturns the log base 2 of the given value 
	 * @param value the value 
	 * @return the log bas e2 of the given value 
	 */
	public static double log2(double value) {
		return Math.log(value) / Math.log(2); 
	}
	
	/**
	 * Returns true if the given value is an integer, false otherwise
	 * @param value the value to check 
	 * @return true if the given value is an integer, false otherwise
	 */
	public static boolean isInteger(double value) {
		return value % 1 == 0;
	}
	
	
	/**
	 * Returns true if the given value is a power of 2, false otherwise 
	 * @param value the value to check 
	 * @return true if the given value is a power of 2, false otherwise 
	 */
	public static boolean isPowerOf2(int value) {
		// http://graphics.stanford.edu/~seander/bithacks.html 
		// v && !(v & (v - 1)) 
		return (value != 0) && ((value & (value - 1)) == 0);
	}
	
	/**
	 * Returns the next power of 2 after the given value. 
	 * If the given value is already a power of 2, return the same value 
	 * @param value the value to start from 
	 * @return the next power of 2. If the given value is already a power of 2, return the given value 
	 * @see <a href="https://graphics.stanford.edu/~seander/bithacks.html">https://graphics.stanford.edu/~seander/bithacks.html</a>
	 */
	public static int nextPowerOf2(int value) { 
		value--;
		value |= value >> 1;
		value |= value >> 2;
		value |= value >> 4;
		value |= value >> 8;
		value |= value >> 16;
		value++;
		
		return value;
	}
	
	/**
	 * Returns a random integer between the values min and max included (x e [min ; max])
	 * @param min the minimal possible value 
	 * @param max the maximal possible value 
	 * @return a random integer between min and max included 
	 */
	public static int random(double min, double max) {
		return (int) (min + Math.random() * (max - min + 1));
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
