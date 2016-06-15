package controller;

import model.StatisticAnalysisInfo;
import view.StatisticAnalysisView;

public class StatisticAnalysisController extends InternalController {
	
	public StatisticAnalysisController(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo, new StatisticAnalysisView(statisticAnalysisInfo));
	}
	
}
