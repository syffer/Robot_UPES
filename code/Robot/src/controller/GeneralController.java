package controller;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;


import transform.Transformation;
import transform.filter.Canny;
import transform.filter.Laplacian;
import transform.filter.WeightedAverageFilter;
import transform.filter.Sobel;
import transform.morphology.Dilation;
import transform.morphology.Erosion;
import transform.symbolic.Clap;

import model.GeneralModel;
import model.ImageModel;
import model.InternalModel;
import model.StatisticAnalysisInfo;
import model.image.GreyImage;
import model.image.Image;
import model.image.MonoImage;
import model.image.RGBImage;

import view.ChoiceCanceledException; 
import view.InternalView;
import view.JOptionPaneSlider;
import view.View;

public class GeneralController {

	protected GeneralModel model;
	protected View view;
	
	protected ActionLoad actionLoad;
	protected ActionSaveAs actionSaveAs;
	protected ActionCloseAll actionCloseAll;
	
	protected ActionGreyScale actionGreyScale;
	protected ActionMonochromatic actionMonochromatic;
	protected ActionStatisticAnalysis actionStatisticAnalysis;
	
	protected ActionTransformation actionSobel;
	protected ActionTransformation actionLaplacian;
	protected ActionTransformation actionCanny;
	protected ActionClap actionClap;
	protected ActionTransformation actionWeightedAverage;
	
	protected ActionTransformation actionErosion;
	protected ActionTransformation actionDilation;
	
	public GeneralController(GeneralModel generalModel) {
		
		this.model = generalModel;
		this.view = new View();	
		
		//  create actions 
		this.actionLoad = new ActionLoad();
		this.actionSaveAs = new ActionSaveAs();
		this.actionCloseAll = new ActionCloseAll();
		
		this.actionGreyScale = new ActionGreyScale();
		this.actionMonochromatic = new ActionMonochromatic();
		this.actionStatisticAnalysis = new ActionStatisticAnalysis();
		
		this.actionSobel = new ActionTransformation("Sobel", new Sobel());
		this.actionLaplacian = new ActionTransformation("Laplacian", new Laplacian());
		this.actionCanny = new ActionTransformation("Canny", new Canny());
		this.actionClap = new ActionClap();
		
		this.actionWeightedAverage = new ActionTransformation("Weighted Average", new WeightedAverageFilter());
		
		this.actionErosion = new ActionTransformation("Erosion", new Erosion());
		this.actionDilation = new ActionTransformation("Dilatation", new Dilation());
		
		// setting actions 
		this.view.menuLoad.setAction(this.actionLoad);
		this.view.menuSaveAs.setAction(this.actionSaveAs);
		this.view.menuCloseAll.setAction(this.actionCloseAll);
		
		this.view.buttonGreyScale.setAction(this.actionGreyScale);
		this.view.buttonMonochrome.setAction(this.actionMonochromatic);
		this.view.buttonStatisticAnalysis.setAction(this.actionStatisticAnalysis);
		
		this.view.menuSobel.setAction(this.actionSobel);
		this.view.menuLaplacian.setAction(this.actionLaplacian);
		this.view.menuCanny.setAction(this.actionCanny);
		this.view.menuClap.setAction(this.actionClap);
		
		this.view.menuWeightedAverage.setAction(this.actionWeightedAverage);
		
		this.view.menuMorphologyErosion.setAction(this.actionErosion);
		this.view.menuMorphologyDilation.setAction(this.actionDilation);
		
		// initialise observers (setting default values)
		this.model.initialise();
	}
	
		
	private void addInternalModel(Image image, String operation, double executionTime) {
		ImageModel imageModel = new ImageModel(image, operation, executionTime);
		InternalController imageController = new InternalController(imageModel);
		this.addInternalModel(imageController , operation.equals("Loading"));
	}
	
