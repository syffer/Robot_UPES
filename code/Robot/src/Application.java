import mvc.controller.GeneralController;
import mvc.model.GeneralModel;


public class Application {
	public static void main(String[] args) {
		
		GeneralModel model = new GeneralModel();
		new GeneralController(model);
		
	}
}
