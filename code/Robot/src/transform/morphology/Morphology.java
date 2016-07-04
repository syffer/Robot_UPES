package transform.morphology;

import transform.Transformation;

/**
 * 
 * 
 * @see <a href="http://cdn.intechopen.com/pdfs-wm/11306.pdf">http://cdn.intechopen.com/pdfs-wm/11306.pdf</a>
 * @see <a href="http://stackoverflow.com/questions/1472768/implementing-erosion-dilation-in-c-c">http://stackoverflow.com/questions/1472768/implementing-erosion-dilation-in-c-c</a>
 * @author Maxime PINEAU
 */
public abstract class Morphology extends Transformation {
	
	public static final int[][] defaultStructuredElement = {{1, 1}, {1, 1}, {1, 1}};	
	
	protected int[][] structuringElement;
	
	public Morphology(int[][] structuringElement) {
		this.structuringElement = structuringElement;
		
		// TODO have to check the structuring element first ... (contains only 0 and 1 ?)
	}
	
	public Morphology() {
		this(Morphology.defaultStructuredElement);
	}
		
}
