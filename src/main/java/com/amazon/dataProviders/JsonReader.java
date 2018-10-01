package com.amazon.dataProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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

		try {
			JSONObject rootJSON = (JSONObject) new JSONParser().parse(new FileReader(
					"C:\\Users\\ketan.sethi\\git\\cucumber-selenium\\src\\test\\resources\\testDataResources\\sfa.json"));
			JSONArray commonData = (JSONArray) rootJSON.get("CommonData");
			for (Object dataList : commonData.toArray()) {
				JSONObject data = (JSONObject) dataList;
				JSONArray pages = (JSONArray) data.get("EricssonMetroEthernetPage");
				for (Object issueObj : pages.toArray()) {
					JSONObject issue = (JSONObject) issueObj;
					System.out.println(issue);
				}
			}
		} catch (Exception e) {
			// do smth
			e.printStackTrace();
		}

		System.out.println(new JsonReader(
				"C:\\Users\\ketan.sethi\\git\\cucumber-selenium\\src\\test\\resources\\testDataResources\\OR.json")
						.getLocator("locators.homepage.username.xpath"));

		// JSON parser object to parse read file
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(
				"C:\\Users\\ketan.sethi\\git\\cucumber-selenium\\src\\test\\resources\\testDataResources\\employee.json")) {
			// Read JSON file
			Object obj = jsonParser.parse(reader);

			JSONArray employeeList = (JSONArray) obj;
			// System.out.println(employeeList);

			// Iterate over employee array
			employeeList.forEach(emp -> parseEmployeeObject((JSONObject) emp));

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void parseEmployeeObject(JSONObject employee) {
		// Get employee object within list
		JSONObject employeeObject = (JSONObject) employee.get("employee");

		// Get employee first name
		String firstName = (String) employeeObject.get("firstName");
		System.out.println(firstName);

		// Get employee last name
		String lastName = (String) employeeObject.get("lastName");
		System.out.println(lastName);

		// Get employee website name
		String website = (String) employeeObject.get("website");
		System.out.println(website);
	}
}