package domain;

import com.google.gson.JsonObject;

public interface JsonSerializable {

	public String toJson();
	public JsonObject toJsonObject();
	public void fromJson(String jsonString);
	public void fromJson(JsonObject jsonObj);

}
