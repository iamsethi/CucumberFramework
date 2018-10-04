package com.amazon.pageObjects;

import java.util.HashMap;

import org.json.simple.JSONObject;

import com.amazon.dataProviders.JsonDataReader;

public class BasePage {

	public HashMap<String, JSONObject> get_data_for_page(BasePage page) {
		return JsonDataReader.getContainer(page.getClass().getSimpleName());
	}
}
