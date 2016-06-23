package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transform.Utils;

import model.image.MonoImage;

public class ChainCodeExtractor {

	public static final int[] horizontalMove = {1, 1, 0, -1, -1, -1, 0, 1};
	public static final int[] verticalMove = {0, -1, -1, -1, 0, 1, 1, 1};
		
	public static Map<Position, List<Integer>> extract(MonoImage image) {
		
		final int WALL = 255;
		final int SEEN = 0;
		
		int[][] matrix = image.getCloneMatrix();
				
		List<Integer> chain = new ArrayList<Integer>();
		//List<List<Integer>> chaines = new ArrayList<List<Integer>>();
		Map<Position, List<Integer>> chains = new HashMap<Position, List<Integer>>();
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				if(matrix[i][j] != WALL) continue; // if ! WHITE (si pas un bord)
				
				// sauvegarde de l'origine dans i et j 
				Position origin = new Position(i, j);
				Position position = new Position(i, j);
				Position previous = null;
				
				do {
					
					boolean hasMoved = false;
					
					// recherche du voisin dans l'ordre
					for(int k = 0; k < 8; k++) {
						// next pixel position 
						Position nextPosition = new Position(position.getI() + ChainCodeExtractor.horizontalMove[k], position.getJ() + ChainCodeExtractor.verticalMove[k]); 					
						if(!image.isInBound(nextPosition.getI(), nextPosition.getJ())) continue;
						if(matrix[nextPosition.getI()][nextPosition.getJ()] != WALL) continue;
						if(nextPosition.equals(previous)) continue; 
						
						chain.add(k);
						previous = position;
						position = nextPosition;
						matrix[position.getI()][position.getJ()] = SEEN; // on vient de le parcourir, ce n'est plus un mur  
						hasMoved = true;
						break;
					}
					
					
					if(!hasMoved && !chain.isEmpty()) {
						// rollback 
						int lastK = chain.remove(chain.size() - 1);
						position = new Position(position.getI() - ChainCodeExtractor.horizontalMove[lastK], position.getJ() - ChainCodeExtractor.verticalMove[lastK]); 	
						
						if(chain.isEmpty()) previous = null;
						else {
							int previousK = chain.get(chain.size() - 1);
							previous = new Position(position.getI() - ChainCodeExtractor.horizontalMove[previousK], position.getJ() - ChainCodeExtractor.verticalMove[previousK]);
						}
						
					} else if(!hasMoved) {
						break;
					}
					
				} while(!origin.equals(position));
				
				if(!chain.isEmpty()) {					
					chains.put(origin, chain);
					chain = new ArrayList<Integer>(); 					
				}
								
			}
		}
			
		
		return chains;
		
		/*
		for(Position position : chaines.keySet()) {
			System.out.print(position + ": ");
			for(int i : chaines.get(position)) {
				System.out.print(i);
			}
			System.out.println();
		}
		
		System.out.println("end");
		*/
	}
	
	
	public static List<Position> getPositions(Position start, List<Integer> chainCode) { 
		List<Position> positions = new ArrayList<Position>();
		
		Position actualPosition = start;
		for(int code : chainCode) {
			positions.add(actualPosition);
			actualPosition = new Position(actualPosition.getI() + ChainCodeExtractor.horizontalMove[code], actualPosition.getJ() + ChainCodeExtractor.verticalMove[code]); 
		}
		
		return positions;
	}
	
	public static List<Integer> getDifferenceCodeChain(List<Integer> chainCode) {
		
		List<Integer> differenceChainCode = new ArrayList<Integer>();
		if(chainCode.size() < 2) return differenceChainCode;
		
		
		for(int i = 1; i < chainCode.size() + 1; i++) {
			int previous = chainCode.get((i-1) % chainCode.size());
			int actual = chainCode.get(i % chainCode.size());
			
			int difference = Utils.mod(actual - previous, 8); 
			differenceChainCode.add(difference);
		}
		
		return differenceChainCode;
	}
	
	
	public static class Test {
		public static void main(String[] args) {
			System.out.println(ChainCodeExtractor.getDifferenceCodeChain(Arrays.asList(6, 6, 0, 0, 2, 2, 4, 4))); 
			
		}
	}
		
}
