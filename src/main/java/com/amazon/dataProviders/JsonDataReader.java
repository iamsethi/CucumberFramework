package com.amazon.dataProviders;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.WrapsElement;

import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;
import com.amazon.pageObjects.CheckoutPage;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class JsonDataReader {
	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	static Gson gson = new Gson();
	private static JsonObject jsonObject;
	private static JsonObject commondata;
	private static JsonObject environments;

	public static void registerEnvironment(String envn) {
		try (FileReader reader = new FileReader(dataFile)) {
			jsonObject = gson.fromJson(new FileReader(dataFile), JsonObject.class);
			environments = jsonObject.getAsJsonObject("Environments");
			System.out.println(environments.getAsJsonArray(envn));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void initializeJSON() {
		commondata = jsonObject.getAsJsonObject("commondata");
	}

	public static void fillAllFields(String pageName) {
		JsonElement subContainers = getAllSubContainers(pageName);
		Set<Map.Entry<String, JsonElement>> s = subContainers.getAsJsonObject().entrySet();
		Iterator<Map.Entry<String, JsonElement>> i = s.iterator();
		while (i.hasNext()) {
			Map.Entry<String, JsonElement> m = i.next();
			JsonDataReader.fillCustomFields(pageName, m.getKey());
		}

	}

	public static void fillCustomFields(String pageName, String dataTarget) {
		JsonElement page = commondata.get(pageName);
		JsonElement dataContainer = getDataContainer(page, dataTarget);
		fillFields(page, dataContainer);
	}

	public static JsonElement getDataContainer(JsonElement page, String dataTarget) {
		JsonObject dataContainer = page.getAsJsonObject();
		return dataContainer.get(dataTarget);
	}

	public static void fillFields(JsonElement page, JsonElement dataContainer) {
		LinkedHashMap<String, String> containerFields = getContainerFields(dataContainer);
		for (Map.Entry<String, String> entry : containerFields.entrySet()) {
			String locator = entry.getKey();
			String value = entry.getValue();
			try {
				Class aClass = CheckoutPage.class;
				Field field = aClass.getField(locator);
				Method sumInstanceMethod = PageObjectManager.class.getMethod("getCheckoutPage");
				PageObjectManager operationsInstance = new PageObjectManager(
						WebDriverManager.getInstance().getDriver());
				Object o = sumInstanceMethod.invoke(operationsInstance);
				field.get(o);
				WebElement element = ((WrapsElement) field.get(o)).getWrappedElement();
				element.sendKeys("Test");
				if (locator.startsWith("tbx_")) {
					// Log.info("Filling " + locator + " with value : " + value + " ");
					element.clear();
					element.sendKeys(value);
				} else if (locator.startsWith("rbn_")) {
					// Log.info("Filling " + locator + " with value : " + value + " ");
				} else if (locator.startsWith("ddl_")) {
					// Log.info("Filling " + locator + " with value : " + value + " ");
				} else if (locator.startsWith("btn_")) {
					// Log.info("Filling " + locator + " with value : " + value + " ");
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

	public static LinkedHashMap<String, String> getContainerFields(JsonElement dataContainer) {
		LinkedHashMap<String, String> fields = new LinkedHashMap<String, String>();
		JsonArray i = dataContainer.getAsJsonArray();
		for (JsonElement field : i) {
			String locator = field.getAsString().split(": ")[0]; // tbx_name
			String value = field.getAsString().split(": ")[1]; // Brian
			fields.put(locator, value);
		}
		return fields;
	}

	public static JsonElement getAllSubContainers(String pageName) {
		JsonElement page = commondata.get(pageName);
		JsonObject subContainers = page.getAsJsonObject();
		return subContainers;
	}

}