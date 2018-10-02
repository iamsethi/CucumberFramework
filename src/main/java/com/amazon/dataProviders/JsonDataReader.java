package com.amazon.dataProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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

	public static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	private static Logger Log = ILog.getLogger(ConfigFileReader.class);

	public static String getLocator(String locator) throws IOException {
		return JsonPath.read(new File(dataFile), "$." + locator);
	}

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException {
		// System.out.println(getLocator("locators.homepage.username.xpath")); use
		// OR.json in this case
		JSONParser jsonParser = new JSONParser();
		try (FileReader reader = new FileReader(dataFile)) {
			JSONObject coreData = (JSONObject) jsonParser.parse(reader);
			JSONArray environments = (JSONArray) coreData.get("Environments");
			JSONArray commondata = (JSONArray) coreData.get("commondata");
			commondata.forEach(pages -> getSubContainerPages((JSONObject) pages));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

	private static void getSubContainerPages(JSONObject page) {
		System.out.println(page); // page is UWSOrderSummaryEditPage with OrderSummary and CancelOrder 1st Loop
									// EricssonMetroEthernetPage with UwsOrderDetails and DirectOrder 2nd loop
		Iterator<?> container = page.keySet().iterator();
		while (container.hasNext()) {
			Object key = container.next();// key is UWSOrderSummaryEditPage 1st Loop
											// EricssonMetroEthernetPage 2nd Loop
			System.out.println(key);
			JSONArray subContainerPages = (JSONArray) page.get(key);
			System.out.println(subContainerPages); // subcontainer page is OrderSummary and CancelOrder order 1st Loop
													// subcontainer page is UwsOrderDetails and DirectOrder 1st Loop
			getContainerFields(subContainerPages);
		}
	}

	private static void getContainerFields(JSONArray page) {
		Iterator<?> iterator = page.iterator();
		while (iterator.hasNext()) {
			JSONObject subPages = (JSONObject) iterator.next();
			for (Object subContainer : subPages.keySet()) {
				String keyStr = (String) subContainer;
				Object keyvalue = subPages.get(keyStr);
				System.out.println("key: " + keyStr + " value: " + keyvalue);
				if (keyvalue instanceof JSONObject) {
					// System.out.println(((JSONObject) keyvalue).get("tbx_county"));
				}
			}
		}

	}
}