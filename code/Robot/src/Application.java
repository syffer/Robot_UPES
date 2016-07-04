import gui.controller.GeneralController;
import gui.model.GeneralModel;


public class Application {
	public static void main(String[] args) {
		
		GeneralModel model = new GeneralModel();
		new GeneralController(model);
		
	}
}
