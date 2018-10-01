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

import com.amazon.managers.FileReaderManager;
import com.jayway.jsonpath.JsonPath;

/**
 * @author Ketan Sethi
 *
 *         JSON DataProvider Utility Class
 *
 */
public class JsonDataReader {

	public static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();

	public static String getLocator(String locator) throws IOException {
		return JsonPath.read(new File(dataFile), "$." + locator);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// System.out.println(getLocator("locators.homepage.username.xpath"));  use OR.json in this case
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