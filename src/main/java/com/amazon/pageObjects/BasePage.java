package com.amazon.pageObjects;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.amazon.dataProviders.JsonDataReader;

public class BasePage {

	public HashMap<String, LinkedHashMap<String, String>> get_data_for_page(BasePage page) {
		return JsonDataReader.getContainer(page.getClass().getSimpleName());
		// String is ShippingAddress and LinkedHashMap<String, String> is
		// (tbx_name,brian)
		// String is CancelOrder value and LinkedHashMap<String, String> is
		// (tbx_name,brian)
	}

	public void fillAllFields(BasePage page, String dataTarget) {
		JsonDataReader.fillFields(page.getClass().getSimpleName(), dataTarget, get_data_for_page(page).get(dataTarget));

	}

}
