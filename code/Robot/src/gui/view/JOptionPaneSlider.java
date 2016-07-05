package gui.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Represents a slider allowing the user to choose a value between a minimum and a maximum.
 * A label is used to show the selected value of the slider. 
 * @author Maxime PINEAU
 */
public class JOptionPaneSlider extends JOptionPane{
	private static final long serialVersionUID = 2138612704444306746L;
	
	private JSlider slider;
	private JLabel labelValueSelected;
	
	/**
	 * Creates a new slider allowing the user to choose a value between 'min' and "max" included.
	 * The selected value of the slider correspond to the median.
	 * @param minValue the minimal possible value of the slider 
	 * @param maxValue the maximal possible value of the slider 
	 */
	public JOptionPaneSlider(int minValue, int maxValue) {
		this(minValue, maxValue, (maxValue + minValue) / 2);
	}
	
	/**
	 * Creates a new slider allowing the user to choose a value between 'min' and "max" included.
	 * @param minValue the minimal possible value of the slider 
	 * @param maxValue the maximal possible value of the slider 
	 * @param initialValue the selected value of the slider 
	 */
	public JOptionPaneSlider(int minValue, int maxValue, int initialValue) {
		super();
		
		this.slider = new JSlider(minValue, maxValue, initialValue);
		this.slider.setMajorTickSpacing(50);
		this.slider.setPaintTicks(true);
		this.slider.setPaintLabels(true);  
		this.slider.setPreferredSize(new Dimension(400, 43));
		this.slider.addChangeListener(new ActionSlider());
		
		this.labelValueSelected = new JLabel();
		this.setSelectedValue(initialValue);
		
		this.setMessage(new Object[] {this.labelValueSelected, this.slider});
		this.setMessageType(JOptionPane.QUESTION_MESSAGE);
		this.setOptionType(JOptionPane.OK_CANCEL_OPTION);
	}
	
	/**
	 * Returns the selected value of the slider
	 * @return the selected value 
	 */
	public int getSelectedValue() {
		return this.slider.getValue();
	}
	
	/**
	 * Sets the selected value displayed by the label to the given value 
	 * @param value the new selected value of the label 
	 */
	public void setSelectedValue(int value) {
		this.labelValueSelected.setText("Select a value:  " + value);
	}
	
	
	/**
	 * Action that changes the displayed label value when the user changes the selected value of the slider.
	 * @author Maxime PINEAU
	 */
	public class ActionSlider implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent event) {
			//if(!slider.getValueIsAdjusting()) 
			setSelectedValue(slider.getValue());
		}
	}
	
	
	/**
	 * Opens a new JOpionPanelSlider in a JDialog and wait the user to confirm his selection.
	 * The initial selected value of the slider is set to be the median. 
	 * @param parentComponent the parent component 
	 * @param title the title of the JDialog window
	 * @param min the minimal possible value of the slider 
	 * @param max the maximal value of the slider 
	 * @return the selected value by the user 
	 * @throws ChoiceCanceledException if the user cancels his operations
	 */
	public static int showConfirmDialog(Component parentComponent, String title, int min, int max) throws ChoiceCanceledException {
		return JOptionPaneSlider.showConfirmDialog(parentComponent, title, min, max, (max + min) / 2);
	}
	
	/**
	 * Opens a new JOpionPanelSlider in a JDialog and wait the user to confirm his selection
	 * @param parentComponent the parent component 
	 * @param title the title of the JDialog window
	 * @param min the minimal possible value of the slider 
	 * @param max the maximal value of the slider 
	 * @param initialValue the initial selected value of the slider 
	 * @return the selected value by the user 
	 * @throws ChoiceCanceledException if the user cancels his operations
	 */
	public static int showConfirmDialog(Component parentComponent, String title, int min, int max, int initialValue) throws ChoiceCanceledException {
		JOptionPaneSlider pane = new JOptionPaneSlider(min, max, initialValue);
		
		JDialog dialog = pane.createDialog(parentComponent, title);
		dialog.setVisible(true);
		
		if (!(pane.getValue() instanceof Integer)) throw new ChoiceCanceledException("choice canceled");
		
		int result = ((Integer) pane.getValue()).intValue();
		
		switch(result){
	        case JOptionPane.YES_OPTION:
	        	return pane.getSelectedValue();
	        default:
	        	throw new ChoiceCanceledException("choice canceled");
		}		
	}
	
	
}
