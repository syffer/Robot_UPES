package database.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.bean.AnnotatedObjectBean;
import database.sessions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class AnnotatedObjectDAO extends DAO<AnnotatedObjectBean, Integer> {

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
	
	
	public AnnotatedObjectDAO(Session session) {
		super(session);	
	}
	



	@Override
	public void insert(AnnotatedObjectBean annotatedObject) throws AccessTableException {
		try {
			PreparedStatement insertStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.insertSQL); 
			
			insertStatement.setString(1, annotatedObject.tag.toString());
			
			insertStatement.setDouble(2, annotatedObject.getArea());
			insertStatement.setDouble(3, annotatedObject.getPerimeter());

			insertStatement.setDouble(4, annotatedObject.getCompactness());
			insertStatement.setDouble(5, annotatedObject.getCircularity());
			insertStatement.setDouble(6, annotatedObject.getCurvature());
			insertStatement.setDouble(7, annotatedObject.getBendingEnergy());
			
			insertStatement.setInt(8, annotatedObject.getWidth());
			insertStatement.setInt(9, annotatedObject.getHeight());
			insertStatement.setDouble(10, annotatedObject.getRatioWidthHeight());
			
			insertStatement.execute();
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public void update(AnnotatedObjectBean annotatedObject) throws AccessTableException {
		try {
			PreparedStatement updateStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.updateSQL); 
			
			updateStatement.setInt(11, annotatedObject.id);
			
			updateStatement.setString(1, annotatedObject.tag.toString());
			
			updateStatement.setDouble(2, annotatedObject.getArea());
			updateStatement.setDouble(3, annotatedObject.getPerimeter());

			updateStatement.setDouble(4, annotatedObject.getCompactness());
			updateStatement.setDouble(5, annotatedObject.getCircularity());
			updateStatement.setDouble(6, annotatedObject.getCurvature());
			updateStatement.setDouble(7, annotatedObject.getBendingEnergy());
			
			updateStatement.setInt(8, annotatedObject.getWidth());
			updateStatement.setInt(9, annotatedObject.getHeight());
			updateStatement.setDouble(10, annotatedObject.getRatioWidthHeight());
			
			updateStatement.execute();
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public void delete(AnnotatedObjectBean annotatedObject) throws AccessTableException {
		try {
			PreparedStatement deleteByIDStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.deleteByIdSQL); 
			deleteByIDStatement.setInt(1, annotatedObject.id);
			deleteByIDStatement.execute();
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}




	@Override
	public AnnotatedObjectBean selectById(Integer id) throws AccessTableException, NoResultsException {
		try {
			PreparedStatement selectByIdStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectByIdSQL); 
			
			selectByIdStatement.setInt(1, id);
			
			boolean hasResultSet = selectByIdStatement.execute(); 	
			if(!hasResultSet) throw new NoResultsException();
			// true if the first result is a ResultSet object; false if the first result is an update count or there is no result
			
			ResultSet result = selectByIdStatement.getResultSet();
			List<AnnotatedObjectBean> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects.get(0);
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}

	
	public List<AnnotatedObjectBean> selectByTag(String tag) throws AccessTableException {
		try {
			PreparedStatement selectByTagStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectByTagSQL); 
			
			boolean hasResultSet = selectByTagStatement.execute();
			if(!hasResultSet) return new ArrayList<AnnotatedObjectBean>();
			
			ResultSet result = selectByTagStatement.getResultSet();
			List<AnnotatedObjectBean> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects;
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}


	@Override
	public List<AnnotatedObjectBean> selectAll() throws AccessTableException {
		try {
			PreparedStatement selectAllStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.selectAllSQL);
			
			boolean hasResultSet = selectAllStatement.execute();
			if(!hasResultSet) return new ArrayList<AnnotatedObjectBean>();
			
			ResultSet result = selectAllStatement.getResultSet();
			List<AnnotatedObjectBean> annotatedObjects = this.getObjetcs(result);
			
			return annotatedObjects;
			
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}

	
	@Override
	public void deleteAll() throws AccessTableException {
		try {
			PreparedStatement deleteAllStatement = this.session.getConnection().prepareStatement(AnnotatedObjectDAO.deleteAllSQL);
			deleteAllStatement.execute();
		} catch(SQLException sqlException) {
			throw new AccessTableException(sqlException);
		}
	}
	
	
	protected List<AnnotatedObjectBean> getObjetcs(ResultSet result) throws SQLException {
		List<AnnotatedObjectBean> objects = new ArrayList<AnnotatedObjectBean>();
		
		while(result.next()) {
			
			int id = result.getInt("id");
			String tag = result.getString("tag");
			double area = result.getDouble("area"); 
			double perimeter = result.getDouble("perimeter");
			double compactness = result.getDouble("compactness");
			double circularity = result.getDouble("circularity");
			int curvature = result.getInt("curvature"); 
			double bendingEnergy = result.getDouble("bendingEnergy");
			int width = result.getInt("width");
			int height = result.getInt("height");
			double ratioWidthHeight = result.getDouble("ratioWidthHeight"); 
			
			AnnotatedObjectBean annotatedObject = new AnnotatedObjectBean(id, tag, area, perimeter, compactness, circularity, curvature, bendingEnergy, width, height, ratioWidthHeight);;
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
					AnnotatedObjectBean annotatedObject = new AnnotatedObjectBean("rock", i, i, i, i, i, i, i, i, i);
					objectDAO.insert(annotatedObject);
				}
				
				
				List<AnnotatedObjectBean> objects = objectDAO.selectAll();
				for(AnnotatedObjectBean ob : objects) {
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
