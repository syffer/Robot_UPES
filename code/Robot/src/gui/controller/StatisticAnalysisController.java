package gui.controller;

import gui.model.StatisticAnalysisInfo;
import gui.view.StatisticAnalysisView;

public class StatisticAnalysisController extends InternalController {
	
	public StatisticAnalysisController(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo, new StatisticAnalysisView(statisticAnalysisInfo));
	}
	
}
