package database.dao;

import java.util.HashMap;
import java.util.Map;

import database.exceptions.AccessTableException;
import database.exceptions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class FactoryDAO {
	
	private static Map<Session, AnnotatedObjectDAO> singletons = new HashMap<Session, AnnotatedObjectDAO>();
	
	public static AnnotatedObjectDAO getAnnotatedObjectDAO(Session session) throws AccessTableException {
		
		AnnotatedObjectDAO annotatedObjectDAO = FactoryDAO.singletons.get(session);
		
		if(!FactoryDAO.singletons.containsKey(session)) {
			annotatedObjectDAO = new AnnotatedObjectDAO(session);
			FactoryDAO.singletons.put(session, annotatedObjectDAO);
		}
		
		return annotatedObjectDAO;
	}
	
	
	public static class Test {
		public static void main(String[] args) {
			
			Session session;
			try {
				session = new SessionOracle();
				AnnotatedObjectDAO dao1 = FactoryDAO.getAnnotatedObjectDAO(session);
				AnnotatedObjectDAO dao2 = FactoryDAO.getAnnotatedObjectDAO(session);
				System.out.println(dao1 == dao2);
				
			} catch (ConnectionException | AccessTableException e) {
				e.printStackTrace();
			}
			
		}
	}
}
