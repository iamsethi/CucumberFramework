package com.amazon.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.amazon.managers.FileReaderManager;
import com.amazon.managers.WebDriverManager;

public abstract class BasePage {

	public long elementWait = FileReaderManager.getInstance().getConfigReader().getImplicitlyWait();
	WebDriver driver = WebDriverManager.getInstance().getDriver();

	public BasePage() {
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = ".//img[@title='All Tabs']")
	public WebElement lnk_all_tabs;

	@FindBy(xpath = "//input[@id='phSearchInput']")
	public WebElement tbx_globalSearch;

	@FindBy(css = "a[title='Logout']")
	public WebElement lnk_logout;

	@FindBy(xpath = "//a[@id='helpBubbleCloseX']/img")
	public WebElement img_close;

	@FindBy(css = "table[class = 'list']")
	public WebElement tbl_search_result;

	@FindBy(xpath = "//li[@id='home_Tab']/a")
	public WebElement lnk_home;

	// common WebElement locators included in base class
	public String getTitle() throws Exception {
		return driver.getTitle();
	}

	public void openTab(String tabName) {
		try {
			// lnk_all_tabs.withTimeoutOf(60, TimeUnit.SECONDS).waitUntilVisible();
			lnk_all_tabs.click();
		} catch (Exception ex) {

		}

		if (tabName.equalsIgnoreCase("Proposal")) {
			// findBy("(//a[@href='/aDZ/o'])").click();
		}

		// else

		// find(By.cssSelector("img[title = '" + tabName + "']")).click();

	}

	public void logout() {
		lnk_logout.click();
	}

	public void closePopUps() { // NO_UCD (unused code)
		closeHelpBubble();
	}

	private void closeHelpBubble() {

		img_close.click();

	}

	public void waitForResultTableToLoad() {
		// tbl_search_result.isPresent();
		// tbl_search_result.waitUntilPresent();
	}

	public void navigateToHomePage() {
		// lnk_home.isPresent();
		lnk_home.click();
	}

	public void switchtoFrame(String frame) {
		// getDriver().switchTo().window(getDriver().getWindowHandle());
		// getDriver().switchTo().frame(frame);
	}

	public void switchToFrame(WebElement element) {
		// List<WebElement> lstFrames = findAll(By.tagName("iframe"));
		// for (WebElement we : lstFrames) {
		// String frame = we.getAttribute("id");
		// switchtoFrame(frame);
		// if (element.isCurrentlyVisible()) {
		// break;
		// }
		// getDriver().switchTo().defaultContent();
		// }
	}

}
