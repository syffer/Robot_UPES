package mvc.controller;

import mvc.model.StatisticAnalysisInfo;
import mvc.view.StatisticAnalysisView;

public class StatisticAnalysisController extends InternalController {
	
	public StatisticAnalysisController(StatisticAnalysisInfo statisticAnalysisInfo) {
		super(statisticAnalysisInfo, new StatisticAnalysisView(statisticAnalysisInfo));
	}
	
}
