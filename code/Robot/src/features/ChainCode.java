package features;

import image.Position;

import java.util.ArrayList;
import java.util.List; 

import transform.Utils;

public class ChainCode extends ArrayList<Integer> {
	private static final long serialVersionUID = 1L;
	
	// TODO : à changer en priorité !!! (dans le sens anti-horaire, en partant du bas
	public static final int[] horizontalMove = {1, 1, 0, -1, -1, -1, 0, 1};
	public static final int[] verticalMove = {0, -1, -1, -1, 0, 1, 1, 1};
		
	public ChainCode() {
		
	}

	public List<Position> getPositions(Position start) { 
		List<Position> positions = new ArrayList<Position>();
		
		Position actualPosition = start;
		for(int code : this) {
			positions.add(actualPosition);
			actualPosition = new Position(actualPosition.getI() + ChainCode.horizontalMove[code], actualPosition.getJ() + ChainCode.verticalMove[code]); 
		}
		
		return positions;
	}
	
	
	public ChainCode getDifferenceCodeChain() {
		
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
