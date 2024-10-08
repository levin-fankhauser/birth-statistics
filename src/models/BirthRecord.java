package models;

public record BirthRecord(
		String geburts_datum,
		String jahr,
		int monat,
		int woche_in_jahr,
		String datum_wochenstart_geburtsdatum,
		int tag_in_jahr,
		String wochentag,
		String geschlecht,
		Integer anzahl_kinder,
		String nationalitaet,
		String wohnviertel_name,
		String id,
		int wohnviertel_id,
		int value_gender_bfs,
		String name_gender_bfs,
		int value_citizenship_bfs,
		String name_citizenship_bfs) {

}
