package gui.controller;

import java.awt.event.ActionEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

/**
 * Action that allows the user to close all the internal views on the destok of the GUI 
 * @see gui.view.InternalView 
 * @author Maxime PINEAU
 */
public class ActionCloseAll extends AbstractAction implements Observer {
	private static final long serialVersionUID = 1L;

	private GeneralController controller;
	
	public ActionCloseAll(GeneralController controller) {
		super("Close All");
		
		this.controller = controller;
		this.controller.model.addObserver(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		this.controller.view.closeAllImageView();
	}

	@Override
	public void update(Observable observable, Object params) {
		this.setEnabled(this.controller.model.hasModelSelected());
	} 
}