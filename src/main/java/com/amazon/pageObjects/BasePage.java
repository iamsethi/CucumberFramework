package com.amazon.pageObjects;

import java.util.HashMap;

import org.json.simple.JSONArray;

import com.amazon.dataProviders.JsonDataReader;

public class BasePage {

	public HashMap<String, JSONArray> get_data_for_page(BasePage page) {
		return JsonDataReader.getContainer(page.getClass().getSimpleName());
	}
}
