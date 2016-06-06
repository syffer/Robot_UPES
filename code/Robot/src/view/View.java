package view;

import java.awt.BorderLayout;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
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
	public JMenuItem menuSave;
	public JMenuItem menuSaveAs;
	
	// toolbar 
	public JToolBar toolBar;
	
	// internals windows 
	public JDesktopPane desktop;
	public JButton buttonGreyScale;
	public JButton buttonSTRT;
	public JButton buttonISTRT;
	public JButton buttonMonochrome;
	
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
		this.menuSave = new JMenuItem("Save");
		this.menuSaveAs = new JMenuItem("Save as ...");
		menuFile.add(this.menuLoad);
		//menuFile.add(this.menuSave);
		menuFile.add(this.menuSaveAs);
		
		menuBar.add(menuFile);
		
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
		
		this.toolBar.add(this.buttonGreyScale);
		this.toolBar.add(this.buttonSTRT);
		this.toolBar.add(this.buttonISTRT);
		this.toolBar.add(this.buttonMonochrome);
		
		this.add(this.toolBar, BorderLayout.PAGE_START);
	}
	
	private void initialiseDesktop() {
		this.desktop = new JDesktopPane();
		this.add(desktop);
	}
	
	
	public ImageView getSelectedImageView() {
		return (ImageView) this.desktop.getSelectedFrame();
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