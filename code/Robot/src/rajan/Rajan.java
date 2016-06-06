package rajan;


import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


public class Rajan {
	
	public static List<Set<Integer>> forward(List<Set<Integer>> sequence) throws RajanException { 
		if(!Utils.isPowerOf2(sequence.size())) throw new RajanException("The length has to be a power of 2");
		
		System.out.println("Number of sequences : " + sequence.size());
		System.out.println("Number of steps : " + Utils.log2(sequence.size()));
		
		int mid = sequence.size() / 2;
				
		for(int i = 0; i < mid; i++) {
			Set<Integer> a = sequence.get(i);
			Set<Integer> b = sequence.get(i+mid);
			
			Set<Integer> intersectionAB = new HashSet<Integer>(a);
			intersectionAB.retainAll(b);
						
			// union 
			a.addAll(b);
			
			// symetric difference 
			b.addAll(a);
			b.removeAll(intersectionAB);
			
		}
		
		
		return null;
		
		//return forwardRecursive(sequences);
	}
		
	private static List<Set<Integer>> forwardRecursive(List<Set<Integer>> sequences) {
		
		if(sequences.size() == 1) return sequences;
		
		List<Set<Integer>> G = new ArrayList<Set<Integer>>();
		List<Set<Integer>> H = new ArrayList<Set<Integer>>();
		
		int mid = sequences.size() / 2;
		for(int i = 0; i < mid; i++) {
			Set<Integer> tmp = (HashSet<Integer>) Utils.union(sequences.get(i), sequences.get(i + mid));
			G.add(tmp);	
		}
		
		for(int i = mid; i < sequences.size(); i++) {
			Set<Integer> tmp = (HashSet<Integer>) Utils.difference(sequences.get(i), sequences.get(i - mid));
			H.add(tmp); 
		}
		
		List<Set<Integer>> nextG = forwardRecursive(G);
		List<Set<Integer>> nextH = forwardRecursive(H);
		
		nextG.addAll(nextH);
		
		return nextG;
	}
	
	public static List<Set<Integer>> reverse(List<Set<Integer>> sequences) throws RajanException { 
		throw new RajanException("The length has to be a power of 2");
	}
}