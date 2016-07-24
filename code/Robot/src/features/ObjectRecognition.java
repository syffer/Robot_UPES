package features;

import java.util.Arrays;
import java.util.List;

import database.bean.AnnotatedObjectBean;
import database.dao.AccessTableException;
import database.dao.AnnotatedObjectDAO;
import database.dao.FactoryDAO;
import database.sessions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class ObjectRecognition {
	
	public static void annotateObjects(List<SomeObject> objects) {
		
	}
	
	public static AnnotatedObjectBean getNearestAnnotated(SomeObject object, List<AnnotatedObjectBean> others) {
		if(others.isEmpty()) return null;
		
		int lower = 0;
		int higher = others.size();
		int middle = (lower + higher) / 2;
		
		int difference = 0;
		
		while(lower < higher) {
			middle = (lower + higher) / 2;
			AnnotatedObjectBean other = others.get(middle);
			difference = object.compareTo(other);
			
			if(difference > 0) lower = middle + 1;
			else if(difference < 0) higher = middle; 
			else return other;
		}
		
		for(int i : Arrays.asList(middle-1, middle+1)) {
			if(i < 0 || i >= others.size()) continue;
			
			AnnotatedObjectBean other = others.get(i);
			int diff = object.compareTo(other);
			diff = Math.abs(diff);
			
			if(diff < difference){
				difference = diff;
				middle = i;
			}
		}
				
		return others.get(middle);		
	}
	
	
	
	public static class Test {
		public static void main(String[] args) {
			
			try {
				Session session = new SessionOracle();
				AnnotatedObjectDAO dao = FactoryDAO.getAnnotatedObjectDAO(session);
				
				List<AnnotatedObjectBean> others = dao.selectAllOrdered();
				
				
				Feature feature = new Feature(55, 44, 32, 0.64, 20.66, 2.3, 35, 42, 77);
				
				SomeObject some = new SomeObject(feature) {
				};

				System.out.println(feature);
				
				getNearestAnnotated(some, others);
								
				for(AnnotatedObjectBean ano : others) {
					//System.out.println(ano.getFeature());
				}
				
			} catch (ConnectionException | AccessTableException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
