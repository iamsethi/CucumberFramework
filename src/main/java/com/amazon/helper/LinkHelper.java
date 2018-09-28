package com.amazon.helper;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.amazon.interfaces.Log;

public class LinkHelper extends GenericHelper {

	private WebDriver driver;

	public LinkHelper(WebDriver driver) {
		super(driver);
		this.driver = driver;
		Log.debug("LinkHelper : " + this.driver.hashCode());
	}

	public void clickLink(String linkText) {
		Log.info(linkText);
		getElement(By.linkText(linkText)).click();
	}

	public void clickPartialLink(String partialLinkText) {
		Log.info(partialLinkText);
		getElement(By.partialLinkText(partialLinkText)).click();
	}

	public String getHyperLink(By locator) {
		Log.info(locator);
		return getHyperLink(getElement(locator));
	}

	public String getHyperLink(WebElement element) {
		String link = element.getAttribute("hreg");
		Log.info("Element : " + element + " Value : " + link);
		return link;
	}
}
