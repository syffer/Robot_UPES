package view;

import javax.swing.JLabel;

import model.StatisticAnalysisInfo;

public class StatisticAnalysisView extends InternalView {
	private static final long serialVersionUID = 1L;
	
	private JLabel labelMSE;
	private JLabel labelPSNR;
	private JLabel labelMean;
	private JLabel labelVariance;
	private JLabel labelStandardDeviation;
	
	public StatisticAnalysisView(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo);
		
		this.labelMSE = new JLabel("MSE : ");
		this.labelPSNR = new JLabel("PSNR : ");
		this.labelMean = new JLabel("Mean : ");
		this.labelVariance = new JLabel("Variance : ");
		this.labelStandardDeviation = new JLabel("Standard Deviation : ");
		
		this.add(this.labelMSE);
		this.add(this.labelPSNR);
		this.add(this.labelMean);
		this.add(this.labelVariance);
		this.add(this.labelStandardDeviation);
		
		this.setClosable(true);
		this.setResizable(false);
		this.setMaximizable(true);
		this.setIconifiable(true); 
		
		this.pack();
		this.setVisible(true);
	}
	
}
