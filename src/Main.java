import models.BirthRecord;
import services.BirthRecordService;

import java.util.List;

public class Main {

	public static void main(String[] args) {
		BirthRecordService service = new BirthRecordService("data.json");

		List<BirthRecord> filteredRecords = service.getAll();
		filteredRecords.forEach(System.out::println);
	}
}
