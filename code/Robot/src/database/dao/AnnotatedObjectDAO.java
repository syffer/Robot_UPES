package database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.bean.AnnotatedObject;
import database.exceptions.AccessTableException;
import database.exceptions.ConnectionException;
import database.exceptions.NoResultsException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class AnnotatedObjectDAO extends DAO<AnnotatedObject, Integer> {

	private static final String insertSQL = "insert " + 
											"into AnnotatedObjects(tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight) " +
											"values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	
	private static final String updateSQL = "update AnnotatedObjects " + 
											"set tag = ?, area = ?, perimeter = ?, compactness = ?, circularity = ?, curvature = ?, " +
											"	 bendingEnergy = ?, width = ?, height = ?, ratioWidthHeight = ?" +
											"where id = ?";
	
	private static final String deleteByIdSQL = "delete " + 
												"from AnnotatedObjects " + 
												"where id = ?";
	
	private static final String deleteAllSQL = "delete " +
			   								   "from AnnotatedObjects";
	
	private static final String selectByIdSQL = "select * " + 
												"from AnnotatedObjects " + 
												"where id = ?";
		
	private static final String selectByTagSQL = "select * " +
												 "from AnnotatedObjects " +
												 "where tag = ?";

	private static final String selectAllSQL = "select * from AnnotatedObjects";
	
	private PreparedStatement insertStatement;
	private PreparedStatement updateStatement;
	private PreparedStatement deleteByIDStatement;
	private PreparedStatement deleteAllStatement;
	private PreparedStatement selectByIdStatement;
	private PreparedStatement selectByTagStatement;
	private PreparedStatement selectAllStatement;
	
	public AnnotatedObjectDAO(Session session) throws AccessTableException {
		super(session);
				
		try {
			this.insertStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.insertSQL); 
			this.updateStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.updateSQL); 
			
			this.deleteByIDStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.deleteByIdSQL); 
			this.deleteAllStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.deleteAllSQL);
			
			this.selectByIdStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectByIdSQL); 
			this.selectByTagStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectByTagSQL); 
			this.selectAllStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectAllSQL);
						
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}		
	}
	



	@Override
	public void insert(AnnotatedObject annotatedObject) throws AccessTableException {
		try {
			this.insertStatement.setString(1, annotatedObject.tag.name());
			
			this.insertStatement.setDouble(2, annotatedObject.area);
			this.insertStatement.setDouble(3, annotatedObject.perimeter);

			this.insertStatement.setDouble(4, annotatedObject.compactness);
			this.insertStatement.setDouble(5, annotatedObject.circularity);
			this.insertStatement.setInt(6, annotatedObject.curvature);
			this.insertStatement.setDouble(7, annotatedObject.bendingEnergy);
			
			this.insertStatement.setInt(8, annotatedObject.width);
			this.insertStatement.setInt(9, annotatedObject.height);
			this.insertStatement.setDouble(10, annotatedObject.ratioWidthHeight);
			
			this.insertStatement.execute();
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public void update(AnnotatedObject annotatedObject) throws AccessTableException {
		try {
			this.updateStatement.setInt(11, annotatedObject.id);
			
			this.updateStatement.setString(1, annotatedObject.tag.name());
			
			this.updateStatement.setDouble(2, annotatedObject.area);
			this.updateStatement.setDouble(3, annotatedObject.perimeter);

			this.updateStatement.setDouble(4, annotatedObject.compactness);
			this.updateStatement.setDouble(5, annotatedObject.circularity);
			this.updateStatement.setInt(6, annotatedObject.curvature);
			this.updateStatement.setDouble(7, annotatedObject.bendingEnergy);
			
			this.updateStatement.setInt(8, annotatedObject.width);
			this.updateStatement.setInt(9, annotatedObject.height);
			this.updateStatement.setDouble(10, annotatedObject.ratioWidthHeight);
			
			this.updateStatement.execute();
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public void delete(AnnotatedObject annotatedObject) throws AccessTableException {
		try {
			this.deleteByIDStatement.setInt(1, annotatedObject.id);
			this.deleteByIDStatement.execute();
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public AnnotatedObject selectById(Integer id) throws AccessTableException, NoResultsException {
		try {
			this.selectByIdStatement.setInt(1, id);
			
			boolean hasResultSet = this.selectByIdStatement.execute(); 	
			if(!hasResultSet) throw new NoResultsException();
			// true if the first result is a ResultSet object; false if the first result is an update count or there is no result
			
			ResultSet result = this.selectByIdStatement.getResultSet();
			List<AnnotatedObject> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects.get(0);
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}

	
	public List<AnnotatedObject> selectByTag(AnnotatedObject.Tag tag) throws AccessTableException {
		try {
			boolean hasResultSet = this.selectByTagStatement.execute();
			if(!hasResultSet) return new ArrayList<AnnotatedObject>();
			
			ResultSet result = this.selectByTagStatement.getResultSet();
			List<AnnotatedObject> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects;
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}


	@Override
	public List<AnnotatedObject> selectAll() throws AccessTableException {
		try {
			boolean hasResultSet = this.selectAllStatement.execute();
			if(!hasResultSet) return new ArrayList<AnnotatedObject>();
			
			ResultSet result = this.selectAllStatement.getResultSet();
			List<AnnotatedObject> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects;
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}

	
	@Override
	public void deleteAll() throws AccessTableException {
		try {
			this.deleteAllStatement.execute();
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}
	
	
	protected List<AnnotatedObject> getObjetcs(ResultSet result) throws SQLException {
		List<AnnotatedObject> objects = new ArrayList<AnnotatedObject>();
		
		while(result.next()) {
			
			int id = result.getInt("id");
			String tagString = result.getString("tag");
			AnnotatedObject.Tag tag = AnnotatedObject.Tag.valueOf(tagString);
			double area = result.getDouble("area"); 
			double perimeter = result.getDouble("perimeter");
			double compactness = result.getDouble("compactness");
			double circularity = result.getDouble("circularity");
			int curvature = result.getInt("curvature"); 
			double bendingEnergy = result.getDouble("bendingEnergy");
			int width = result.getInt("width");
			int height = result.getInt("height");
			double ratioWidthHeight = result.getDouble("ratioWidthHeight"); 
			
			AnnotatedObject annotatedObject = new AnnotatedObject(id, tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight);;
			objects.add(annotatedObject);
		}
		
		return objects;
	}
	
	
	
	public static class Test {
		public static void main(String[] args) {
			
			try {
				
				Session session = new SessionOracle();
				
				AnnotatedObjectDAO objectDAO = FactoryDAO.getAnnotatedObjectDAO(session);
				
				for(int i = 0; i < 5; i++) {
					AnnotatedObject annotatedObject = new AnnotatedObject(AnnotatedObject.Tag.ROCK, i, i, i, i, i, i, i, i, i);
					objectDAO.insert(annotatedObject);
				}
				
				
				List<AnnotatedObject> objects = objectDAO.selectAll();
				for(AnnotatedObject ob : objects) {
					System.out.println(ob.id);
				}
				
				
				objectDAO.deleteAll();
				
				session.close();
				
				System.out.println("SUCCESS");
				
			} catch(ConnectionException co) {
				System.out.println(co.getMessage());
				co.printStackTrace();
			} catch (AccessTableException ac) {
				System.out.println(ac.getMessage());
				ac.printStackTrace();
			}
			
		}
	}
	
}