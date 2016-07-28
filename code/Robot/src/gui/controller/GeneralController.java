package gui.controller;

import image.MonoImage;

import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.AbstractAction;

import classification.Cluster;
import classification.Individual;
import classification.KMeans;
import classification.KMeansException;
import classification.NumberOfVariablesException;

import features.Feature;
import features.FeatureExtractor;
import features.PositionnedObject;
import gui.model.ClassificationModel;
import gui.model.FeatureExtractionModel;
import gui.model.GeneralModel;
import gui.model.HistogramModel;
import gui.model.ImageModel;
import gui.model.InternalModel;
import gui.model.StatisticAnalysisInfo;
import gui.view.InternalView;
import gui.view.View;

import transform.cellularAutomata.CellularAutomataAlgoII;
import transform.filter.Canny;
import transform.filter.GaussianBlur;
import transform.filter.Laplacian;
import transform.filter.MedianFilter;
import transform.filter.WeightedAverageFilter;
import transform.filter.Sobel;
import transform.morphology.Dilation;
import transform.morphology.Erosion;
import transform.morphology.GreyScale;
import transform.morphology.Negative;



/**
 * General controller of the GUI.
 * @author Maxime PINEAU
 */
public class GeneralController {

	protected GeneralModel model;
	protected View view;
	
	protected ActionLoad actionLoad;
	protected ActionSaveAs actionSaveAs;
	protected ActionCloseAll actionCloseAll;
		
	protected ActionTransformation actionSobelFilter;
	protected ActionTransformation actionLaplacianFilter;
	protected ActionTransformation actionCanny;
	
	protected ActionClap actionClap;
	protected ActionTransformation actionWeightedAverageFilter;
	protected ActionTransformation actionMedianFilter;
	protected ActionTransformation actionGaussianFilter;
	protected ActionTransformation actionCellularAutomataII;
	
	protected ActionTransformation actionGreyScale;
	protected ActionThreshold actionThreshold;
	protected ActionTransformation actionNegative;
	protected ActionTransformation actionErosion;
	protected ActionTransformation actionDilation;
	
	protected ActionFeatureExtraction actionFeatureExtraction;
	protected ActionObjectRecognition actionObjectRecognition;
	protected ActionKMeans actionKMeans;
	
	protected ActionStatisticAnalysis actionStatisticAnalysis;
	protected ActionHistogram actionHistogram;
	protected ActionTest actionTest;
	
	
	/**
	 * Creates a new GeneralController using the given model.
	 * Creates the GUI view and show it to the user. 
	 * @param generalModel the model of the controller 
	 */
	public GeneralController(GeneralModel generalModel) {
		
		this.model = generalModel;
		this.view = new View();	
		
		//  create actions 
		this.actionLoad = new ActionLoad(this);
		this.actionSaveAs = new ActionSaveAs(this);
		this.actionCloseAll = new ActionCloseAll(this);
				
		this.actionSobelFilter = new ActionTransformation(this, "Sobel", new Sobel());
		this.actionLaplacianFilter = new ActionTransformation(this, "Laplacian", new Laplacian());
		this.actionCanny = new ActionTransformation(this, "Canny", new Canny());
		this.actionClap = new ActionClap(this);
		
		this.actionWeightedAverageFilter = new ActionTransformation(this, "Weighted Average", new WeightedAverageFilter());
		this.actionMedianFilter = new ActionTransformation(this, "Median", new MedianFilter());
		this.actionGaussianFilter = new ActionTransformation(this, "Gaussian Blur", new GaussianBlur());
		this.actionCellularAutomataII = new ActionTransformation(this, "Cellular Automata II", new CellularAutomataAlgoII());
		
		this.actionGreyScale = new ActionTransformation(this, "Grey Scale", new GreyScale());
		this.actionThreshold = new ActionThreshold(this);
		this.actionNegative = new ActionTransformation(this, "Negative", new Negative());
		this.actionErosion = new ActionTransformation(this, "Erosion", new Erosion());
		this.actionDilation = new ActionTransformation(this, "Dilatation", new Dilation());
		
		this.actionFeatureExtraction = new ActionFeatureExtraction(this);
		this.actionObjectRecognition = new ActionObjectRecognition(this);
		this.actionKMeans = new ActionKMeans(this);
		
		this.actionStatisticAnalysis = new ActionStatisticAnalysis(this);
		this.actionHistogram = new ActionHistogram(this);
		this.actionTest = new ActionTest();
		
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
		
		this.view.menuCellularAutomataII.setAction(this.actionCellularAutomataII);
		
		this.view.menuGreyScale.setAction(this.actionGreyScale);
		this.view.menuThreshold.setAction(this.actionThreshold);
		this.view.menuNegative.setAction(this.actionNegative);
		this.view.menuErosion.setAction(this.actionErosion);
		this.view.menuDilation.setAction(this.actionDilation);
		
		this.view.menuFeatureExtraction.setAction(this.actionFeatureExtraction);
		this.view.menuObjectRecognition.setAction(this.actionObjectRecognition);
		this.view.menuKMeans.setAction(this.actionKMeans);
		
		// initialise observers (setting default values)
		this.model.initialise();
	}
	
	
	/**
	 * @return true if there is a ImageModel selected by the user (via the GUI), false otherwise
	 */
	public boolean hasImageModelSelected() {
		return this.model.hasImageModelSelected();
	}
	
