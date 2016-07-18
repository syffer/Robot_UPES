package features;

import geometry.Position;
import geometry.Utils;

import java.util.ArrayList;
import java.util.List; 


/**
 * Represents a Freeman chain code. 
 * A chain code is used to represent a form, it is created by travelling the edge of the form. 
 * 
 * @see <a href="https://tel.archives-ouvertes.fr/tel-00496290/document">https://tel.archives-ouvertes.fr/tel-00496290/document</a>
 * @author Maxime PINEAU
 */
public class ChainCode extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
	
	
	public static final int[] horizontalMove = {1, 1, 0, -1, -1, -1, 0, 1};
	public static final int[] verticalMove = {0, -1, -1, -1, 0, 1, 1, 1};
	
	
	/**
	 * Returns the positions of the chain code given a starting point. 
	 * @param start the starting point of the chain 
	 * @return the positions of the chain code 
	 */
	public List<Position> getPositions(Position start) { 
		List<Position> positions = new ArrayList<Position>();
		
		Position actualPosition = start;
		for(int code : this) {
			positions.add(actualPosition);
			actualPosition = new Position(actualPosition.getI() + ChainCode.horizontalMove[code], actualPosition.getJ() + ChainCode.verticalMove[code]); 
		}
		
		return positions;
	}
	
	
	/**
	 * Returns the corresponding difference chain code.  
	 * @return the difference chain code 
	 */
	public ChainCode getDifferenceChainCode() {
		
		ChainCode differenceChainCode = new ChainCode();
		if(this.size() < 2) return differenceChainCode;
		
		for(int i = 1; i < this.size() + 1; i++) {
			int previous = this.get((i-1) % this.size());
			int actual = this.get(i % this.size());
			
			int difference = Utils.mod(actual - previous, 8); 
			differenceChainCode.add(difference);
		}
		
		return differenceChainCode;
	}
	
}
