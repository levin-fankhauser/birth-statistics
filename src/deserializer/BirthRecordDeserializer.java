package deserializers;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import properties.enums.District;

import java.lang.reflect.Type;

public class NeighbourhoodDeserializer implements JsonDeserializer<BirthRecord> {
    @Override
    public BirthRecord deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        return new BirthRecord(
            jsonObject.get("birth_date"),
            jsonObject.get("year"),
            jsonObject.get("month"),
            jsonObject.get("weekInYear"),
            jsonObject.get("startOfWeekDate"),
            jsonObject.get("dayOfYear"),
            jsonObject.get("weekday"),
            jsonObject.get("gender"),
            jsonObject.get("anzahl_kinder"),
            jsonObject.get("nationality"),
            jsonObject.get("neighbourhood"),
            jsonObject.get("id"),
            jsonObject.get("neighbourhood_id"),
            jsonObject.get("genderValue"),
            jsonObject.get("genderName"),
            jsonObject.get("citizenshipValue"),
            jsonObject.get("citizenshipName"));
        );
    }
}
