package com.amazon.api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

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

public class Test {
	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	static Gson gson = new Gson();
	private static JsonObject jsonObject;
	private static JsonObject commondata;
	private static JsonObject environments;

	Test() {
		registerEnvironment("ITV1");
		initializeJSON();
		fillCustomFields("CheckoutPage", "CancelOrder");

	}

	public static void main(String args[]) {
		new Test();
	}

	public void registerEnvironment(String envn) {
		try (FileReader reader = new FileReader(dataFile)) {
			jsonObject = gson.fromJson(new FileReader(dataFile), JsonObject.class);
			environments = jsonObject.getAsJsonObject("Environments");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void initializeJSON() {
		commondata = jsonObject.getAsJsonObject("commondata");
	}

	public void fillCustomFields(String pageName, String dataTarget) {
		JsonElement page = commondata.get(pageName);
		JsonElement dataContainer = getDataContainer(page, dataTarget);
		fillFields(page, dataContainer);
	}

	public JsonElement getDataContainer(JsonElement page, String dataTarget) {
		JsonObject obj = page.getAsJsonObject();
		return obj.get(dataTarget);
	}

	public void fillFields(JsonElement page, JsonElement dataContainer) {
		LinkedHashMap<String, String> containerFields = getContainerFields(dataContainer);
		for (Map.Entry<String, String> entry : containerFields.entrySet()) {
			String locator = entry.getKey();
			String value = entry.getValue();
			System.out.println(locator);
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

	public void getAllSubContainers(String pageName) {
		JsonElement page = commondata.get(pageName);
		JsonObject obj = page.getAsJsonObject();
		System.out.println(obj.keySet());
	}

}