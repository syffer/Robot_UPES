package mvc.view;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class JOptionPaneSlider extends JOptionPane{
	private static final long serialVersionUID = 2138612704444306746L;
	
	private JSlider slider;
	private JLabel labelValueSelected;
	
	public JOptionPaneSlider(int minValue, int maxValue) {
		this(minValue, maxValue, (maxValue + minValue) / 2);
	}
	
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
	
	public int getSelectedValue() {
		return this.slider.getValue();
	}
	
	public void setSelectedValue(int value) {
		this.labelValueSelected.setText("Select a value:  " + value);
	}
	
	
	public class ActionSlider implements ChangeListener {
		@Override
		public void stateChanged(ChangeEvent event) {
			//if(!slider.getValueIsAdjusting()) 
			setSelectedValue(slider.getValue());
		}
	}
	
	
	public static int showConfirmDialog(Component parentComponent, String title, int min, int max) throws ChoiceCanceledException {
		return JOptionPaneSlider.showConfirmDialog(parentComponent, title, min, max, (max + min) / 2);
	}
	
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
