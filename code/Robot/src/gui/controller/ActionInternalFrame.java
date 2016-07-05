package gui.controller;

import gui.model.InternalModel;
import gui.view.InternalView;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


/**
 * Action that changes the selected internal model when the user selects 
 * another internal view on the destok GUI
 * @author Maxime PINEAU
 */
public class ActionInternalFrame implements InternalFrameListener {
	
	private GeneralController controller;
	
	public ActionInternalFrame(GeneralController controller) {
		this.controller = controller;
	}
	
	@Override
	public void internalFrameActivated(InternalFrameEvent event) { 			
		InternalView selectedView = this.controller.getSelectedInternalView();
		InternalModel selectedInternalModel = selectedView.getInternalModel();
		this.controller.setSelectedInternalModel(selectedInternalModel);
	}

	@Override
	public void internalFrameDeactivated(InternalFrameEvent event) { 
		this.controller.setSelectedInternalModel(null);			
	}
	
	@Override
	public void internalFrameOpened(InternalFrameEvent event) {
		
	}
	
	@Override
	public void internalFrameClosed(InternalFrameEvent event) {
		
	}

	@Override
	public void internalFrameClosing(InternalFrameEvent event) {
		
	}
	
	@Override
	public void internalFrameDeiconified(InternalFrameEvent event) {
		
	}

	@Override
	public void internalFrameIconified(InternalFrameEvent event) {
		
	} 
}
