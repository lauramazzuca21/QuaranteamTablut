package utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.json.JSONObject;
import org.json.JSONTokener;


public class JsonUtils {

	public static JSONObject getObjectFromFile(String fileName) {
		InputStream fileStream = null;
		try {
			fileStream = new FileInputStream(fileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		JSONTokener tokener = new JSONTokener(fileStream);
        JSONObject object = new JSONObject(tokener);
 
        return object;
	}
}
