package controller;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


import rajan.Rajan;
import rajan.RajanException;
import transform.filter.AbstractFilter;
import transform.filter.Canny;
import transform.filter.Laplacian;
import transform.filter.MediumFilter;
import transform.filter.Sobel;
import transform.morphology.Morphology;

import model.GeneralModel;
import model.GreyImageModel;
import model.ImageModel;
import model.MonoImageModel;
import model.RGBImageModel;

import view.ChoiceCanceledException;
import view.ImageView;
import view.JOptionPaneSlider;
import view.View;

public class GeneralController {

	protected GeneralModel model;
	protected View view;
	
	protected ActionLoad actionLoad;
	protected ActionSave actionSave;
	protected ActionSaveAs actionSaveAs;
	
	protected ActionGreyScale actionGreyScale;
	protected ActionMonochromatic actionMonochromatic;	
	protected ActionSTRT actionSTRT;
	
	protected ActionSobel actionSobel;
	protected ActionLaplacian actionLaplacian;
	protected ActionCanny actionCanny;
	protected ActionWeightedAverage actionWeightedAverage;
	
	protected ActionErosion actionErosion;
	
	public GeneralController(GeneralModel generalModel) {
		
		this.model = generalModel;
		this.view = new View();	
		
		//  create actions 
		this.actionLoad = new ActionLoad();
		this.actionSave = new ActionSave();
		this.actionSaveAs = new ActionSaveAs();
		
		this.actionGreyScale = new ActionGreyScale();
		this.actionMonochromatic = new ActionMonochromatic();
		this.actionSTRT = new ActionSTRT();
		
		this.actionSobel = new ActionSobel();
		this.actionLaplacian = new ActionLaplacian();
		this.actionCanny = new ActionCanny();
		this.actionWeightedAverage = new ActionWeightedAverage();
		
		this.actionErosion = new ActionErosion();
		
		// setting actions 
		this.view.menuLoad.setAction(this.actionLoad);
		this.view.menuSave.setAction(this.actionSave);
		this.view.menuSaveAs.setAction(this.actionSaveAs);
		
		this.view.buttonGreyScale.setAction(this.actionGreyScale);
		this.view.buttonMonochrome.setAction(this.actionMonochromatic);
		this.view.buttonSTRT.setAction(this.actionSTRT);
		
		this.view.menuSobel.setAction(this.actionSobel);
		this.view.menuLaplacian.setAction(this.actionLaplacian);
		this.view.menuCanny.setAction(this.actionCanny);
		this.view.menuWeightedAverage.setAction(this.actionWeightedAverage);
		
		this.view.menuMorphologyErosion.setAction(this.actionErosion);
		
		// initialise observers (setting default values)
		this.model.initialise();
	}
	
	
	private void addImageModel(ImageModel imageModel, String operation, double executionTime) {
		ImageController imageController = new ImageController(imageModel, operation, executionTime);
		
		// http://stackoverflow.com/questions/18633164/how-to-ask-are-you-sure-before-close-jinternalframe 
		imageController.imageView.addInternalFrameListener(new InternalFrameAction());
		
		view.desktop.add(imageController.imageView);
	}
		
	
	
	public class ActionLoad extends AbstractAction implements Observer {
		private static final long serialVersionUID = 7721629641991701632L;

