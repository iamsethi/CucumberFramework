package com.amazon.dataProviders;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebDriver;

import com.amazon.cucumber.TestContext;
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
	private static WebDriver driver;

	public static String getLocator(String locator) throws IOException {
		return JsonPath.read(new File(dataFile), "$." + locator);
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
	public static void get_data_for_page(String pageName) {
		Iterator<?> iterator = commondata.iterator();
		while (iterator.hasNext()) {
			HashMap<String, JSONArray> subPages = (JSONObject) iterator.next();
			getPageContainer(subPages, pageName);
		}

	}

	private static JSONArray getPageContainer(HashMap<String, JSONArray> page, String pageName) {
		if (page.containsKey(pageName))
			return getSubContainers(page.get(pageName), pageName);
		else
			return null;

	}

	@SuppressWarnings("unchecked")
	private static JSONArray getSubContainers(JSONArray dataContainers, String pageName) {
		Iterator<?> iterator = dataContainers.iterator();
		HashMap<String, JSONObject> newHM = new HashMap<>();
		while (iterator.hasNext()) {
			HashMap<String, JSONObject> subPages = (JSONObject) iterator.next();
			newHM.putAll(subPages);
			for (Object subContainer : subPages.keySet()) {
				String keyStr = (String) subContainer;
				HashMap<String, String> dataTarget = subPages.get(keyStr);
				dataTarget.forEach((locator, value) -> fillfields(locator, value, pageName));
			}
		}
		return dataContainers;
	}

	public static void fillfields(String locator, String value, String pageName) {
		try {
			if (locator.startsWith("tbx_")) {
				// testContext.getPageObjectManager().getSignInPage().txtbx_Email.sendKeys(value);
				// Method sumInstanceMethod =
				// testContext.getPageObjectManager().getClass().getMethod("get" + pageName);
				// Object o = sumInstanceMethod.invoke(testContext.getPageObjectManager());
				System.out.println(locator);
			} else if (locator.startsWith("rbn_")) {
				System.out.println(locator);

			} else if (locator.startsWith("ddl_")) {
				System.out.println(locator);

			}
			// } catch (NoSuchMethodException e) {
			// e.printStackTrace();
			// } catch (IllegalAccessException e) {
			// e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// test.log(LogStatus.INFO, "Clicking on : " + locator);

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