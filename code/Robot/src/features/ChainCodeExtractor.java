package features;

import image.MonoImage;
import image.Position;

import java.util.HashMap;
import java.util.Map;

import transform.Utils;


/**
 * 
 * The chain code allow to represent a form by a chain code, i.e. a list of codes. 
 * Those codes are integers, and represent the direction of a displacement. 
 * Starting from a point in the edge of the form, we travel the form using those codes. 
 * 
 * The codes can be represented as the following star : 
 * <pre> 
 *  3  2  1 	
 *   \ | /	
 * 4 - x - 0	
 *   / | \	
 *  5  6  7	
 * </pre>
 * Where "x" is the actual point. To go to the left pixel from the "x" pixel, the code "0" will be used. 
 * The start is traveled in anti-clockwise.  
 * 
 * In order to travel all the pixel border : 
 * <ul>
 * 	<li> we start from the most left upper pixel of the form border (there can be other border pixels above, but not at the left of the starting point)</li>
 * 	<li> we change the starting point of the star to the opposite code of the last displacement (6 by default). The opposite code of 4 is 0, of 2 is 6, ...</li>
 * </ul> 
 * 
 * @see features.ChainCode 
 * @author Maxime PINEAU
 */
public class ChainCodeExtractor {
	
	private final int edge;
	private final int seen;
	
	public ChainCodeExtractor() {
		this.edge = 255;
		this.seen = 2;
	}
	
	/**
	 * Extracts the chain codes from a monochromatic image.
	 * 
	 * @param image the monochromatic image from where the chain codes must be extracted 
	 * @return a list of the extracted chain codes
	 */
	public Map<Position, ChainCode> extract(MonoImage image) {
				
		int[][] matrix = image.getCloneMatrix();
		
		ChainCode chain = new ChainCode();
		Map<Position, ChainCode> chains = new HashMap<Position, ChainCode>();
		
		// travel the image 
		for(int i = 0; i < image.getWidth(); i++) {
			for(int j = 0; j < image.getHeight(); j++) {
				
				// skip if it's not an edge/wall
				if(!this.isPixelAnEdge(matrix[i][j])) continue; 
				
				// save the starting position of the possible chain code 
				Position origin = new Position(i, j);
				Position position = new Position(i, j); 
				
				// save the previous position in order to be able to rollback (if it doesn't come back to the origin point)
				Position previous = null;
				int codePrec = 6;	// allow to change the starting point of the star  
									// we consider that the last displacement was from the center to the bottom (6)
				
				// travel the chain code 
				do {
					
					boolean hasMoved = false;
					
					// travel the star starting from the opposite direction of the last displacement/code
					int depart = Utils.mod(codePrec - 4, 8); 	
					for(int k = depart; k < depart + 8; k++) { 
						int code = k % 8; 	// stay in the star tables (index between [0;8])
						
						// calculate the next position 
						Position nextPosition = new Position(position.getI() + ChainCode.horizontalMove[code], position.getJ() + ChainCode.verticalMove[code]); 					
						if(!image.isInBound(nextPosition)) continue;	// stay in the image 
						if(!this.isPixelAnEdge(matrix[nextPosition.getI()][nextPosition.getJ()])) continue;  
						if(nextPosition.equals(previous)) continue; 	// don't go back (as the start position is never seen) 
						
						chain.add(code);
						previous = position;
						position = nextPosition;
						
						// we just have visited the next position, it's not an edge anymore  
						matrix[position.getI()][position.getJ()] = this.seen; 
						
						hasMoved = true;
						codePrec = code;
						
						break;
					}
					
					// rollback 
					if(!hasMoved && !chain.isEmpty()) {
						
						// reset the actual position to the previous position if possible  
						int lastCode = chain.remove(chain.size() - 1);
						position = new Position(position.getI() - ChainCode.horizontalMove[lastCode], position.getJ() - ChainCode.verticalMove[lastCode]); 	
						
						// reset the previous position to the previous "previous position" if possible  
						if(chain.isEmpty()) {  // we are at the origin position  
							codePrec = 6;
							previous = null;
						}
						else {
							codePrec = chain.get(chain.size() - 1);
							previous = new Position(position.getI() - ChainCode.horizontalMove[codePrec], position.getJ() - ChainCode.verticalMove[codePrec]);
						}
						
					} else if(!hasMoved) {
						break;
					}
					
				} while(!origin.equals(position));
				
				// the chain is empty when we couldn't move from the origin/starting position 
				if(!chain.isEmpty()) {					
					chains.put(origin, chain);
					chain = new ChainCode(); 
				}
								
			}
		}
			
		
		return chains;
	}
	
	private boolean isPixelAnEdge(int pixelValue) {
		return pixelValue == this.edge;
	}
	
	
}
