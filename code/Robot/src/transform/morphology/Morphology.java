package transform.morphology;

import transform.Transformation;

public abstract class Morphology extends Transformation {

	// http://cdn.intechopen.com/pdfs-wm/11306.pdf 
	// http://stackoverflow.com/questions/1472768/implementing-erosion-dilation-in-c-c 
	
	protected static final int[][] defaultStructuredElement = {{1, 1}, {1, 1}, {1, 1}};	
	
	protected int[][] structuringElement;
	
	public Morphology(int[][] structuringElement) {
		this.structuringElement = structuringElement;
		
		// have to check the structuring element first ... (contains only 0 and 1 ?)
	}
	
	public Morphology() {
		this(Morphology.defaultStructuredElement);
	}
		
}
