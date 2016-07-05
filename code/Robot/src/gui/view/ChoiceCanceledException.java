package gui.view;

/**
 * An exception that occurs when the user cancels his choice from a GUI operation. 
 * @see gui.view.View#fileChooserOpen 
 * @see gui.view.View#fileChooserSave 
 * @see gui.view.JOptionPaneSlider#showConfirmDialog(java.awt.Component, String, int, int, int)
 * @author Maxime PINEAU
 */
public class ChoiceCanceledException extends Exception {
	private static final long serialVersionUID = 1L;

	public ChoiceCanceledException(String message) {
		super(message);
	}
}
