package dataset;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class Application {
	public static void main(String[] args) {
		
		String pathToFile = "../dataset/annotations/p1010736.xml";
		
		try {
			// Create a new factory that will create the parser.
		    SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
	
		    // Create the XMLReader to be used to parse the document.
		    SAXParser parser = saxParserFactory.newSAXParser();
		    XmlObjectExtractHandler handler = new XmlObjectExtractHandler("car");
		    		    
		    parser.parse(pathToFile, handler);
		    		    
		    System.out.println("Success");
		    
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			
		}
	    
	}
}
