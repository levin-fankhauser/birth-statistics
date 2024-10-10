import services.BirthRecordService;
import services.BirthRecordServiceFunctionalImpl;

public class FunctionalRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceFunctionalImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}
}