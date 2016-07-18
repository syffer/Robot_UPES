package dataset;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import database.bean.AnnotatedObjectBean;
import database.dao.AccessTableException;
import database.dao.AnnotatedObjectDAO;
import database.dao.FactoryDAO;
import database.sessions.Session;

public class DatasetRecorder {
	
	private SAXParser parser;
	private AnnotatedObjectDAO annotatedObjectDAO;
	
	public DatasetRecorder(Session session) throws DatasetRecorderInitializationException {
		
		try {
			SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
			this.parser = saxParserFactory.newSAXParser();
		
			this.annotatedObjectDAO = FactoryDAO.getAnnotatedObjectDAO(session);
			
		} catch(AccessTableException | ParserConfigurationException | SAXException e) {
			throw new DatasetRecorderInitializationException(e);
		}
	}
	
	public void record(String pathToFolder, String... tags) throws DatasetRecorderNotADirectoryException, AccessTableException {
		
		File folder = new File(pathToFolder);
		if(!folder.isDirectory()) throw new DatasetRecorderNotADirectoryException("The given path doesn't correspond to a directory : '" + pathToFolder + "'");
		
		// Create a new handler
	    ExtractHandler handler = new ExtractHandler(tags);
		
		for(File file : folder.listFiles()) {
						
			try {
				
				System.out.println("# Processing : " + file.getName());
				System.out.println("  parsing the file");
				
				this.parser.parse(file, handler);
							
				
				List<XmlObject> xmlObjects = handler.getXmlObjects();
				for(XmlObject xmlObject : xmlObjects) {
										
					AnnotatedObjectBean annotatedObject = xmlObject.toAnnotatedObject();
					
					System.out.println("  new object " + annotatedObject.getTag());
								
					this.annotatedObjectDAO.insert(annotatedObject); 
				}
								
			} catch(SAXException saxE) {
				System.out.println("  - error in the file : " + saxE.getMessage());
			} catch(IOException e) {
				System.out.println("  - io exception, passing the file");
			} catch (IllegalArgumentException e) {
				System.out.println("  - impossible to save this object");
			}
			
		}
		
	}
	
}
