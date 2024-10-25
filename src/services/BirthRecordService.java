package services;

import types.BirthCounts;

import java.util.Map;

/**
 * Interface für den BirthRecordService. Dieser wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
 * @author levin-fankhauser
 * @version 0.0
 */
public interface BirthRecordService {

	/**
	 * Gibt die Anzahl der Geburten in einem angegebenen Datumsbereich zurück, aufgeteilt nach Geschlecht.
	 * Diese Methode wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
	 *
	 * @param startDateString Startdatum im Format "yyyy-MM-dd"
	 * @param endDateString Enddatum im Format "yyyy-MM-dd"
	 * @return BirthCounts Objekt mit den gezählten männlichen, weiblichen und gesamten Geburten im angegebenen Zeitraum
	 */
	BirthCounts getBirthCountsByDateRange(String startDateString, String endDateString);

	/**
	 * Gibt eine Map zurück, die die Anzahl der Geburten pro Wohnviertel innerhalb eines Datumsbereichs enthält.
	 * Diese Methode wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
	 *
	 * @param startDateString Startdatum im Format "yyyy-MM-dd"
	 * @param endDateString Enddatum im Format "yyyy-MM-dd"
	 * @return Map mit dem Wohnviertel als Schlüssel und der Anzahl der Geburten als Wert
	 */
	Map<String, Integer> getBirthsByNeighbourhoodByDateRange(String startDateString, String endDateString);

	/**
	 * Zählt die Anzahl der Mehrlingsgeburten (Zwillinge, Drillinge usw.) innerhalb eines angegebenen Datumsbereichs.
	 * Diese Methode wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
	 *
	 * @param startDateString Startdatum im Format "yyyy-MM-dd"
	 * @param endDateString Enddatum im Format "yyyy-MM-dd"
	 * @param numberOfChildren Anzahl der Kinder bei der Geburt
	 * @return Anzahl der Mehrlingsgeburten
	 */
	long countMultipleBirths(String startDateString, String endDateString, int numberOfChildren);

	/**
	 * Gibt die durchschnittliche Anzahl der Kinder pro Geburt, gruppiert nach Nationalität, innerhalb eines Datumsbereichs zurück.
	 * Diese Methode wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
	 *
	 * @param startDateString Startdatum im Format "yyyy-MM-dd"
	 * @param endDateString Enddatum im Format "yyyy-MM-dd"
	 * @return Map mit der Nationalität als Schlüssel und dem Durchschnitt der Kinderzahl als Wert
	 */
	Map<String, Double> getAverageChildrenByNationality(String startDateString, String endDateString);

	/**
	 * Gibt die Anzahl der Geburten an einem bestimmten Wochentag oder an allen Wochentagen zurück.
	 * 	 * Diese Methode wird einmal imperativ (V 1.0) und einmal funktional (V 2.0) implementiert.
	 *
	 * @param day Name des Wochentages (z.B. "Mo", "Di") oder "alle" für die Geburten an allen Wochentagen
	 * @return Map mit dem Wochentag als Schlüssel und der Anzahl der Geburten als Wert
	 */
	Map<String, Long> getBirthsByDay(String day);
}