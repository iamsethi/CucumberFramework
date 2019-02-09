package com.amazon.pageObjects;

import com.amazon.dataProviders.JsonDataReader;

public class BasePage {

	public void fillAllFields(BasePage page, String dataTarget) {

		JsonDataReader.fillCustomFields("CheckoutPage", "CancelOrder");

	}

}
