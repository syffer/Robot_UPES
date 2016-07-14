package database.dao;

import java.io.Serializable;
import java.util.List;

import database.exceptions.AccessTableException;
import database.exceptions.NoResultsException;
import database.sessions.Session;

public abstract class DAO <T, Key extends Serializable> {
	// http://stackoverflow.com/questions/6401543/what-is-dao-factory-pattern 
	
	protected Session session;
	
	public DAO(Session session) {
		this.session = session;
	}
		
	public abstract void insert(T obj) throws AccessTableException;
	
	public abstract void update(T obj) throws AccessTableException;
	
	public abstract void delete(T obj) throws AccessTableException;
	public abstract void deleteAll() throws AccessTableException;
	
	public abstract T selectById(Key id) throws AccessTableException, NoResultsException;
	public abstract List<T> selectAll() throws AccessTableException;
		
}
