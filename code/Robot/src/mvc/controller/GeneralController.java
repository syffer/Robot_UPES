package mvc.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import clustering.Cluster;
import clustering.Individual;
import clustering.KMeans;
import clustering.KMeansException;
import clustering.NumberOfVariablesException;

import features.Feature;
import features.FeatureExtractor;

import transform.filter.Canny;
import transform.filter.GaussianBlur;
import transform.filter.Laplacian;
import transform.filter.MedianFilter;
import transform.filter.WeightedAverageFilter;
import transform.filter.Sobel;
import transform.morphology.Dilation;
import transform.morphology.Erosion;

import mvc.model.GeneralModel;
import mvc.model.Histogram;
import mvc.model.ImageModel;
import mvc.model.InternalModel;
import mvc.model.StatisticAnalysisInfo;
import mvc.view.InternalView;
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
		this.actionLoad = new ActionLoad(this);
		this.actionSaveAs = new ActionSaveAs(this);
		this.actionCloseAll = new ActionCloseAll(this);
		
		this.actionGreyScale = new ActionGreyScale(this);
		this.actionThreshold = new ActionThreshold(this);
		this.actionStatisticAnalysis = new ActionStatisticAnalysis(this);
		this.actionHistogram = new ActionHistogram(this);
		this.actionTest = new ActionTest();
		
		this.actionSobelFilter = new ActionTransformation(this, "Sobel", new Sobel());
		this.actionLaplacianFilter = new ActionTransformation(this, "Laplacian", new Laplacian());
		this.actionCanny = new ActionTransformation(this, "Canny", new Canny());
		this.actionClap = new ActionClap(this);
		
		this.actionWeightedAverageFilter = new ActionTransformation(this, "Weighted Average", new WeightedAverageFilter());
		this.actionMedianFilter = new ActionTransformation(this, "Median", new MedianFilter());
		this.actionGaussianFilter = new ActionTransformation(this, "Gaussian Blur", new GaussianBlur());
		
		this.actionErosion = new ActionTransformation(this, "Erosion", new Erosion());
		this.actionDilation = new ActionTransformation(this, "Dilatation", new Dilation());
		
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
	
	
	public boolean hasImageModelSelected() {
		return this.model.hasImageModelSelected();
	}
	
	public InternalModel getSelectedInternalModel() {
		return this.model.getSelectedModel();
	}
	
	public void setSelectedInternalModel(InternalModel selectedModel) {
		this.model.setSelectedModel(selectedModel);
	}
	
	
	public InternalView getSelectedInternalView() {
		return this.view.getSelectedInternalView();
	}
	
	
	
	
	public void addInternalModel(ImageModel imageModel) {
		InternalController imageController = new ImageController(imageModel);
		this.addInternalModel(imageController , imageModel.getOperationName().equals("Loading"));
	}
	
	public void addInternalModel(StatisticAnalysisInfo statisticAnalysisInfo) {
		InternalController imageController = new StatisticAnalysisController(statisticAnalysisInfo);
		this.addInternalModel(imageController);
	}
	
	public void addInternalModel(Histogram histogram) {
		InternalController imageController = new HistogramController(histogram);
		this.addInternalModel(imageController);
	}
	
	public void addInternalModel(InternalController internalController) {
		this.addInternalModel(internalController, false);
	}
			
	public void addInternalModel(InternalController internalController, boolean newInternalFrame) {		
		// http://stackoverflow.com/questions/18633164/how-to-ask-are-you-sure-before-close-jinternalframe 
		internalController.internalView.addInternalFrameListener(new ActionInternalFrame(this));
		
		view.addInternalView(internalController.internalView, newInternalFrame); 
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
