package gui.view;

import gui.model.ImageModel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;


/**
 * Represents an internal view that contains : 
 * <ul>
 * 	<li>an image</li>
 *  <li>a title</li>
 * 	<li>the time of execution of the operation that had generated the image</li>
 * </ul>
 * @author Maxime PINEAU
 */
public class ImageView extends InternalView  {
	private static final long serialVersionUID = 1383374106891634092L;

	//private Container container;
	
	private JLabel labelOperationName;
	private JLabel labelexecutionTime;
	
	public ImageView(ImageModel imageModel) {
		super(imageModel);
		
		//this.container = this.getContentPane();
		
		// setting layout 
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.insets = new Insets(30, 30, 30, 30);
		
		// creating components
		this.labelOperationName = new JLabel(imageModel.getOperationName(), JLabel.CENTER);
		this.labelexecutionTime = new JLabel("Execution time : " + imageModel.getExecutionTime() + " s", JLabel.CENTER);
		
		// adding compponents 
		this.add(this.labelOperationName, gbc);
		this.add(this.labelexecutionTime, gbc);
		
		// resizing image 
		BufferedImage bufferedImage = imageModel.getBufferedImage();
		int newHeigth = 256;
		int newWidth = bufferedImage.getWidth() * 256 / bufferedImage.getHeight();
		Image tmp = bufferedImage.getScaledInstance(newWidth, newHeigth, Image.SCALE_SMOOTH);
		
		this.add(new JLabel(new ImageIcon(tmp)), gbc);
		
		
		this.setClosable(true);
		this.setResizable(false);
		this.setMaximizable(true);
		this.setIconifiable(true); 
		
		this.pack();
		//this.setSize(258, 300);
		this.setVisible(true);
				
	}
}
