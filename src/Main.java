import services.BirthRecordService;

import java.util.List;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		BirthRecordService service = new BirthRecordService("data.json");

		List<?> filteredRecords = service.getAll();
		filteredRecords.forEach(System.out::println);
	}
}
