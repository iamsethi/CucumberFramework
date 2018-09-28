package com.amazon.helper;

import java.util.LinkedList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.amazon.interfaces.Log;

public class DropDownHelper extends GenericHelper {

	private WebDriver driver;

	public DropDownHelper(WebDriver driver) {
		super(driver);
		this.driver = driver;
		Log.debug("DropDownHelper : " + this.driver.hashCode());
	}

	public void SelectUsingVisibleValue(By locator, String visibleValue) {
		SelectUsingVisibleValue(getElement(locator), visibleValue);
	}

	public void SelectUsingVisibleValue(WebElement element, String visibleValue) {
		Select select = new Select(element);
		select.selectByVisibleText(visibleValue);
		Log.info("Locator : " + element + " Value : " + visibleValue);
	}

	public void SelectUsingValue(By locator, String value) {
		Select select = new Select(getElement(locator));
		select.selectByValue(value);
		Log.info("Locator : " + locator + " Value : " + value);
	}

	public void SelectUsingIndex(By locator, int index) {
		Select select = new Select(getElement(locator));
		select.selectByIndex(index);
		Log.info("Locator : " + locator + " Index : " + index);
	}

	public String getSelectedValue(By locator) {
		Log.info(locator);
		return getSelectedValue(getElement(locator));
	}

	public String getSelectedValue(WebElement element) {
		String value = new Select(element).getFirstSelectedOption().getText();
		Log.info("WebELement : " + element + " Value : " + value);
		return value;
	}

	public List<String> getAllDropDownValues(By locator) {
		Select select = new Select(getElement(locator));
		List<WebElement> elementList = select.getOptions();
		List<String> valueList = new LinkedList<String>();

		for (WebElement element : elementList) {
			Log.info(element.getText());
			valueList.add(element.getText());
		}
		return valueList;
	}
}
