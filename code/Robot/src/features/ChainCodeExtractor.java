package features;

import image.MonoImage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import transform.Utils;


public class ChainCodeExtractor {
	
	public static Map<Position, ChainCode> extract(MonoImage image) {
		
		final int WALL = 255;
		final int SEEN = 0;
		
		int[][] matrix = image.getCloneMatrix();
		
		ChainCode chain = new ChainCode();
		Map<Position, ChainCode> chains = new HashMap<Position, ChainCode>();
		
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				if(matrix[i][j] != WALL) continue; // if ! WHITE (si pas un bord)
				
				// sauvegarde de l'origine dans i et j 
				Position origin = new Position(i, j);
				Position position = new Position(i, j);
				Position previous = null;
				
				int lPrec = 6;	// on considère que l'on vient d'en haut 
				
				do {
					
					boolean hasMoved = false;
					
					// recherche du voisin dans l'ordre 
					int depart = Utils.mod(lPrec - 4, 8);
					for(int k = depart; k < depart + 8; k++) { 
						int l = k % 8;
						
						// next pixel position 
						Position nextPosition = new Position(position.getI() + ChainCode.horizontalMove[l], position.getJ() + ChainCode.verticalMove[l]); 					
						if(!image.isInBound(nextPosition.getI(), nextPosition.getJ())) continue;
						if(matrix[nextPosition.getI()][nextPosition.getJ()] != WALL) continue;
						if(nextPosition.equals(previous)) continue; 
						
						chain.add(l);
						previous = position;
						position = nextPosition;
						matrix[position.getI()][position.getJ()] = SEEN; // on vient de le parcourir, ce n'est plus un mur  
						hasMoved = true;
						
						lPrec = l;
						break;
					}
					
					
					if(!hasMoved && !chain.isEmpty()) {
						// rollback 
						int lastK = chain.remove(chain.size() - 1);
						position = new Position(position.getI() - ChainCode.horizontalMove[lastK], position.getJ() - ChainCode.verticalMove[lastK]); 	
						
						if(chain.isEmpty()) {
							previous = null;
							lPrec = 6;
						}
						else {
							int previousK = chain.get(chain.size() - 1);
							previous = new Position(position.getI() - ChainCode.horizontalMove[previousK], position.getJ() - ChainCode.verticalMove[previousK]);
						
							lPrec = chain.get(chain.size() - 1);
						}
						
					} else if(!hasMoved) {
						break;
					}
					
				} while(!origin.equals(position));
				
				if(!chain.isEmpty()) {					
					chains.put(origin, chain);
					chain = new ChainCode(); 					
				}
								
			}
		}
			
		
		return chains;
	}
	
	
	public static List<Position> getPositions(Position start, List<Integer> chainCode) { 
		List<Position> positions = new ArrayList<Position>();
		
		Position actualPosition = start;
		for(int code : chainCode) {
			positions.add(actualPosition);
			actualPosition = new Position(actualPosition.getI() + ChainCode.horizontalMove[code], actualPosition.getJ() + ChainCode.verticalMove[code]); 
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
