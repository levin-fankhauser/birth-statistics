import models.BirthRecord;
import services.BirthRecordService;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		BirthRecordService service = new BirthRecordService("data.json");

		List<BirthRecord> filteredRecords = service.getAll();
		List<String> listItem = new ArrayList<>();
		filteredRecords.forEach(item -> {
			if (!listItem.contains(item.name_citizenship_bfs())) {
				listItem.add(item.name_citizenship_bfs());
			}
		});
		listItem.forEach(System.out::println);
		System.out.println("\nAmount of possibilities: " + listItem.size());
	}
}
