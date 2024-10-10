package services.impl;

import models.BirthRecord;
import services.BirthRecordService;
import types.BirthCounts;
import utils.JsonUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BirthRecordServiceFunctionalImpl implements BirthRecordService {

	private final List<BirthRecord> records;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public BirthRecordServiceFunctionalImpl(String filePath) {
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

			return (date.isEqual(startDate) || date.isAfter(startDate))
					&& (date.isEqual(endDate) || date.isBefore(endDate))
					&& record.anzahl_kinder() != null
					&& record.anzahl_kinder() == numberOfChildren;
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
				Collectors.averagingDouble(record -> record.anzahl_kinder() != null ? record.anzahl_kinder() : 1)));
	}

	public Map<String, Long> getBirthsByDay(String day) {
		if (day.equalsIgnoreCase("alle")) {
			return records.stream().collect(Collectors.groupingBy(BirthRecord::wochentag, Collectors.counting()));
		} else {
			return records.stream().reduce(new HashMap<>(), (map, record) -> {
				map.put(record.wochentag(), map.getOrDefault(record.wochentag(), 0L) + 1);
				return map;
			}, (map1, map2) -> {
				map2.forEach((key, value) -> map1.merge(key, value, Long::sum));
				return map1;
			});

		}
	}
}
