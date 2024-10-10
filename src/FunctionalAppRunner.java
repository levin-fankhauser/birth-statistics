import services.BirthRecordService;
import services.impl.BirthRecordServiceFunctionalImpl;

public class FunctionalAppRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceFunctionalImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}
}