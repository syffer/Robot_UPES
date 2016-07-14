package database.exceptions;

public class ConnectionException extends DatabaseException {
	private static final long serialVersionUID = 1L;
	
	public ConnectionException(Throwable cause) {
		super(cause);
	}
	
	public ConnectionException(String message) {
		super(message);
	}
	
	public ConnectionException(String message, Throwable cause) {
		super(message, cause);
	}
	
}
