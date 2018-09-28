package com.amazon.helper;

import java.net.URL;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.amazon.interfaces.ILog;
import com.amazon.interfaces.IwebComponent;

public class NavigationHelper implements IwebComponent {

	private WebDriver driver;
	private Logger Log = ILog.getLogger(NavigationHelper.class);

	public NavigationHelper(WebDriver driver) {
		this.driver = driver;
		Log.debug("NavigationHelper : " + this.driver.hashCode());
	}

	public void navigateTo(String url) {
		Log.info(url);
		driver.get(url);
	}

	public void naviagteTo(URL url) {
		Log.info(url.getPath());
		driver.get(url.getPath());
	}

	public String getTitle() {
		String title = driver.getTitle();
		Log.info(title);
		return driver.getTitle();
	}

	public String getCurrentUrl() {
		String url = driver.getCurrentUrl();
		Log.info(url);
		return driver.getCurrentUrl();
	}

}
