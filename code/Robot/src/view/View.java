package view;

import java.awt.BorderLayout;
import java.awt.Point;
import java.beans.PropertyVetoException;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JToolBar;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;


public class View extends JFrame {
	
	private static final long serialVersionUID = 2118299654730994785L;

	//private Container container;
	
	// menu 
	public JMenuItem menuLoad;
	public JMenuItem menuSaveAs;
	public JMenuItem menuCloseAll;
	
	public JMenuItem menuSobel;
	public JMenuItem menuLaplacian;
	public JMenuItem menuCanny;
	public JMenuItem menuClap;
	
	public JMenuItem menuWeightedAverage;
	public JMenuItem menuMeanFilter;
	public JMenuItem menuMedianFilter;
	public JMenuItem menuGaussianFilter;
	public JMenuItem menuCellularAutomataI;
	public JMenuItem menuCellularAutomataII;
	
	public JMenuItem menuMorphologyErosion;
	public JMenuItem menuMorphologyDilation;
	
	// toolbar 
	public JToolBar toolBar;
	public JButton buttonGreyScale;
	public JButton buttonSTRT;
	public JButton buttonISTRT;
	public JButton buttonMonochrome;
	public JButton buttonStatisticAnalysis;
	
	// internals windows 
	public JDesktopPane desktop;
	
	// open and save file 
	public JFileChooser fileChooserOpen;
	public JFileChooserConfirm fileChooserSave;
	
	
	public View() {
		super("Robot");
		
		//this.container = this.getContentPane();
		
		this.initialiseMenuBar();
		this.initialiseFileChooser();
		this.initialiseToolBar();
		this.initialiseDesktop();
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the window when you clic en "x" 
		this.pack();
		this.setSize(600, 600);
		this.setVisible(true);
	}
	
	
	private void initialiseMenuBar() {
		JMenuBar menuBar = new JMenuBar();
		
		JMenu menuFile = new JMenu("File");
		this.menuLoad = new JMenuItem("Load");
		this.menuSaveAs = new JMenuItem("Save as ...");
		this.menuCloseAll = new JMenuItem("Close All");
		menuFile.add(this.menuLoad);
		menuFile.add(this.menuSaveAs);
		menuFile.add(this.menuCloseAll);
		
		// submenu of noise removal 
		JMenu menuCellularAutomata = new JMenu("Cellular Automata");
		this.menuCellularAutomataI = new JMenuItem("Algorithm I");
		this.menuCellularAutomataII = new JMenuItem("Algorithm II");
		//menuCellularAutomata.add(this.menuCellularAutomataI);
		menuCellularAutomata.add(this.menuCellularAutomataII);
		
		JMenu menuNoiseRemoval = new JMenu("Noise Removal");
		this.menuWeightedAverage = new JMenuItem("Weighted Average");
		this.menuMeanFilter = new JMenuItem("Mean Filter");
		this.menuMedianFilter = new JMenuItem("Median Filter");
		this.menuGaussianFilter = new JMenuItem("Gaussian Filter");		
		menuNoiseRemoval.add(this.menuWeightedAverage);
		//menuNoiseRemoval.add(this.menuMeanFilter);
		//menuNoiseRemoval.add(this.menuMedianFilter);
		menuNoiseRemoval.add(menuCellularAutomata);
		
		JMenu menuEdgeDetection = new JMenu("Edge Detection");
		this.menuSobel = new JMenuItem("Sobel");
		this.menuLaplacian = new JMenuItem("Laplacian");
		this.menuCanny = new JMenuItem("Canny");
		this.menuClap = new JMenuItem("CLAP");
		menuEdgeDetection.add(this.menuSobel);
		menuEdgeDetection.add(this.menuLaplacian);
		menuEdgeDetection.add(this.menuCanny);
		menuEdgeDetection.add(this.menuClap);
		
		JMenu menuMorphology = new JMenu("Morphology");
		this.menuMorphologyErosion = new JMenuItem("Erosion");
		this.menuMorphologyDilation = new JMenuItem("Dilation");
		menuMorphology.add(this.menuMorphologyErosion);
		menuMorphology.add(this.menuMorphologyDilation);
		
		menuBar.add(menuFile);
		menuBar.add(menuNoiseRemoval);
		menuBar.add(menuEdgeDetection);
		menuBar.add(menuMorphology);
		
		this.setJMenuBar(menuBar);
	}
	
	private void initialiseFileChooser() { 
		this.fileChooserOpen = new JFileChooser();
		this.fileChooserSave = new JFileChooserConfirm();
		
		// get only image file 
		FileFilter filter = new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes());
		
		this.fileChooserOpen.setFileFilter(filter);
		this.fileChooserSave.setFileFilter(filter);
	}
	
	private void initialiseToolBar() {
		this.toolBar = new JToolBar("Toolbar");
		this.toolBar.setFloatable(false);
		
		this.buttonGreyScale = new JButton("Grey Scale");
		this.buttonSTRT = new JButton("STRT");
		this.buttonISTRT = new JButton("ISTRT");
		this.buttonMonochrome = new JButton("Monochrome");
		this.buttonStatisticAnalysis = new JButton("Statistic Analysis");
		
		this.toolBar.add(this.buttonGreyScale);
		//this.toolBar.add(this.buttonSTRT);
		//this.toolBar.add(this.buttonISTRT);
		this.toolBar.add(this.buttonMonochrome);
		this.toolBar.add(this.buttonStatisticAnalysis);
		
		this.add(this.toolBar, BorderLayout.PAGE_START);
	}
	
	private void initialiseDesktop() {
		this.desktop = new JDesktopPane();
		this.add(desktop);
	}
	
	
	public ImageView getSelectedImageView() {
		return (ImageView) this.desktop.getSelectedFrame();
	}
	
	
	
	public void addInternalView(InternalView internalView) {
		this.addInternalView(internalView, false);
	}
	
	public void addInternalView(InternalView internalView, boolean newImage) {
		
		// calculate the new position 
		Point position = new Point(0, 0);
		if(this.desktop.getSelectedFrame() != null && !newImage) {
			Point previousPosition = this.desktop.getSelectedFrame().getLocation();
			position.setLocation(previousPosition.getX() + 50, previousPosition.getY() + 50);
		}
		
		// add the iframe 
		this.desktop.add(internalView);
		
		internalView.setLocation(position);
		internalView.toFront();
		
		try {
			internalView.setSelected(true);
		} catch (PropertyVetoException e) {
			
		}
	}
	
	public void closeAllImageView() {
		for(JInternalFrame imageView : this.desktop.getAllFrames()) {
			imageView.dispose();
		}
	}
	
	
	public File getFileToLoad() throws ChoiceCanceledException {
		
		int results = this.fileChooserOpen.showOpenDialog(this);
		
		if(results != JFileChooser.APPROVE_OPTION) throw new ChoiceCanceledException("choice canceled");
		
		File file = this.fileChooserOpen.getSelectedFile();
		
		return file;
	}	
	
	
	public File getFileToSave() throws ChoiceCanceledException {
		
		int results = this.fileChooserSave.showSaveDialog(this);
		
		if(results != JFileChooser.APPROVE_OPTION) throw new ChoiceCanceledException("choice canceled");
		
		File file = this.fileChooserSave.getSelectedFile();
		
		// on ajoute l'extention ".tournoi" si elle n'y est pas
		String pathToFile = file.getAbsolutePath();
		if( ! pathToFile.endsWith(".jpg") && ! pathToFile.endsWith(".JPG") ) {
			file = new File( file.getAbsoluteFile() + ".jpg" );
		}
				
		return file;
	}
	
}