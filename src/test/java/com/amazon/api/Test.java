package com.amazon.api;

import java.io.FileReader;

import com.amazon.managers.FileReaderManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Test {
	private String dataFile = FileReaderManager.getInstance().getConfigReader().getTestDataResourcePath();
	Gson gson = new Gson();

	Test() {
		getEnvironment("ITV1");
	}

	public static void main(String args[]) {
		new Test();
	}

	public void getEnvironment(String envn) {
		try {
			JsonObject jsonObject = gson.fromJson(new FileReader(dataFile), JsonObject.class);
			JsonObject Environments = jsonObject.getAsJsonObject("Environments");
			System.out.println(Environments.get(envn));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}