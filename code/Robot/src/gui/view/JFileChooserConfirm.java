package gui.view;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

/**
 * represents a JFileCooser that demands a confirmation if the file given by the user already exists.
 * @author Maxime PINEAU
 */
public class JFileChooserConfirm extends JFileChooser {
	
	private static final long serialVersionUID = 61185538418586982L;
	
	public JFileChooserConfirm() {
		super();
		
	}
	
	@Override
    public void approveSelection() {
		
        File file = getSelectedFile();
        
        if(file.exists() && getDialogType() == SAVE_DIALOG) {
        	
        	String titrePopup = "Existing file";
        	String message = "There is already an existing file with this name, do you want to override it ?";
            int result = JOptionPane.showConfirmDialog(this, message, titrePopup, JOptionPane.YES_NO_CANCEL_OPTION);
            
            switch(result){
                case JOptionPane.YES_OPTION:
                    super.approveSelection();
                    return;
                case JOptionPane.NO_OPTION:
                    return;
                case JOptionPane.CLOSED_OPTION:
                    return;
                case JOptionPane.CANCEL_OPTION:
                    cancelSelection();
                    return;
            }
        }
        
        super.approveSelection();
    } 
	
}
