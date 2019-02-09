package com.amazon.api;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.amazon.managers.FileReaderManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Test {
	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	static Gson gson = new Gson();
	private static JsonObject jsonObject;
	private static JsonObject commondata;

	Test() {
		registerEnvironment("ITV1");
		initializeJSON();

	}

	public static void main(String args[]) {
		new Test();
	}

	public static void registerEnvironment(String envn) {
		try (FileReader reader = new FileReader(dataFile)) {
			jsonObject = gson.fromJson(new FileReader(dataFile), JsonObject.class);
			JsonObject Environments = jsonObject.getAsJsonObject("Environments");
			System.out.println(Environments.get(envn));
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
		System.out.println(commondata);

	}
}