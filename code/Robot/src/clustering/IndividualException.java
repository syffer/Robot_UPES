package clustering;

/**
 * An exception that occurs on the individual level.
 * @author Maxime PINEAU
 */
public class IndividualException extends ArrayIndexOutOfBoundsException {
	private static final long serialVersionUID = 1L;

	public IndividualException(int index) {
		super(index);
	}
}
