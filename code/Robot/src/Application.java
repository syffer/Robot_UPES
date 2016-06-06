import model.GeneralModel;
import controller.GeneralController;


public class Application {
	public static void main(String[] args) {
		
		GeneralModel model = new GeneralModel();
		new GeneralController(model);
		
	}
}
