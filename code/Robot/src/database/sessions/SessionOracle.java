package database.sessions;



public class SessionOracle extends Session {

	// jdbc:oracle:thin:@hostname:port:databaseName 
	
	public SessionOracle(String host, int port, String databaseName, String user, String password) throws ConnectionException {
		super("oracle:thin", host, port, databaseName, user, password); 
	}
	
	public SessionOracle() throws ConnectionException {
		this("localhost", 1521, "xe", "E125341Q", "E125341Q");
	}
	
	
	public static class Test {
		public static void main(String[] args) {
			
			try {
				Session session = new SessionOracle("localhost", 1521, "xe", "E125341Q", "E125341Q");
				session.close();
				
				System.out.println("Success");
				
			} catch (ConnectionException connectionException) {
				//System.out.println(connectionException.getMessage());
				connectionException.printStackTrace();
			} 
			
		}
	}
	
}
