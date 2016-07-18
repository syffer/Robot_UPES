package dataset;

public class DatasetRecorderInitializationException extends DatasetRecorderException {
	private static final long serialVersionUID = 1L;

	public DatasetRecorderInitializationException(Throwable cause) {
		this(cause.getMessage(), cause);
	}
	
	public DatasetRecorderInitializationException(String message) {
		super(message);
	}
	
	public DatasetRecorderInitializationException(String message, Throwable cause) {
		super(message, cause);
	}
}
