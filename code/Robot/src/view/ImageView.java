package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import model.ImageModel;

public class ImageView extends JInternalFrame  {
	
	private static final long serialVersionUID = 1383374106891634092L;

	//private Container container;
	private ImageModel imageModel;
	
	public JLabel labelOperationName;
	public JLabel labelexecutionTime;
	
	public ImageView(ImageModel imageModel, String operationName, double executionTime) {
		super();
		
		//this.container = this.getContentPane();
		this.imageModel = imageModel;
		
		// setting layout 
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		//gbc.insets = new Insets(30, 30, 30, 30);
		
		// creating components
		this.labelOperationName = new JLabel(operationName, JLabel.CENTER);
		this.labelexecutionTime = new JLabel("Execution time : " + executionTime + " ms", JLabel.CENTER);
		
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
	
	public ImageModel getImageModel() {
		return this.imageModel;
	}
	
	
}