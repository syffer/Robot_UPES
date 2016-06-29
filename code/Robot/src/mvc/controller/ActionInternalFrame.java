package mvc.controller;

import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import mvc.model.InternalModel;
import mvc.view.InternalView;

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
