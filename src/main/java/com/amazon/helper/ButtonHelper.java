package com.amazon.helper;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.amazon.interfaces.ILog;
import com.amazon.interfaces.IwebComponent;

public class ButtonHelper implements IwebComponent {

	private WebDriver driver;
	private Logger Log = ILog.getLogger(ButtonHelper.class);

	public ButtonHelper(WebDriver driver) {
		this.driver = driver;
		Log.debug("Button Helper : " + this.driver.hashCode());
	}

	public void click(By locator) {
		click((WebElement) driver.findElement(locator));
		Log.info(locator);
	}

	public void click(WebElement element) {
		element.click();
		Log.info(element);
	}
}
