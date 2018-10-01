package com.amazon.dataProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jayway.jsonpath.JsonPath;

public class JsonReader {

	private File jsonFile;
	public String fileName;

	public JsonReader(String fileName) {

		this.fileName = fileName;
	}

	public String getLocator(String locator) throws IOException {

		jsonFile = new File(fileName);
		return JsonPath.read(jsonFile, "$." + locator);

	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		System.out.println(new JsonReader(
				"C:\\Users\\ketan.sethi\\git\\cucumber-selenium\\src\\test\\resources\\testDataResources\\OR.json")
						.getLocator("locators.homepage.username.xpath"));
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(
				"C:\\Users\\ketan.sethi\\git\\cucumber-selenium\\src\\test\\resources\\testDataResources\\sfa.json")) {
			JSONObject coreData = (JSONObject) jsonParser.parse(reader);
			JSONArray commondata = (JSONArray) coreData.get("commondata");
			commondata.forEach(containerPages -> parsePageData((JSONObject) containerPages));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void parsePageData(JSONObject data) {
		Iterator<?> keys = data.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			JSONObject page = (JSONObject) data.get(key);

			// Get employee first name
			String firstName = (String) page.get("tbx_firstName");
			System.out.println(firstName);
			// Get employee last name
			String lastName = (String) page.get("tbx_lastName");
			System.out.println(lastName);
		}

	}
}