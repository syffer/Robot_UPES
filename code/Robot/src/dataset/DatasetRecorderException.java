package dataset;

public abstract class DatasetRecorderException extends Exception { 
	private static final long serialVersionUID = 1L;
	
	public DatasetRecorderException(Throwable cause) {
		this(cause.getMessage(), cause);
	}
	
	public DatasetRecorderException(String message) {
		super(message);
	}
	
	public DatasetRecorderException(String message, Throwable cause) {
		super(message, cause);
	}

}
