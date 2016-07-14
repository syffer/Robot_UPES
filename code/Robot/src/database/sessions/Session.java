package database.sessions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import database.exceptions.ConnectionException;

public abstract class Session {
	
	protected Connection connection;
	
	protected String url;
	protected String user;
	protected String password;
	
	public Session(String driver, String host, int port, String databaseName, String user, String password) throws ConnectionException {
		this("jdbc:" + driver + ":@" + host + ":" + port + ":" + databaseName, user, password);
	}
	
	public Session(String url, String user, String password) throws ConnectionException { 

		this.url = url;
		this.user = user;
		this.password = password;
		
		try {
			// http://stackoverflow.com/questions/5484227/jdbc-class-forname-vs-drivermanager-registerdriver 
			// since JDBC 4, no need of either 
			// - Class.forName(driver)			// register the JDBC driver by calling the next one 
			// - DriverManager.registerDriver() // only use it if we are writting driver itself  
			
			// jdbc:myDriver:myDatabase 		
			
			this.connection = DriverManager.getConnection(url, user, password);
			this.connection.setAutoCommit(true);
			
		} catch(SQLException sqlException) {
			throw new ConnectionException(sqlException);
		} 
		
	}
	
	public void close() throws ConnectionException {
		try {
			this.connection.close();
		} catch(SQLException sqlException) {
			throw new ConnectionException(sqlException);
		}
		
	}
	
	public Connection getConnection() {
		return connection;
	}
		
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((password == null) ? 0 : password.hashCode());
		result = prime * result + ((url == null) ? 0 : url.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Session other = (Session) obj;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (url == null) {
			if (other.url != null)
				return false;
		} else if (!url.equals(other.url))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}
	
	
	
}
