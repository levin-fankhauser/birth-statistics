package services;

import models.BirthRecord;
import utils.JsonUtil;

import java.util.List;

public class BirthRecordService {

	private final List<BirthRecord> records;

	public BirthRecordService(String filePath) {
		this.records = JsonUtil.readJsonFile(filePath);
	}

	public List<BirthRecord> getAll() {
		return records;
	}

	// Methoden siehe Projektantrag
}
