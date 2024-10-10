package services;

import types.BirthCounts;

import java.util.Map;

public interface BirthRecordService {
	BirthCounts getBirthCountsByDateRange(String startDateString, String endDateString);
	Map<String, Integer> getBirthsByNeighbourhoodByDateRange(String startDateString, String endDateString);
	long countMultipleBirths(String startDateString, String endDateString, int numberOfChildren);
	Map<String, Double> getAverageChildrenByNationality(String startDateString, String endDateString);
	Map<String, Long> getBirthsByDay(String day);
}
