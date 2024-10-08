import models.BirthRecord;
import services.BirthRecordService;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		BirthRecordService service = new BirthRecordService("data.json");

		List<BirthRecord> filteredRecords = service.getAll();
		List<String> listItems = new ArrayList<>();
		filteredRecords.forEach(item -> {
			if (!listItems.contains(item.wohnviertel_name())) {
				listItems.add(item.wohnviertel_name());
			}
		});
		listItems.forEach(System.out::println);
		System.out.println("\nAmount of possibilities: " + listItems.size());
	}
}
