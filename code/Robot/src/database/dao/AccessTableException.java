package database.dao;

import database.DatabaseException;


public class AccessTableException extends DatabaseException {
	private static final long serialVersionUID = 1L;

	public AccessTableException(Throwable cause) {
		super(cause);
	}
	
	public AccessTableException(String message) {
		super(message);
	}
	
	public AccessTableException(String message, Throwable cause) {
		super(message, cause);
	}
		
}
