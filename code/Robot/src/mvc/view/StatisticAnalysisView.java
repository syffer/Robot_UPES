package mvc.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;

import mvc.model.StatisticAnalysisInfo;

public class StatisticAnalysisView extends InternalView {
	private static final long serialVersionUID = 1L;
	
	private JLabel labelMSE;
	private JLabel labelPSNR;
	private JLabel labelMean;
	private JLabel labelVariance;
	private JLabel labelStandardDeviation;
	
	public StatisticAnalysisView(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo);
		
		// setting layout 
		this.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		// creating components 
		this.labelMSE = new JLabel("MSE : " + String.format("%.2f", statisticAnalysisInfo.getMSE()));
		this.labelPSNR = new JLabel("PSNR : " + String.format("%.2f", statisticAnalysisInfo.getPSNR()) + " db");
		this.labelMean = new JLabel("Mean : " + String.format("%.2f", statisticAnalysisInfo.getMean()));
		this.labelVariance = new JLabel("Variance : " + String.format("%.2f", statisticAnalysisInfo.getVariance()));
		this.labelStandardDeviation = new JLabel("Standard Deviation : " + String.format("%.2f", statisticAnalysisInfo.getStandardDeviation()));
		
		// adding compponents 
		this.add(this.labelMSE, gbc);
		this.add(this.labelPSNR, gbc);
		this.add(this.labelMean, gbc);
		this.add(this.labelVariance, gbc);
		this.add(this.labelStandardDeviation, gbc);
		
		this.setClosable(true);
		this.setResizable(false);
		this.setMaximizable(true);
		this.setIconifiable(true); 
		
		this.pack();
		this.setVisible(true);
	}
	
}
