package mvc.controller;

import image.GreyImage;
import image.Image;
import image.MonoImage;
import image.RGBImage;

import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.event.InternalFrameEvent;
import javax.swing.event.InternalFrameListener;

import clustering.Cluster;
import clustering.Individual;
import clustering.KMeans;
import clustering.KMeansException;
import clustering.NumberOfVariablesException;

import features.Feature;
import features.FeatureExtractor;


import transform.Threshold;
import transform.Transformation;
import transform.filter.Canny;
import transform.filter.GaussianBlur;
import transform.filter.Laplacian;
import transform.filter.MedianFilter;
import transform.filter.WeightedAverageFilter;
import transform.filter.Sobel;
import transform.morphology.Dilation;
import transform.morphology.Erosion;
import transform.symbolic.Clap;

import mvc.model.GeneralModel;
import mvc.model.Histogram;
import mvc.model.ImageModel;
import mvc.model.InternalModel;
import mvc.model.StatisticAnalysisInfo;
import mvc.view.ChoiceCanceledException;
import mvc.view.InternalView;
import mvc.view.JOptionPaneSlider;
import mvc.view.View;


public class GeneralController {

	protected GeneralModel model;
	protected View view;
	
	protected ActionLoad actionLoad;
	protected ActionSaveAs actionSaveAs;
	protected ActionCloseAll actionCloseAll;
	
	protected ActionGreyScale actionGreyScale;
	protected ActionThreshold actionThreshold;
	protected ActionStatisticAnalysis actionStatisticAnalysis;
	protected ActionHistogram actionHistogram;
	protected ActionTest actionTest;
	
	protected ActionTransformation actionSobelFilter;
	protected ActionTransformation actionLaplacianFilter;
	protected ActionTransformation actionCanny;
	protected ActionClap actionClap;
	protected ActionTransformation actionWeightedAverageFilter;
	protected ActionTransformation actionMedianFilter;
	protected ActionTransformation actionGaussianFilter;
	
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
		this.actionThreshold = new ActionThreshold();
		this.actionStatisticAnalysis = new ActionStatisticAnalysis();
		this.actionHistogram = new ActionHistogram();
		this.actionTest = new ActionTest();
		
		this.actionSobelFilter = new ActionTransformation("Sobel", new Sobel());
		this.actionLaplacianFilter = new ActionTransformation("Laplacian", new Laplacian());
		this.actionCanny = new ActionTransformation("Canny", new Canny());
		this.actionClap = new ActionClap();
		
		this.actionWeightedAverageFilter = new ActionTransformation("Weighted Average", new WeightedAverageFilter());
		this.actionMedianFilter = new ActionTransformation("Median", new MedianFilter());
		this.actionGaussianFilter = new ActionTransformation("Gaussian Blur", new GaussianBlur());
		
		this.actionErosion = new ActionTransformation("Erosion", new Erosion());
		this.actionDilation = new ActionTransformation("Dilatation", new Dilation());
		
		// setting actions 
		this.view.menuLoad.setAction(this.actionLoad);
		this.view.menuSaveAs.setAction(this.actionSaveAs);
		this.view.menuCloseAll.setAction(this.actionCloseAll);
		
		this.view.buttonGreyScale.setAction(this.actionGreyScale);
		this.view.buttonMonochrome.setAction(this.actionThreshold);
		this.view.buttonStatisticAnalysis.setAction(this.actionStatisticAnalysis);
		this.view.buttonHistogram.setAction(this.actionHistogram);
		this.view.buttonTest.setAction(this.actionTest);
		
		this.view.menuSobel.setAction(this.actionSobelFilter);
		this.view.menuLaplacian.setAction(this.actionLaplacianFilter);
		this.view.menuCanny.setAction(this.actionCanny);
		this.view.menuClap.setAction(this.actionClap);
		
		this.view.menuWeightedAverage.setAction(this.actionWeightedAverageFilter);
		this.view.menuMedianFilter.setAction(this.actionMedianFilter);
		this.view.menuGaussianFilter.setAction(this.actionGaussianFilter);
		
		this.view.menuGreyScale.setAction(this.actionGreyScale);
		this.view.menuThreshold.setAction(this.actionThreshold);
		this.view.menuErosion.setAction(this.actionErosion);
		this.view.menuDilation.setAction(this.actionDilation);
		
