package deserializer;

import com.google.gson.*;
import models.BirthRecord;

import java.lang.reflect.Type;

public class BirthRecordDeserializer implements JsonDeserializer<BirthRecord> {

	@Override
	public BirthRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
		JsonObject jsonObject = json.getAsJsonObject();

		return new BirthRecord(
				jsonObject.get("birth_date").getAsString(),
				jsonObject.get("year").getAsString(),
				jsonObject.get("month").getAsInt(),
				jsonObject.get("weekInYear").getAsInt(),
				jsonObject.get("startOfWeekDate").getAsString(),
				jsonObject.get("dayOfYear").getAsInt(),
				jsonObject.get("weekday").getAsString(),
				jsonObject.get("gender").getAsString(),
				jsonObject.get("anzahl_kinder").getAsInt(),
				jsonObject.get("nationality").getAsString(),
				jsonObject.get("neighbourhood").getAsString(),
				jsonObject.get("id").getAsString(),
				jsonObject.get("neighbourhood_id").getAsInt(),
				jsonObject.get("genderValue").getAsInt(),
				jsonObject.get("genderName").getAsString(),
				jsonObject.get("citizenshipValue").getAsInt(),
				jsonObject.get("citizenshipName").getAsString());
	}
}
