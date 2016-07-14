package database.dao;

import java.util.HashMap;
import java.util.Map;

import database.exceptions.AccessTableException;
import database.sessions.Session;

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
	
}