		// initialise observers (setting default values)
		this.model.initialise();
	}
		
	private void addInternalModel(ImageModel imageModel) {
		InternalController imageController = new ImageController(imageModel);
		this.addInternalModel(imageController , imageModel.getOperationName().equals("Loading"));
	}
	
	private void addInternalModel(StatisticAnalysisInfo statisticAnalysisInfo) {
		InternalController imageController = new StatisticAnalysisController(statisticAnalysisInfo);
		this.addInternalModel(imageController);
	}
	
	private void addInternalModel(Histogram histogram) {
		InternalController imageController = new HistogramController(histogram);
		this.addInternalModel(imageController);
	}
	
	private void addInternalModel(InternalController internalController) {
		this.addInternalModel(internalController, false);
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
				
				Image image = new RGBImage(bufferedImage);
				GeneralController.this.addInternalModel(new ImageModel(image, "Loading", endTime - startTime));
				
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
			this.setEnabled(model.hasImageModelSelected());
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
			Image greyImage = new GreyImage(imageModel.getImage());
			long endTime = System.currentTimeMillis();
			
			GeneralController.this.addInternalModel(new ImageModel(greyImage, imageModel.getOriginalImage(), "Grey Scale", endTime - startTime));
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
	}
	
	
	public class ActionThreshold extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionThreshold() {
			super("Threshold");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) { 
			try {
				int value = JOptionPaneSlider.showConfirmDialog(view, "Monochromatic", 0, 255);
				Threshold threashold = new Threshold(value);
					
				ImageModel imageModel = (ImageModel) model.getSelectedModel();
				Image image = imageModel.getImage();
				
				long startTime = System.currentTimeMillis();
				//MonoImage monoImage = new MonoImage(image, threshold);
				image.accept(threashold);
				long endTime = System.currentTimeMillis();
				
				Image monoImage = threashold.getTransformedImage();
				GeneralController.this.addInternalModel(new ImageModel(monoImage, image, "Monochromatic", endTime - startTime));
				
			} catch (ChoiceCanceledException e) {
				// don't do anything
			}
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
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
			GeneralController.this.addInternalModel(new ImageModel(transformedImage, image, this.name, endTime - startTime));
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
				GeneralController.this.addInternalModel(new ImageModel(imageFiltered, image, "CLAP", endTime - startTime));	
				
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
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			Image image = imageModel.getImage();
			
			StatisticAnalysisInfo statisticAnalysisInfo;
			
			if(imageModel.hasOriginalImage()) statisticAnalysisInfo = new StatisticAnalysisInfo(image, imageModel.getOriginalImage());
			else statisticAnalysisInfo = new StatisticAnalysisInfo(image);
			
			GeneralController.this.addInternalModel(statisticAnalysisInfo); 
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
	}
	
	
	
	public class ActionHistogram extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionHistogram() {
			super("Histogram");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			Image image = imageModel.getImage();
			
			Histogram histogram = new Histogram();
			
			image.accept(histogram);
			
			GeneralController.this.addInternalModel(histogram); 
		}

		@Override
		public void update(Observable observable, Object params) {
			this.setEnabled(model.hasImageModelSelected());
		} 
	}
	
	
	
	public class ActionTest extends AbstractAction implements Observer {
		private static final long serialVersionUID = 1L;

		public ActionTest() {
			super("Test");
			model.addObserver(this);
		}
		
		@Override
		public void actionPerformed(ActionEvent event) {
			// TODO Auto-generated method stub
			ImageModel imageModel = (ImageModel) model.getSelectedModel();
			MonoImage image = (MonoImage) imageModel.getImage();
		
			List<Feature> features = FeatureExtractor.extract(image);
			
			try {
			
				Cluster cluster = new Cluster(5);
				for(Feature feature : features) {
					Individual individual = new Individual(feature.getWidth(), feature.getHeight(), feature.getArea(), feature.getBendingenergy(), feature.getCircularity());
					cluster.add(individual);
				}
				
				KMeans kmean = new KMeans();
				List<Cluster> clusters = kmean.clustering(false, cluster, 4);
				for(Cluster c : clusters) {
					System.out.println(c);
				}
			
			} catch (NumberOfVariablesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (KMeansException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