	private void addInternalModel(InternalController internalController, boolean newInternalFrame) {		
		// http://stackoverflow.com/questions/18633164/how-to-ask-are-you-sure-before-close-jinternalframe 
		internalController.internalView.addInternalFrameListener(new ActionInternalFrame());
		
		view.addInternalView(internalController.internalView, newInternalFrame); 
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
				
				Image imageModel = new RGBImage(bufferedImage);
				GeneralController.this.addInternalModel(imageModel, "Loading", endTime - startTime);
				
			} catch (ChoiceCanceledException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}

		@Override
		public void update(Observable observable, Object params) {
			
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
				
				ImageModel imageModel = (ImageModel) model.getSelectedModel();
				imageModel.saveImageAs(file);
				
			} catch (ChoiceCanceledException e) {
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasModelSelected());
		} 
		
	}
	
	public class ActionCloseAll extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionCloseAll() {
			super("Close All");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			view.closeAllImageView();
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasModelSelected());
		} 
	}
	
	
	public class ActionInternalFrame implements InternalFrameListener {
		
		@Override
		public void internalFrameActivated(InternalFrameEvent event) { 			
			InternalView selectedView = view.getSelectedInternalView();
			InternalModel selectedInternalModel = selectedView.getInternalModel();
			model.setSelectedModel(selectedInternalModel);
		}

		@Override
		public void internalFrameDeactivated(InternalFrameEvent event) { 
			model.setSelectedModel(null);			
		}
		
		@Override
		public void internalFrameOpened(InternalFrameEvent event) {
			
		}
		
		@Override
		public void internalFrameClosed(InternalFrameEvent event) {
			
		}

		@Override
		public void internalFrameClosing(InternalFrameEvent event) {
			
		}
		
		@Override
		public void internalFrameDeiconified(InternalFrameEvent event) {
			
		}

		@Override
		public void internalFrameIconified(InternalFrameEvent event) {
			
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
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			
			long startTime = System.currentTimeMillis();
			Image greyImageModel = new GreyImage(imageModel.getBufferedImage());
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addInternalModel(greyImageModel, "Grey Scale", endTime - startTime);
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasModelSelected());
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
				
				ImageModel imageModel = (ImageModel) model.getSelectedModel();
				Image image = imageModel.getImage();
				
				long startTime = System.currentTimeMillis();
				MonoImage monoImageModel = new MonoImage(image, threshold);
				long endTime = System.currentTimeMillis();
				
				GeneralController.this.addInternalModel(monoImageModel, "Monochromatic", endTime - startTime);
				
			} catch (ChoiceCanceledException e) {
				// don't do anything
			}
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasModelSelected());
		} 
	}
	
	
	public class ActionTransformation extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		private String name;
		private Transformation transformation;
		
		public ActionTransformation(String name, Transformation transformation) {
			super(name);
			model.addObserver(this);
			
			this.name = name;
			this.transformation = transformation;
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			Image image = imageModel.getImage();
						
			long startTime = System.currentTimeMillis();
			image.accept(this.transformation);
			long endTime = System.currentTimeMillis();
			
			Image transformedImage = this.transformation.getTransformedImage();
			GeneralController.this.addInternalModel(transformedImage, this.name, endTime - startTime);
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
	}
	
	
	
	public class ActionClap extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionClap() {
			super("CLAP");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			
			try {
				ImageModel imageModel = (ImageModel) model.getSelectedModel();
				Image image = imageModel.getImage();
				
				int threshold = JOptionPaneSlider.showConfirmDialog(view, "CLAP", 0, 255);
				Clap clap = new Clap(threshold);
				
				long startTime = System.currentTimeMillis();
				image.accept(clap);
				long endTime = System.currentTimeMillis();
				
				Image imageFiltered = clap.getTransformedImage();
				GeneralController.this.addInternalModel(imageFiltered, "CLAP", endTime - startTime);	
				
			} catch (ChoiceCanceledException e) {
				
			}		
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
	}
	
	
	
	public class ActionStatisticAnalysis extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionStatisticAnalysis() {
			super("Statistic Analysis");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			Image image = imageModel.getImage();
			
			StatisticAnalysisInfo statisticAnalysisInfo = new StatisticAnalysisInfo(image);
			
			System.out.println(statisticAnalysisInfo.getMean());
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
