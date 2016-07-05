package gui.controller;

import gui.model.StatisticAnalysisInfo;
import gui.view.StatisticAnalysisView;

/**
 * Controller of the statistic analysis model and view.
 * Creates the statistic analysis view.
 * @author Maxime PINEAU
 */
public class StatisticAnalysisController extends InternalController {
	
	public StatisticAnalysisController(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo, new StatisticAnalysisView(statisticAnalysisInfo));
	}
	
}
