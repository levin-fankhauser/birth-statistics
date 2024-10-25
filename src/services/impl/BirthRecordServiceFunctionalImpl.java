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

/**
 * Funktionale Implementierung des BirthRecordService, die Streams verwendet, um Geburtsdatensätze zu analysieren.
 *
 * @author levin-fankhauser
 * @author ToppTobi
 * @version 2.0
 */
public class BirthRecordServiceFunctionalImpl implements BirthRecordService {

	private final List<BirthRecord> records;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	/**
	 * Konstruktor, der die JSON-Datei einliest und die Geburtsdaten in die records-Liste initialisiert.
	 *
	 * @param filePath Pfad zur JSON-Datei mit den Geburtsdaten
	 */
	public BirthRecordServiceFunctionalImpl(String filePath) {
		this.records = JsonUtil.readJsonFile(filePath);
	}

	@Override
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

	@Override
	public Map<String, Integer> getBirthsByNeighbourhoodByDateRange(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		// Filtern und Gruppieren nach Wohnviertel
		return records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
		}).collect(Collectors.groupingBy(BirthRecord::wohnviertel_name, Collectors.summingInt(record -> 1)));
	}

	@Override
	public long countMultipleBirths(String startDateString, String endDateString, int numberOfChildren) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		// Filtern der Datensätze nach Anzahl der Kinder bei der Geburt
		return records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			return (date.isEqual(startDate) || date.isAfter(startDate))
					&& (date.isEqual(endDate) || date.isBefore(endDate))
					&& record.anzahl_kinder() != null
					&& record.anzahl_kinder() == numberOfChildren;
		}).count();
	}

	@Override
	public Map<String, Double> getAverageChildrenByNationality(String startDateString, String endDateString) {
		LocalDate startDate = LocalDate.parse(startDateString, formatter);
		LocalDate endDate = LocalDate.parse(endDateString, formatter);

		// Filtern und Berechnen des Durchschnitts der Kinder pro Nationalität
		return records.stream().filter(record -> {
			LocalDate date = LocalDate.parse(record.geburts_datum(), formatter);
			return (date.isEqual(startDate) || date.isAfter(startDate)) && (date.isEqual(endDate) || date.isBefore(endDate));
		}).collect(Collectors.groupingBy(
				BirthRecord::name_citizenship_bfs,
				Collectors.averagingDouble(record -> record.anzahl_kinder() != null ? record.anzahl_kinder() : 1)));
	}

	@Override
	public Map<String, Long> getBirthsByDay(String day) {
		if (day.equalsIgnoreCase("alle")) {
			// Gruppieren nach Wochentag und Zählen der Geburten
			return records.stream().collect(Collectors.groupingBy(BirthRecord::wochentag, Collectors.counting()));
		} else {
			Map<String, Long> result = new HashMap<>();
			long count = records.stream().filter(record -> record.wochentag().equalsIgnoreCase(day)).count();
			result.put(day, count);
			return result;
		}
	}
}
