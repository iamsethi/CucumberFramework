package com.amazon.pageObjects;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.amazon.managers.FileReaderManager;

public class BasePage {

	public HashMap<String, LinkedHashMap<String, String>> get_data_for_page(BasePage page) {
		return FileReaderManager.getInstance().getJsonReader().getContainer(page.getClass().getSimpleName());
	}

	public void fillAllFields(BasePage page, String dataTarget) {
		FileReaderManager.getInstance().getJsonReader().fillFields(page.getClass().getSimpleName(), dataTarget,
				get_data_for_page(page).get(dataTarget));

	}

}
