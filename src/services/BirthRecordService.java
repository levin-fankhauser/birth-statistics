package services;

import models.BirthRecord;
import types.BirthCounts;
import utils.JsonUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BirthRecordService {

	private final List<BirthRecord> records;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public BirthRecordService(String filePath) {
		this.records = JsonUtil.readJsonFile(filePath);
	}

	public List<BirthRecord> getAll() {
		return records;
	}

	public BirthCounts getBirthCountsByDateRange(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		List<BirthRecord> filteredRecords = records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);

			return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
		}).toList();

		long maleCount = filteredRecords.stream().filter(record -> record.value_gender_bfs() == 1).count();

		long femaleCount = filteredRecords.stream().filter(record -> record.value_gender_bfs() == 2).count();

		long totalCount = filteredRecords.size();

		return new BirthCounts(maleCount, femaleCount, totalCount);
	}

	public Map<String, Integer> getBirthsByNeighbourhoodByDateRange(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		return records.stream().filter(record -> {
					LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);

					return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
				})
				.collect(Collectors.groupingBy(
						BirthRecord::wohnviertel_name,
						Collectors.summingInt(record -> 1)
				));
	}
}
