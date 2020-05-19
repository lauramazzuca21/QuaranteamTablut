package domain;

import com.google.gson.JsonObject;

public interface JsonSerializable {

	public String toJson();
	public void fromJson(String jsonString);
	public void fromJson(JsonObject jsonObj);

}
