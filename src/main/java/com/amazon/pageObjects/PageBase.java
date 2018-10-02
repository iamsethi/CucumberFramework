package com.amazon.pageObjects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.interfaces.ILog;
import com.google.common.base.Function;

@SuppressWarnings("rawtypes")
public abstract class PageBase {
	private WebDriver driver;
	private Logger Log = ILog.getLogger(PageBase.class);

	public static PageBase page;

	public PageBase(WebDriver driver) {
		if (driver == null)
			throw new IllegalArgumentException("Driver object is null");

		page = PageFactory.initElements(driver, PageBase.class);
		this.driver = driver;
	}

	private By getFindByAnno(FindBy anno) {
		switch (anno.how()) {

		case CLASS_NAME:
			return new By.ByClassName(anno.using());
		case CSS:
			return new By.ByCssSelector(anno.using());
		case ID:
			return new By.ById(anno.using());
		case LINK_TEXT:
			return new By.ByLinkText(anno.using());
		case NAME:
			return new By.ByName(anno.using());
		case PARTIAL_LINK_TEXT:
			return new By.ByPartialLinkText(anno.using());
		case XPATH:
			return new By.ByXPath(anno.using());
		default:
			throw new IllegalArgumentException("Locator not Found : " + anno.how() + " : " + anno.using());
		}
	}

	protected By getElemetLocator(Object obj, String element) throws SecurityException, NoSuchFieldException {
		Class childClass = obj.getClass();
		By locator = null;
		try {
			locator = getFindByAnno(childClass.getDeclaredField(element).getAnnotation(FindBy.class));
		} catch (SecurityException | NoSuchFieldException e) {
			Log.info(e);
			throw e;
		}
		Log.info(locator);
		return locator;
	}

	public void waitForElement(WebElement element, int timeOutInSeconds) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.ignoring(NoSuchElementException.class);
		wait.ignoring(ElementNotVisibleException.class);
		wait.ignoring(StaleElementReferenceException.class);
		wait.until(elementLocated(element));
	}

	private Function<WebDriver, Boolean> elementLocated(final WebElement element) {
		return new Function<WebDriver, Boolean>() {

			@Override
			public Boolean apply(WebDriver driver) {
				Log.debug("Waiting for Element : " + element);
				return element.isDisplayed();
			}
		};
	}

	public boolean checkForTitle(String title) {
		Log.info(title);
		if (title == null || title.isEmpty())
			throw new IllegalArgumentException(title);
		return driver.getTitle().trim().contains(title);
	}

}
