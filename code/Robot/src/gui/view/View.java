package gui.view;

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


/**
 * Represents the view used for the GUI
 * @author Maxime PINEAU
 */
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
		
	public JMenuItem menuWeightedAverage;
	public JMenuItem menuMeanFilter;
	public JMenuItem menuMedianFilter;
	public JMenuItem menuGaussianFilter;
	public JMenuItem menuCellularAutomataI;
	public JMenuItem menuCellularAutomataII;
	
	public JMenuItem menuGreyScale;
	public JMenuItem menuThreshold;
	public JMenuItem menuNegative;
	public JMenuItem menuErosion;
	public JMenuItem menuDilation;
	public JMenuItem menuClap;
	
	public JMenuItem menuFeatureExtraction;
	public JMenuItem menuObjectRecognition;
	public JMenuItem menuKMeans;
	
	// toolbar 
	public JToolBar toolBar;
	public JButton buttonGreyScale;
	public JButton buttonMonochrome;
	public JButton buttonStatisticAnalysis;
	public JButton buttonHistogram;
	public JButton buttonSegmentation;
	public JButton buttonTest;
	
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
		
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // close the window when you clic on "x" 
		this.pack();
		this.setSize(600, 600);
		this.setVisible(true);
	}
	
	
	/**
	 * Initializes the menu bar
	 */
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
		menuNoiseRemoval.add(this.menuMedianFilter);
		menuNoiseRemoval.add(this.menuGaussianFilter);
		menuNoiseRemoval.add(menuCellularAutomata);
		
		JMenu menuEdgeDetection = new JMenu("Edge Detection");
		this.menuSobel = new JMenuItem("Sobel");
		this.menuLaplacian = new JMenuItem("Laplacian");
		this.menuCanny = new JMenuItem("Canny");
		menuEdgeDetection.add(this.menuSobel);
		menuEdgeDetection.add(this.menuLaplacian);
		menuEdgeDetection.add(this.menuCanny);
		
		JMenu menuMorphology = new JMenu("Morphology");
		this.menuGreyScale = new JMenuItem("Grey Scale");
		this.menuThreshold = new JMenuItem("Threshold");
		this.menuNegative = new JMenuItem("Negative");
		this.menuErosion = new JMenuItem("Erosion");
		this.menuDilation = new JMenuItem("Dilation");
		this.menuClap = new JMenuItem("CLAP");
		menuMorphology.add(this.menuGreyScale);
		menuMorphology.add(this.menuThreshold);
		menuMorphology.add(this.menuNegative);
		menuMorphology.add(this.menuErosion);
		menuMorphology.add(this.menuDilation);
		menuMorphology.add(this.menuClap);
		
		JMenu menuExtraction = new JMenu("Extraction");
		this.menuFeatureExtraction = new JMenuItem("Feature Extraction");
		this.menuObjectRecognition = new JMenuItem("Object Recognition");
		menuExtraction.add(this.menuFeatureExtraction);
		menuExtraction.add(this.menuObjectRecognition);
		
		JMenu menuClassification = new JMenu("Classification");
		this.menuKMeans = new JMenuItem("KMeans");
		menuClassification.add(this.menuKMeans);
		
		menuBar.add(menuFile);
		menuBar.add(menuNoiseRemoval);
		menuBar.add(menuEdgeDetection);
		menuBar.add(menuMorphology);
		menuBar.add(menuExtraction);
		menuBar.add(menuClassification);
		
		this.setJMenuBar(menuBar);
	}
	
	/**
	 * Initializes the file choosers (open and save)
	 */
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
		this.buttonMonochrome = new JButton("Monochrome");
		this.buttonStatisticAnalysis = new JButton("Statistic Analysis");
		this.buttonHistogram = new JButton("Histogram");
		this.buttonSegmentation = new JButton("Segmentation");
		this.buttonTest = new JButton("Test");
		
		this.toolBar.add(this.buttonGreyScale);
		this.toolBar.add(this.buttonMonochrome);
		this.toolBar.add(this.buttonStatisticAnalysis);
		this.toolBar.add(this.buttonHistogram);
		this.toolBar.add(this.buttonSegmentation);
		this.toolBar.add(this.buttonTest);
		
		this.add(this.toolBar, BorderLayout.PAGE_START);
	}
	
	/**
	 * Initialize the desktop (where the windows are dragged/dropped)
	 */
	private void initialiseDesktop() {
		this.desktop = new JDesktopPane();
		this.add(desktop);
	}
	
	
	/**
	 * Returns the actual selected internal view (window) in the destop 
	 * @return the selected internal view in the desktop 
	 */
	public InternalView getSelectedInternalView() {
		return (InternalView) this.desktop.getSelectedFrame();
	}
	
	
	
	/**
	 * Adds a new internal view (window) in the desktop.
	 * The new internal window will be then automatically selected. 
	 * It will also be added relatively to the last selected internal view.
	 * @param internalView the internal view to be added 
	 */
	public void addInternalView(InternalView internalView) {
		this.addInternalView(internalView, false);
	}
	
	/**
	 * Adds a new internal view (window) in the desktop.
	 * The new internal window will be then automatically selected. 
	 * @param internalView the internal view to be added 
	 * @param newImage add the new internal view at the position (0, 0) if true, and relatively to the last selected internal window otherwise
	 */
	public void addInternalView(InternalView internalView, boolean newImage) {
		
		// calculate the new position 
		Point position = new Point(0, 0);
		if(this.desktop.getSelectedFrame() != null && !newImage) {
			Point previousPosition = this.desktop.getSelectedFrame().getLocation();
			position.setLocation(previousPosition.getX() + 50, previousPosition.getY() + 50);
		}
		
		// add the iframe (internal view)
		this.desktop.add(internalView);
		
		// select it 
		internalView.setLocation(position);
		internalView.toFront();
		
		try {
			internalView.setSelected(true);
		} catch (PropertyVetoException e) {
			
		}
	}
	
	/**
	 * Closes all the internal views opened in the desktop
	 */
	public void closeAllImageView() {
		for(JInternalFrame imageView : this.desktop.getAllFrames()) {
			imageView.dispose();
		}
	}
	
	
	/**
	 * Opens a file chooser allowing the user to choose a file that he wants to open, and return it.
	 * @return the choosed file to be open
	 * @throws ChoiceCanceledException if the user cancel the selection (e.g. by closing the file chooser)
	 */
	public File getFileToLoad() throws ChoiceCanceledException {
		
		int results = this.fileChooserOpen.showOpenDialog(this);
		
		if(results != JFileChooser.APPROVE_OPTION) throw new ChoiceCanceledException("choice canceled");
		
		File file = this.fileChooserOpen.getSelectedFile();
		
		return file;
	}	
	
	
	/**
	 * Opens a file chooser allowing the user to choose a "jpg" file for saving something.
	 * @return the choosed file where something will be saved 
	 * @throws ChoiceCanceledException if the user cancel the selection (e.g. by closing the file chooser)
	 */
	public File getFileToSave() throws ChoiceCanceledException {
		
		int results = this.fileChooserSave.showSaveDialog(this);
		
		if(results != JFileChooser.APPROVE_OPTION) throw new ChoiceCanceledException("choice canceled");
		
		File file = this.fileChooserSave.getSelectedFile();
		
		// adding the file extension if none exists 
		String pathToFile = file.getAbsolutePath();
		if( ! pathToFile.endsWith(".jpg") && ! pathToFile.endsWith(".JPG") ) {
			file = new File( file.getAbsoluteFile() + ".jpg" );
		}
				
		return file;
	}
	
}