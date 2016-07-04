package image;

/**
 * Represents a position in an image. 
 * @author Maxime PINEAU
 * @see Image
 */
public class Position {
	public int i;
	public int j;
	
	public Position(int i, int j) {
		this.i = i;
		this.j = j;
	}

	/**
	 * Returns the first coordinate, i 
	 * @return the coordinate i 
	 */
	public int getI() {
		return i;
	}

	
	/**
	 * Returns the second coordinate, j 
	 * @return the coordinate j
	 */
	public int getJ() {
		return j;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + i;
		result = prime * result + j;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (i != other.i)
			return false;
		if (j != other.j)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Position [i=" + i + ", j=" + j + "]";
	}
	
	
	
}
