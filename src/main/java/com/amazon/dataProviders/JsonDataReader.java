package com.amazon.dataProviders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import com.amazon.interfaces.ILog;
import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;

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
	public static HashMap<String, JSONArray> getContainer(String pageName) {
		boolean isPage = false;
		HashMap<String, JSONArray> page = new HashMap<String, JSONArray>();
		Iterator<?> iterator = commondata.iterator();
		while (iterator.hasNext()) {
			HashMap<String, JSONArray> pages = (JSONObject) iterator.next();
			if (pages.containsKey(pageName)) {
				page = getSubContainers(pages.get(pageName), pageName);
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
	private static HashMap<String, JSONArray> getSubContainers(JSONArray pageContainer, String pageName) {
		Iterator<?> iterator = pageContainer.iterator();
		HashMap<String, JSONArray> subContainers = new HashMap<>();
		while (iterator.hasNext()) {
			HashMap<String, JSONArray> subPages = (JSONObject) iterator.next();
			subContainers.putAll(subPages);
			for (Object subContainer : subPages.keySet()) {
				String dataTarget = (String) subContainer;
				JSONArray containerFields = subPages.get(dataTarget);
				fillfields(dataTarget, containerFields, pageName);
			}
		}
		return subContainers;
	}

	@SuppressWarnings("unchecked")
	public static void fillfields(String dataTarget, JSONArray containerFields, String pageName) {
		Iterator<String> i = containerFields.iterator();
		while (i.hasNext()) {
			String fields = i.next();
			String locator = fields.split(": ")[0];
			String value = fields.split(": ")[1];
			try {
				Method sumInstanceMethod = PageObjectManager.class.getMethod("get" + pageName);
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