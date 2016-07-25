package features;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import database.bean.AnnotatedObjectBean;
import database.dao.AccessTableException;
import database.dao.AnnotatedObjectDAO;
import database.dao.FactoryDAO;
import database.sessions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class ObjectRecognition {
	
	public static Map<Tag, Collection<PositionnedObject>> annotateObjects(List<PositionnedObject> objects, List<AnnotatedObjectBean> tagged) throws ObjectRecognitionException {
		
		Map<Tag, Collection<PositionnedObject>> map = new HashMap<Tag, Collection<PositionnedObject>>();
		
		for(PositionnedObject some : objects) {
			
			AnnotatedObjectBean nearest = ObjectRecognition.getNearestAnnotated(some, tagged);
			Tag tag = nearest.getTag();
			some.setTag(tag);
			
			if(!map.containsKey(tag)) map.put(tag, new ArrayList<PositionnedObject>());
			Collection<PositionnedObject> collection = map.get(tag);
			collection.add(some);
		}
		
		return map;
	}
	
	public static AnnotatedObjectBean getNearestAnnotated(SomeObject object, List<AnnotatedObjectBean> others) throws ObjectRecognitionException {
		if(others.isEmpty()) throw new ObjectRecognitionException("the list of annotated object is empty");
		
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
				
				try {
					
					getNearestAnnotated(some, others);
					
				} catch (ObjectRecognitionException e) {
					e.printStackTrace();
				}
				
				
			} catch (ConnectionException | AccessTableException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
