package database;

public class DatabaseException extends Exception { 
	private static final long serialVersionUID = 1L;
		
	public DatabaseException(Throwable cause) {
		this(cause.getMessage(), cause);
	}
	
	public DatabaseException(String message) {
		super(message);
	}
	
	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
		
	
}
