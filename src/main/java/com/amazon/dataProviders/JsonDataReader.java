package com.amazon.dataProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.amazon.interfaces.ILog;
import com.amazon.managers.FileReaderManager;
import com.jayway.jsonpath.JsonPath;

/**
 * @author Ketan Sethi
 *
 *         JSON DataProvider Utility Class
 *
 */
public class JsonDataReader {

	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	private static Logger Log = ILog.getLogger(ConfigFileReader.class);
	private static JSONObject coreData;
	private static JSONArray environments;
	private static JSONArray commondata;
	private static JSONParser jsonParser = new JSONParser();

	public static String getLocator(String locator) throws IOException {
		return JsonPath.read(new File(dataFile), "$." + locator);
	}

	public static void main(String args[]) {
		registerEnvironment();
		initializeJSON();
		getContainer("UWSOrderSummaryEditPage");
	}

	public static void registerEnvironment() {
		try (FileReader reader = new FileReader(dataFile)) {
			coreData = (JSONObject) jsonParser.parse(reader);
			environments = (JSONArray) coreData.get("Environments");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

	public static void initializeJSON() {
		try (FileReader reader = new FileReader(dataFile)) {
			commondata = (JSONArray) coreData.get("commondata");// JSONArray commondata has JSONObjects of String and
																// JSONArray
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unchecked")
	public static void getContainer(String pageName) {
		commondata.forEach(page -> getContainerPages((JSONObject) page, pageName)); // iterating commondata for
																					// JSONObjects of String and
																					// JSONArray;
	}

	private static void getContainerPages(HashMap<String, JSONArray> page, String pageName) {
		page.forEach((key, value) -> {
			if (key.equals(pageName))
				getSubContainerPages(value);
		});
	}

	private static void getSubContainerPages(JSONArray page) {
		Iterator<?> iterator = page.iterator();
		while (iterator.hasNext()) {
			JSONObject subPages = (JSONObject) iterator.next();
			for (Object subContainer : subPages.keySet()) {
				String keyStr = (String) subContainer;
				Object keyvalue = subPages.get(keyStr);
				System.out.println("key: " + keyStr);
				if (keyvalue instanceof JSONObject) {
					System.out.println("value: " + keyvalue);
					System.out.println(((JSONObject) keyvalue).get("tbx_county"));
				}
			}
		}
	}

	/*
	 * private static void helper(JSONObject jsonObjPage) {
	 * System.out.println(jsonObjPage); // page is UWSOrderSummaryEditPage with
	 * OrderSummary and CancelOrder 1st Loop // EricssonMetroEthernetPage with
	 * UwsOrderDetails and DirectOrder 2nd loop Iterator<?> container =
	 * jsonObjPage.keySet().iterator(); while (container.hasNext()) { Object key =
	 * container.next();// key is UWSOrderSummaryEditPage 1st Loop //
	 * EricssonMetroEthernetPage 2nd Loop System.out.println(key); JSONArray
	 * subContainerPages = (JSONArray) jsonObjPage.get(key);
	 * System.out.println(subContainerPages); // subcontainer page is OrderSummary
	 * and CancelOrder order 1st Loop // subcontainer page is UwsOrderDetails and
	 * DirectOrder 1st Loop getContainerFields(subContainerPages); } }
	 */

}