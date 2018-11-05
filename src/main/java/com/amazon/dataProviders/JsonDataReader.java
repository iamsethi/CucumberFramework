package com.amazon.dataProviders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import com.amazon.cucumber.TestContext;
import com.amazon.interfaces.ILog;
import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;
import com.amazon.pageObjects.CheckoutPage;

/**
 * @author Ketan Sethi
 *
 *         JSON DataProvider Utility Class
 *
 */
public class JsonDataReader {


	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	private static Logger Log = ILog.getLogger(JsonDataReader.class);
	private static JSONObject coreData;
	private static JSONArray environments;
	private static JSONArray commondata;
	private static JSONParser jsonParser = new JSONParser();
	private static PageObjectManager pom = new PageObjectManager(WebDriverManager.getInstance().getDriver());

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
	public HashMap<String, LinkedHashMap<String, String>> getContainer(String pageName) {
		boolean isPage = false;
		HashMap<String, LinkedHashMap<String, String>> page = new HashMap<String, LinkedHashMap<String, String>>();
		Iterator<?> iterator = commondata.iterator();
		while (iterator.hasNext()) {
			HashMap<String, JSONArray> pages = (JSONObject) iterator.next();
			if (pages.containsKey(pageName)) {
				page = getDataTargets(pages.get(pageName), pageName);
				isPage = true;
				break;
			}
		}
		if (isPage == true)
			return page;
		else
			throw new RuntimeException("Page : " + pageName + " doesn't exist in sfa.json");

	}

	@SuppressWarnings("unchecked")
	private static HashMap<String, LinkedHashMap<String, String>> getDataTargets(JSONArray pageContainer,
			String pageName) {
		HashMap<String, LinkedHashMap<String, String>> container = new HashMap<String, LinkedHashMap<String, String>>();
		Iterator<?> iterator = pageContainer.iterator();
		while (iterator.hasNext()) {
			HashMap<String, JSONArray> subPages = (JSONObject) iterator.next();
			for (Object subContainer : subPages.keySet()) {
				String dataTarget = (String) subContainer;
				JSONArray containerFields = subPages.get(dataTarget);
				container.put(dataTarget, getContainerFields(containerFields)); // key-cancelorder value is
																				// (tbx_name,brian)
				// fillAllFields(pageName, dataTarget, getContainerFields(containerFields));
			}
		}
		return container;
	}

	@SuppressWarnings("unchecked")
	public static LinkedHashMap<String, String> getContainerFields(JSONArray containerFields) {
		LinkedHashMap<String, String> kv = new LinkedHashMap<String, String>();
		Iterator<String> i = containerFields.iterator();
		while (i.hasNext()) {
			String fields = i.next();
			String locator = fields.split(": ")[0]; // tbx_name
			String value = fields.split(": ")[1]; // Brian
			kv.put(locator, value);
		}
		return kv;

	}

	public void fillFields(String pageName, String dataTarget, HashMap<String, String> containerFields) {
		for (Map.Entry<String, String> entry : containerFields.entrySet()) {
			String locator = entry.getKey();
			String value = entry.getValue();
			try {
				Method sumInstanceMethod = PageObjectManager.class.getMethod("get" + pageName);
				//TestContext.class.getMethod("getPageObjectManager"); //testContext.getPageObjectManager().getCheckoutPage();
				Object o = sumInstanceMethod.invoke(pom);
				Field field = o.getClass().getField(locator);
				WebElement element = ((WrapsElement) field.get(o)).getWrappedElement();
				if (locator.startsWith("tbx_")) {
					Log.info("Filling " + locator + " with value : " + value + " ");
					element.sendKeys(value);
				} else if (locator.startsWith("rbn_")) {
					Log.info("Filling " + locator + " with value : " + value + " ");
				} else if (locator.startsWith("ddl_")) {
					Log.info("Filling " + locator + " with value : " + value + " ");
				} else if (locator.startsWith("btn_")) {
					Log.info("Filling " + locator + " with value : " + value + " ");
					element.click();
				}
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
		}
	}
}