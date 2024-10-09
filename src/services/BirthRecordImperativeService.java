package services;

import models.BirthRecord;
import types.BirthCounts;
import utils.JsonUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BirthRecordImperativeService {

	private final List<BirthRecord> records;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public BirthRecordImperativeService(String filePath) {
		this.records = JsonUtil.readJsonFile(filePath);
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
		}).collect(Collectors.groupingBy(BirthRecord::wohnviertel_name, Collectors.summingInt(record -> 1)));
	}

	public long countMultipleBirths(String startDateString, String endDateString, int numberOfChildren) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		return records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);

			return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate))
					&& record.anzahl_kinder() != null && record.anzahl_kinder() == numberOfChildren;
		}).count();
	}

	public Map<String, Double> getAverageChildrenByNationality(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		return records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);

			return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
		}).collect(Collectors.groupingBy(
				BirthRecord::name_citizenship_bfs,
				Collectors.averagingDouble(record -> record.anzahl_kinder() != null ? record.anzahl_kinder() : 1)
		));
	}

	public Map<String, Long> getBirthsByDay(String day) {
		if (day.equalsIgnoreCase("alle")) {
			return records.stream()
					.collect(Collectors.groupingBy(BirthRecord::wochentag, Collectors.counting()));
		} else {
			Map<String, Long> result = new HashMap<>();
			long count = records.stream()
					.filter(record -> record.wochentag().equalsIgnoreCase(day))
					.count();
			result.put(day, count);
			return result;
		}
	}
}
