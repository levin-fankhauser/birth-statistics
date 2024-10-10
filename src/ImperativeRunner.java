import services.BirthRecordService;
import services.BirthRecordServiceImperativeImpl;

public class ImperativeRunner {

	public static void main(String[] args) {
		BirthRecordService birthRecordService = new BirthRecordServiceImperativeImpl("data.json");
		App app = new App(birthRecordService);
		app.start();
	}

}
