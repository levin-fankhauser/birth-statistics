// V 1.0

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

public class BirthRecordServiceImperativeImpl implements BirthRecordService {

	private final List<BirthRecord> records;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public BirthRecordServiceImperativeImpl(String filePath) {
		this.records = JsonUtil.readJsonFile(filePath);
	}

	public BirthCounts getBirthCountsByDateRange(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		List<BirthRecord> filteredRecords = new java.util.ArrayList<>();
		for (BirthRecord record : records) {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			if ((date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate))) {
				filteredRecords.add(record);
			}
		}

		long maleCount = 0;
		long femaleCount = 0;

		for (BirthRecord record : filteredRecords) {
			if (record.value_gender_bfs() == 1) {
				maleCount++;
			} else if (record.value_gender_bfs() == 2) {
				femaleCount++;
			}
		}

		long totalCount = filteredRecords.size();
		return new BirthCounts(maleCount, femaleCount, totalCount);
	}

	public Map<String, Integer> getBirthsByNeighbourhoodByDateRange(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		Map<String, Integer> birthsByNeighbourhood = new HashMap<>();
		for (BirthRecord record : records) {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			if ((date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate))) {
				String neighbourhood = record.wohnviertel_name();
				birthsByNeighbourhood.put(neighbourhood, birthsByNeighbourhood.getOrDefault(neighbourhood, 0) + 1);
			}
		}

		return birthsByNeighbourhood;
	}

	public long countMultipleBirths(String startDateString, String endDateString, int numberOfChildren) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		long count = 0;
		for (BirthRecord record : records) {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			if ((date.isEqual(startDate) || date.isAfter(startDate))
					&& (date.isEqual(endDate) || date.isBefore(endDate))
					&& record.anzahl_kinder() != null
					&& record.anzahl_kinder() == numberOfChildren) {
				count++;
			}
		}
		return count;
	}

	public Map<String, Double> getAverageChildrenByNationality(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		Map<String, Integer> totalChildrenByNationality = new HashMap<>();
		Map<String, Integer> countByNationality = new HashMap<>();

		for (BirthRecord record : records) {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			if ((date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate))) {
				String nationality = record.name_citizenship_bfs();
				int children = record.anzahl_kinder() != null ? record.anzahl_kinder() : 1;

				totalChildrenByNationality.put(nationality, totalChildrenByNationality.getOrDefault(nationality, 0) + children);
				countByNationality.put(nationality, countByNationality.getOrDefault(nationality, 0) + 1);
			}
		}

		Map<String, Double> averageChildrenByNationality = new HashMap<>();
		for (Map.Entry<String, Integer> entry : totalChildrenByNationality.entrySet()) {
			String nationality = entry.getKey();
			double average = (double) entry.getValue() / countByNationality.get(nationality);
			averageChildrenByNationality.put(nationality, average);
		}

		return averageChildrenByNationality;
	}

	public Map<String, Long> getBirthsByDay(String day) {
		Map<String, Long> birthsByDay = new HashMap<>();

		if (day.equalsIgnoreCase("alle")) {
			for (BirthRecord record : records) {
				String weekday = record.wochentag();
				birthsByDay.put(weekday, birthsByDay.getOrDefault(weekday, 0L) + 1);
			}
		} else {
			long count = 0;
			for (BirthRecord record : records) {
				if (record.wochentag().equalsIgnoreCase(day)) {
					count++;
				}
			}
			birthsByDay.put(day, count);
		}

		return birthsByDay;
	}
}
