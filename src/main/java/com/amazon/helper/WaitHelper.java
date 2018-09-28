package com.amazon.helper;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.dataProviders.ConfigFileReader;
import com.amazon.interfaces.ILog;
import com.amazon.managers.FileReaderManager;

public class WaitHelper extends GenericHelper {

	private WebDriver driver;
	private Logger Log = ILog.getLogger(WaitHelper.class);
	private static ConfigFileReader configFileReader = FileReaderManager.getInstance().getConfigReader();

	public WaitHelper(WebDriver driver) {
		super(driver);
		this.driver = driver;
		Log.debug("WaitHelper : " + this.driver.hashCode());
	}

	private WebDriverWait getWait(int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.debug("");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.pollingEvery(pollingEveryInMiliSec, TimeUnit.MILLISECONDS);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.ignoring(NoSuchFrameException.class);
		return wait;
	}

	public void setImplicitWait(long timeout, TimeUnit unit) {
		Log.info(timeout);
		driver.manage().timeouts().implicitlyWait(timeout, unit == null ? TimeUnit.SECONDS : unit);
	}

	public void waitForElementVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
		setImplicitWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
	}

	public void hardWait(int timeOutInMiliSec) throws InterruptedException {
		Log.info(timeOutInMiliSec);
		Thread.sleep(timeOutInMiliSec);
	}

	public WebElement handleStaleElement(By locator, int retryCount, int delayInSeconds) throws InterruptedException {
		Log.info(locator);
		WebElement element = null;

		while (retryCount >= 0) {
			try {
				element = driver.findElement(locator);
				return element;
			} catch (StaleElementReferenceException e) {
				hardWait(delayInSeconds);
				retryCount--;
			}
		}
		throw new StaleElementReferenceException("Element cannot be recovered");
	}

	public void elementExits(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(elementLocatedBy(locator));
		setImplicitWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
	}

	public void elementExistAndVisible(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(elementLocatedBy(locator));
		// new JavaScriptHelper().scrollIntoView(locator);
		wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		setImplicitWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);

	}

	public void waitForIframe(By locator, int timeOutInSeconds, int pollingEveryInMiliSec) {
		Log.info(locator);
		setImplicitWait(1, TimeUnit.SECONDS);
		WebDriverWait wait = getWait(timeOutInSeconds, pollingEveryInMiliSec);
		wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(locator));
		driver.switchTo().defaultContent();
		setImplicitWait(configFileReader.getImplicitlyWait(), TimeUnit.SECONDS);
	}

	private Function<WebDriver, Boolean> elementLocatedBy(final By locator) {
		return new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				Log.debug(locator);
				return driver.findElements(locator).size() >= 1;
			}
		};
	}

}
