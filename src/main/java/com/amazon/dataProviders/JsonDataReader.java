package com.amazon.dataProviders;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.amazon.managers.FileReaderManager;

/**
 * @author Ketan Sethi
 *
 *         TestNG JSON DataProvider Utility Class
 *
 */
public class JsonDataReader {
	public static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();

	/**
	 * fetchData method to retrieve test data for specified method
	 *
	 * @param method
	 * @return Object[][]
	 * @throws Exception
	 */
	public JSONObject fetchData(String dataTarget) throws Exception {
		JSONArray testData = (JSONArray) extractData_JSON(dataFile + dataTarget + ".json")
				.get("sign_in_on_application");
		List<JSONObject> testDataList = new ArrayList<JSONObject>();
		for (int i = 0; i < testData.size(); i++) {
			testDataList.add((JSONObject) testData.get(i));
		}
		return testDataList.get(0);
	}

	/**
	 * extractData_JSON method to get JSON data from file
	 *
	 * @param file
	 * @return JSONObject
	 * @throws Exception
	 */
	public static JSONObject extractData_JSON(String file) throws Exception {
		FileReader reader = new FileReader(file);
		JSONParser jsonParser = new JSONParser();

		return (JSONObject) jsonParser.parse(reader);
	}

}