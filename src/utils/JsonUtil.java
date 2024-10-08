package utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.BirthRecord;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

public class JsonUtil {

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
