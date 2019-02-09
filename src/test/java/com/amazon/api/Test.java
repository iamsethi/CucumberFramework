package com.amazon.api;

import java.io.FileReader;
import java.util.Iterator;

import com.amazon.managers.FileReaderManager;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Test {
	private static String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();

	public static void main(String args[]) {
		Gson gson = new Gson();
		try {
			JsonObject jsonObject = gson.fromJson(new FileReader(dataFile), JsonObject.class);
			JsonArray Environments = jsonObject.getAsJsonArray("Environments");
			Iterator<JsonElement> iterator = Environments.iterator();
			while (iterator.hasNext()) {
				JsonElement jsonElement = iterator.next();
				JsonObject itv = jsonElement.getAsJsonObject();
				System.out.println(itv.keySet());
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
