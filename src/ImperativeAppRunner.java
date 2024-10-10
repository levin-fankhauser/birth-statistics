import services.BirthRecordService;
import services.impl.BirthRecordServiceImperativeImpl;

public class ImperativeAppRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceImperativeImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}

}