		public ActionLoad() {
			super("Load"); 
			
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			try { 
				
				File choosenFile = view.getFileToLoad();
				
				long startTime = System.currentTimeMillis();
				BufferedImage bufferedImage = ImageIO.read(choosenFile);
				long endTime = System.currentTimeMillis();
				
				ImageModel imageModel = new RGBImageModel(bufferedImage);
				GeneralController.this.addImageModel(imageModel, "Loading", endTime - startTime);
				
			} catch (ChoiceCanceledException e) {
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}

		@Override
		public void update(Observable observable, Object params) {
			
		}
	}
		
	
	public class ActionSave extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionSave() {
			super("Save");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(Observable observable, Object params) { 			
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class ActionSaveAs extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionSaveAs() {
			super("Save as ...");
			
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			try {
				File file = view.getFileToSave();
				
				ImageModel imageModel = model.getSelectedImageModel();
				imageModel.saveAs(file);
				
			} catch (ChoiceCanceledException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	
	public class InternalFrameAction implements InternalFrameListener {
		
		@Override
		public void internalFrameActivated(InternalFrameEvent event) { 			
			ImageView selectedView = view.getSelectedImageView();
			ImageModel selectedImageModel = selectedView.getImageModel();
			model.setSelectedImageModel(selectedImageModel);
		}

		@Override
		public void internalFrameDeactivated(InternalFrameEvent event) { 
			model.setSelectedImageModel(null);			
		}
		
		@Override
		public void internalFrameOpened(InternalFrameEvent event) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void internalFrameClosed(InternalFrameEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent event) {
			// TODO Auto-generated method stub
		}
		
		@Override
		public void internalFrameDeiconified(InternalFrameEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void internalFrameIconified(InternalFrameEvent event) {
			// TODO Auto-generated method stub
		} 
	}
	
	
	public class ActionGreyScale extends AbstractAction implements Observer {
		private static final long serialVersionUID = 6509734674576991637L;

		public ActionGreyScale() {
			super("Grey Scale"); 
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel imageModel = model.getSelectedImageModel();
			
			long startTime = System.currentTimeMillis();
			ImageModel greyImageModel = new GreyImageModel(imageModel.getBufferedImage());
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(greyImageModel, "Grey Scale", endTime - startTime);
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class ActionMonochromatic extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionMonochromatic() {
			super("Mono image");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) { 
			try {
				int threshold = JOptionPaneSlider.showConfirmDialog(view, "Monochromatic", 0, 255);
				
				ImageModel imageModel = model.getSelectedImageModel();
				
				long startTime = System.currentTimeMillis();
				MonoImageModel monoImageModel = new MonoImageModel(imageModel, threshold);
				long endTime = System.currentTimeMillis();
				
				GeneralController.this.addImageModel(monoImageModel, "Monochromatic", endTime - startTime);
				
			} catch (ChoiceCanceledException e) {
				// don't do anything
			}
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class ActionSTRT extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionSTRT() {
			super("STRT");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			try {
				
				ImageModel imageModel = model.getSelectedImageModel();
				List<Set<Integer>> sequence = imageModel.toSequence();
				
				/*
				List<Set<Integer>> test = new ArrayList<Set<Integer>>();
				test.add(new HashSet<Integer>(Arrays.asList(1, 2, 3, 4)));
				test.add(new HashSet<Integer>());
				test.add(new HashSet<Integer>());
				
				Rajan.forward(test);
				*/
				
				long startTime = System.currentTimeMillis();
				List<Set<Integer>> sequenceTransformed = Rajan.forward(sequence);
				long endTime = System.currentTimeMillis();
							
				System.out.println((endTime - startTime) + " ms");
				
				//System.out.println(sequenceTransformed.get(1));
				
			} catch (RajanException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	
	public class ActionSobel extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionSobel() {
			super("Sobel");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel image = model.getSelectedImageModel();
			
			AbstractFilter filter = new Sobel();
			
			long startTime = System.currentTimeMillis();
			ImageModel imageFiltered = filter.filter(image);
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(imageFiltered, "Sobel", endTime - startTime);
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class ActionLaplacian extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionLaplacian() {
			super("Laplacian");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel image = model.getSelectedImageModel();
			
			AbstractFilter filter = new Laplacian();
			
			long startTime = System.currentTimeMillis();
			ImageModel imageFiltered = filter.filter(image);
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(imageFiltered, "Laplacian", endTime - startTime);
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	
	public class ActionCanny extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionCanny() {
			super("Canny");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel image = model.getSelectedImageModel();
			
			AbstractFilter filter = new Canny();
			
			long startTime = System.currentTimeMillis();
			ImageModel imageFiltered = filter.filter(image);
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(imageFiltered, "Canny", endTime - startTime);			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class ActionWeightedAverage extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionWeightedAverage() {
			super("Weighted Average");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel image = model.getSelectedImageModel();
			
			AbstractFilter filter = new MediumFilter();
			
			long startTime = System.currentTimeMillis();
			ImageModel imageFiltered = filter.filter(image);
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(imageFiltered, "Medium Filter", endTime - startTime);			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	
	
	public class ActionErosion extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionErosion() {
			super("Erosion");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel image = model.getSelectedImageModel();
						
			long startTime = System.currentTimeMillis();
			ImageModel imageFiltered = Morphology.erosion(image);
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addImageModel(imageFiltered, "Erosion", endTime - startTime);		
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
	
	public class Action extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public Action() {
			super("name");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
		
	}
	
}
