package com.amazon.stepDefinitions;

import com.amazon.dataProviders.JsonDataReader;
import com.amazon.pageObjects.PageBase;

public class BaseSteps {

	public void get_data_for_page(PageBase page) {
		return JsonDataReader.getContainer(page.getClass().getSimpleName());
	}

}