	/**
	 * @return true if there is a InternalModel selected by the user (via the GUI), false otherwise
	 */
	public boolean hasInternalModelSelected() {
		return this.model.hasModelSelected(); 
	}
	
	/**
	 * @return the internal model selected by the user via the GUI
	 */
	public InternalModel getSelectedInternalModel() {
		return this.model.getSelectedModel();
	}
	
	/**
	 * Sets the selected internal model 
	 * @param selectedModel the new selected internal model 
	 */
	public void setSelectedInternalModel(InternalModel selectedModel) {
		this.model.setSelectedModel(selectedModel);
	}
	
	
	/**
	 * @return the internal view selected by the user via the GUI
	 */
	public InternalView getSelectedInternalView() {
		return this.view.getSelectedInternalView();
	}
	
	
	
	
	/**
	 * Adds a new ImageModel to the GUI and select it by opening a new internal view.
	 * @param imageModel the ImageModel to be added in the GUI
	 * @see gui.view.View  
	 */
	public void addInternalModel(ImageModel imageModel) {
		InternalController imageController = new ImageController(imageModel);
		this.addInternalModel(imageController , imageModel.getOperationName().equals("Loading"));
	}
	
	/**
	 * Add a new StatisticAnalysisInfo to the GUI and select it by opening a new internal view.
	 * @param statisticAnalysisInfo the StatisticAnalysisInfo to be added to the GUI
	 */
	public void addInternalModel(StatisticAnalysisInfo statisticAnalysisInfo) {
		InternalController imageController = new StatisticAnalysisController(statisticAnalysisInfo);
		this.addInternalModel(imageController);
	}
	
	/**
	 * Add a new HistogramModel to the GUI and select it by opening a new internal view.
	 * @param histogramModel the HistogramModel to be added to the GUI 
	 */
	public void addInternalModel(HistogramModel histogramModel) {
		InternalController imageController = new HistogramController(histogramModel);
		this.addInternalModel(imageController);
	}
	
	/**
	 * Add a new FeatureExtractionModel to the GUI and select it by opening a new internal view.
	 * @param featureExtractionModel the new internal model to be added to the GUI
	 */
	public void addInternalModel(FeatureExtractionModel featureExtractionModel) {
		FeatureExtractionController featureExtractionController = new FeatureExtractionController(featureExtractionModel);
		this.addInternalModel(featureExtractionController);
	}
	
	/**
	 * Add a new ClassificationModel to the GUI and select it by opening a new internal view.
	 * @param classificationModel the new internal model to be added to the GUI
	 */
	public void addInternalModel(ClassificationModel classificationModel) {
		ClassificationController classificationController = new ClassificationController(classificationModel);
		this.addInternalModel(classificationController);
	}
	
	/**
	 * Add a new internal controller to the GUI. 
	 * Open the corresponding internal view relatively to the last selected internal view 
	 * @param internalController the new internal controller to be added to the GUI
	 */
	public void addInternalModel(InternalController internalController) {
		this.addInternalModel(internalController, false);
	}
	
	/**
	 * Add a new internal controller to the GUI. 
	 * @param internalController the new internal controller to be added to the GUI
	 * @param newInternalFrame whether if the new internal controller should be added relatively to the last selected internal view or not
	 */
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
		
			List<PositionnedObject> features = FeatureExtractor.extract(image);
			
			try {
			
				Cluster cluster = new Cluster(5);
				for(PositionnedObject someObject : features) {
					Feature feature = someObject.getFeature();
					Individual individual = new Individual(feature.getWidth(), feature.getHeight(), feature.getArea(), feature.getBendingEnergy(), feature.getCircularity());
					cluster.add(individual);
				}
				
				KMeans kmean = new KMeans();
				List<Cluster> clusters = kmean.clustering(cluster, 4);
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
