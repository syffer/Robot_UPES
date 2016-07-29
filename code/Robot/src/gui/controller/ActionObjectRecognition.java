package gui.controller;

import features.ObjectRecognition;
import features.ObjectRecognitionException;
import features.PositionnedObject;
import features.Tag;
import gui.model.FeatureExtractionModel;
import gui.model.ObjectRecognitionModel;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import database.bean.AnnotatedObjectBean;
import database.dao.AccessTableException;
import database.dao.AnnotatedObjectDAO;
import database.dao.FactoryDAO;
import database.sessions.ConnectionException;
import database.sessions.Session;
import database.sessions.SessionOracle;

public class ActionObjectRecognition  extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionObjectRecognition(GeneralController controller) {
		super("Object Recognition");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) { 
		FeatureExtractionModel featureExtractionModel = (FeatureExtractionModel) this.controller.getSelectedInternalModel();
		List<PositionnedObject> someObjects = featureExtractionModel.getExtractedObjects();
		MonoImage image = featureExtractionModel.getPreviousImage();
		
		try {
			
			Session session = new SessionOracle();	// can take time 
			AnnotatedObjectDAO dao = FactoryDAO.getAnnotatedObjectDAO(session);			
			List<AnnotatedObjectBean> others = dao.selectAllOrdered();
			
			long startTime = System.currentTimeMillis();
			Map<Tag, Collection<PositionnedObject>> map = ObjectRecognition.annotateObjects(someObjects, others);
			long endTime = System.currentTimeMillis();
			
			ObjectRecognitionModel model = new ObjectRecognitionModel(image, featureExtractionModel.getOriginalImage(), map, endTime - startTime);
			this.controller.addInternalModel(new ObjectRecognitionController(model));
			
		} catch (ConnectionException e) {
			e.printStackTrace();
		} catch (AccessTableException access) {
			access.printStackTrace();
		} catch (ObjectRecognitionException e) {
			e.printStackTrace();
		}
		
		
		
		
	}

	@Override
	public void update(Observable observable, Object params) { 
		this.setEnabled(this.controller.hasInternalModelSelected() && this.controller.getSelectedInternalModel() instanceof FeatureExtractionModel);
	} 

}
