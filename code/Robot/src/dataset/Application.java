package dataset;

import database.dao.AccessTableException;
import database.sessions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;


public class Application {
	public static void main(String[] args) {
				
		try {
			
			Session session = new SessionOracle("localhost", 1521, "xe", "E125341Q", "E125341Q");
			
			DatasetRecorder datasetRecorder = new DatasetRecorder(session);
			
			datasetRecorder.record("../dataset/annotations/", "car", "tree", "rock", "person", "wall");
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (DatasetRecorderInitializationException e) {
			e.printStackTrace();
		} catch (DatasetRecorderNotADirectoryException e) {
			e.printStackTrace();
		} catch (AccessTableException e) {
			e.printStackTrace();
		}
		
	    
	}
}
