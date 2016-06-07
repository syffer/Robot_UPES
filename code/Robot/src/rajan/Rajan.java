package rajan;


import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Rajan {
	
	public static <E> List<Set<E>> forward(List<Set<E>> sequence) throws RajanException { 
		//if(!Utils.isPowerOf2(sequence.size())) throw new RajanException("The length has to be a power of 2");
		
		if(!Utils.isPowerOf2(sequence.size())) {
			int toPad = Utils.nextPowerOf2(sequence.size()) - sequence.size();
			for(int i = 0; i < toPad; i++) sequence.add(new HashSet<E>());
		}
		
		System.out.println("Number of sequences : " + sequence.size());
		System.out.println("Number of steps : " + Utils.log2(sequence.size()));	
				
		forwardRecursive(sequence, 0, sequence.size() - 1);
				
		return sequence;
	}
		
	private static <E> void forwardRecursive(List<Set<E>> sequence, int indexBegin, int indexEnd) {
		if(indexBegin >= indexEnd) return;
		
		int indexMid = (indexEnd + indexBegin + 1) / 2;
		int step = indexMid - indexBegin;
		
		for(int i = indexBegin; i < indexMid; i++) {			
			Set<E> a = sequence.get(i);
			Set<E> b = sequence.get(i+step);
			
			Set<E> intersectionAB = new HashSet<E>(a);
			intersectionAB.retainAll(b);
			
			// union 
			a.addAll(b);
			
			// symetric difference 
			b.addAll(a);
			b.removeAll(intersectionAB);
			
			//System.out.println("- " + sequence.size() + " " + sequence.get(i).size() + " " + sequence.get(i+step).size());
		}
		
		//System.out.println(sequence.get(0));
		
		forwardRecursive(sequence, indexBegin, indexMid - 1);
		forwardRecursive(sequence, indexMid, indexEnd);		
	}
	
	public static List<Set<Integer>> reverse(List<Set<Integer>> sequences) throws RajanException { 
		throw new RajanException("The length has to be a power of 2");
	}
}
