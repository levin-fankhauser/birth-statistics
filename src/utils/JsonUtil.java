package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.BirthRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Util-Klasse für JSON-Verarbeitungsfunktionen.
 * <p>
 * Die Klasse dient zum Verarbeiten von JSON Daten, speziell für den Datensatz {@link BirthRecord}.
 *
 * @author levin-fankhauser
 * @version 0.0
 */
public class JsonUtil {

	/**
	 * Liest eine JSON-Datei ein und gibt eine Liste von {@link BirthRecord} Objekten zurück.
	 * <p>
	 * Diese Methode verwendet die GSON-Bibliothek, um die Daten aus der angegebenen Datei
	 * in ein Array von {@link BirthRecord}-Objekten zu deserialisieren und filtert Nullwerte heraus.
	 *
	 * @param filePath Der Pfad zur JSON-Datei, die eingelesen werden soll.
	 * @return Eine Liste von {@link BirthRecord}-Objekten, oder `null`, falls ein IO-Fehler auftritt.
	 */
	public static List<BirthRecord> readJsonFile(String filePath) {
		try (FileReader reader = new FileReader(filePath)) {
			Gson gson = new GsonBuilder().create();

			BirthRecord[] recordsArray = gson.fromJson(reader, BirthRecord[].class);

			return Stream.of(recordsArray).filter(Objects::nonNull).toList();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
