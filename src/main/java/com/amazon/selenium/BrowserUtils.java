package com.amazon.selenium;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.amazon.managers.FileReaderManager;
import com.amazon.managers.WebDriverManager;

public class BrowserUtils {

	public static void untilJqueryIsDone(WebDriver driver) {
		untilJqueryIsDone(driver, FileReaderManager.getInstance().getConfigReader().getImplicitlyWait());
	}

	public static void untilJqueryIsDone(WebDriver driver, Long timeoutInSeconds) {
		until(driver, (d) -> {
			Boolean isJqueryCallDone = (Boolean) ((JavascriptExecutor) driver).executeScript("return jQuery.active==0");
			if (!isJqueryCallDone)
				System.out.println("JQuery call is in Progress");
			return isJqueryCallDone;
		}, timeoutInSeconds);
	}

	public static void untilPageLoadComplete(WebDriver driver) {
		untilPageLoadComplete(driver, FileReaderManager.getInstance().getConfigReader().getImplicitlyWait());
	}

	public static void untilPageLoadComplete(WebDriver driver, Long timeoutInSeconds) {
		until(driver, (d) -> {
			Boolean isPageLoaded = (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState")
					.equals("complete");
			if (!isPageLoaded)
				System.out.println("Document is loading");
			return isPageLoaded;
		}, timeoutInSeconds);
		System.out.println("untilPageLoadComplete");
	}

	public static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition) {
		until(driver, waitCondition, FileReaderManager.getInstance().getConfigReader().getImplicitlyWait());
	}

	private static void until(WebDriver driver, Function<WebDriver, Boolean> waitCondition, Long timeoutInSeconds) {
		WebDriverWait webDriverWait = new WebDriverWait(driver, timeoutInSeconds);
		webDriverWait.withTimeout(timeoutInSeconds, TimeUnit.SECONDS);
		try {
			webDriverWait.until(waitCondition);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	/**
	 * waitFor method to wait up to a designated period before throwing exception
	 * (static locator)
	 *
	 * @param element
	 * @param timer
	 * @throws Exception
	 */
	public static void waitFor(WebElement element, Long timer) throws Exception {
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		// wait for the static element to appear
		WebDriverWait exists = new WebDriverWait(driver, timer);
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOf(element)));
	}

	/**
	 * overloaded waitFor method to wait up to a designated period before throwing
	 * exception (dynamic locator)
	 *
	 * @param by
	 * @param timer
	 * @throws Exception
	 */
	public static void waitFor(By by, int timer) throws Exception {
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		// wait for the dynamic element to appear
		WebDriverWait exists = new WebDriverWait(driver, timer);
		// examples: By.id(id),By.name(name),By.xpath(locator),

		// By.cssSelector(css)
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.visibilityOfElementLocated(by)));
	}

	/**
	 * waitForGone method to wait up to a designated period before throwing
	 * exception if element still exists
	 *
	 * @param by
	 * @param timer
	 * @throws Exception
	 */
	public static void waitForGone(By by, int timer) throws Exception {
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		// wait for the dynamic element to disappear
		WebDriverWait exists = new WebDriverWait(driver, timer);
		// examples: By.id(id),By.name(name),By.xpath(locator),
		// By.cssSelector(css)
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.invisibilityOfElementLocated(by)));
	}

	/**
	 * waitForURL method to wait up to a designated period before throwing exception
	 * if URL is not found
	 *
	 * @param by
	 * @param timer
	 * @throws Exception
	 */
	public static void waitForURL(String url, Long seconds) throws Exception {
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		WebDriverWait exists = new WebDriverWait(driver, seconds);
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.urlContains(url)));
	}

	/**
	 * waitFor method to wait up to a designated period before throwing exception if
	 * Title is not found
	 *
	 * @param by
	 * @param timer
	 * @throws Exception
	 */
	public void waitFor(String title, int timer) throws Exception {
		WebDriver driver = WebDriverManager.getInstance().getDriver();
		WebDriverWait exists = new WebDriverWait(driver, timer);
		exists.until(ExpectedConditions.refreshed(ExpectedConditions.titleContains(title)));
	}

	public int getRowCount(WebElement table) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		return tableRows.size();
	}

	public int getColumnCount(WebElement table) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		WebElement headerRow = tableRows.get(1);
		List<WebElement> tableCols = headerRow.findElements(By.tagName("td"));
		return tableCols.size();

	}

	public int getColumnCount(WebElement table, int index) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		WebElement headerRow = tableRows.get(index);
		List<WebElement> tableCols = headerRow.findElements(By.tagName("td"));
		return tableCols.size();
	}

	public String getRowData(WebElement table, int rowIndex) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		WebElement currentRow = tableRows.get(rowIndex);
		return currentRow.getText();
	}

	public String getCellData(WebElement table, int rowIndex, int colIndex) {
		List<WebElement> tableRows = table.findElements(By.tagName("tr"));
		WebElement currentRow = tableRows.get(rowIndex);
		List<WebElement> tableCols = currentRow.findElements(By.tagName("td"));
		WebElement cell = tableCols.get(colIndex - 1);
		return cell.getText();
	}

}