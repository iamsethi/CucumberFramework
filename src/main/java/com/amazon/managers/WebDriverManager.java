package com.amazon.managers;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.amazon.enums.DriverType;
import com.amazon.enums.EnvironmentType;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;

/**
 * @author Ketan Sethi
 *
 *
 */
public class WebDriverManager {
	private static DriverType driverType;
	private static EnvironmentType environmentType;
	private static WebDriverManager instance = null;
	private ThreadLocal<WebDriver> webDriver = new ThreadLocal<WebDriver>();
	private ThreadLocal<AppiumDriver<MobileElement>> mobileDriver = new ThreadLocal<AppiumDriver<MobileElement>>();
	private ThreadLocal<String> sessionId = new ThreadLocal<String>();
	private ThreadLocal<String> sessionBrowser = new ThreadLocal<String>();
	private ThreadLocal<String> sessionPlatform = new ThreadLocal<String>();
	private ThreadLocal<String> sessionVersion = new ThreadLocal<String>();

	private WebDriverManager() {
		driverType = FileReaderManager.getInstance().getConfigReader().getBrowser();
		environmentType = FileReaderManager.getInstance().getConfigReader().getEnvironment();
	}

	/**
	 * getInstance method to retrieve active driver instance
	 *
	 * @return WebDriverManager
	 */
	public static WebDriverManager getInstance() {
		if (instance == null) {
			instance = new WebDriverManager();
		}

		return instance;
	}

	/**
	 * setDriver method to create driver instance
	 *
	 * @param browser
	 * @param environment
	 * @param platform
	 * @param optPreferences
	 * @throws Exception
	 */

	@SafeVarargs
	public final void setDriver(Map<String, Object>... optPreferences) throws Exception {
		String remoteHubURL = "http://localhost:4444/wd/hub";
		DesiredCapabilities caps = null;
		switch (environmentType) {
		case LOCAL:
			switch (driverType) {
			case FIREFOX: // about:config
				FirefoxOptions ffOptions = new FirefoxOptions();
				webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), ffOptions));
				break;
			case CHROME: // chrome://flags
				ChromeOptions chOptions = new ChromeOptions();
				// Start Chrome maximized
				chOptions.addArguments("start-maximized");
				chOptions.addArguments("--disable-plugins", "--disable-extensions", "--disable-popup-blocking");
				// Set a Chrome preference
				Map<String, Object> prefs = new HashMap<String, Object>();
				prefs.put("profile.default_content_settings.popups", 0);
				chOptions.setExperimentalOption("prefs", prefs);
				System.setProperty("webdriver.chrome.driver",
						FileReaderManager.getInstance().getConfigReader().getDriverPath("chrome.driver.windows.path"));
				if (optPreferences.length > 0) {
					processCHOptions(chOptions, optPreferences);
				}
				webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), chOptions));
				break;
			case INTERNETEXPLORER:
				caps = DesiredCapabilities.internetExplorer();
				caps.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
				caps.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
				System.setProperty("webdriver.ie.driver",
						FileReaderManager.getInstance().getConfigReader().getDriverPath("ie.driver.windows.path"));
				if (optPreferences.length > 0) {
					// processDesiredCaps(caps, optPreferences);
				}
				webDriver.set(new InternetExplorerDriver(caps));
				break;
			case ANDROID:
				break;
			default:
				break;
			}
			break;
		case REMOTE:
			webDriver.set(new RemoteWebDriver(new URL(remoteHubURL), caps));
			((RemoteWebDriver) webDriver.get()).setFileDetector(new LocalFileDetector());
			break;
		}
		sessionId.set(((RemoteWebDriver) webDriver.get()).getSessionId().toString());
		sessionBrowser.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getBrowserName());
		sessionPlatform.set(((RemoteWebDriver) webDriver.get()).getCapabilities().getPlatform().toString());
		getDriver().manage().window().maximize();

	}

	/**
	 * overloaded setDriver method to switch driver to specific AppiumDriver if
	 * running concurrent drivers
	 *
	 * @param driver
	 *            AppiumDriver instance to switch to
	 */
	public void setDriver(AppiumDriver<MobileElement> driver) {
		mobileDriver.set(driver);
		sessionId.set(mobileDriver.get().getSessionId().toString());
		sessionBrowser.set(mobileDriver.get().getCapabilities().getBrowserName());
		sessionPlatform.set(mobileDriver.get().getCapabilities().getPlatform().toString());
	}

	/**
	 * getDriver method will retrieve the active WebDriver
	 *
	 * @return WebDriver
	 */
	public WebDriver getDriver() {
		return webDriver.get();
	}

	/**
	 * getDriver method will retrieve the active AppiumDriver
	 *
	 * @param mobile
	 *            boolean parameter
	 * @return AppiumDriver
	 */
	public AppiumDriver<MobileElement> getDriver(boolean mobile) {
		return mobileDriver.get();
	}

	/**
	 * getCurrentDriver method will retrieve the active WebDriver or AppiumDriver
	 *
	 * @return WebDriver
	 */
	public WebDriver getCurrentDriver() {
		if (getInstance().getSessionBrowser().contains("iphone") || getInstance().getSessionBrowser().contains("ipad")
				|| getInstance().getSessionBrowser().contains("android")) {
			return getInstance().getDriver(true);
		} else {
			return getInstance().getDriver();
		}
	}

	/**
	 * closeDriver method to close active driver
	 *
	 */
	public void closeDriver() {
		try {
			getDriver().quit();
		}

		catch (Exception e) {
			// do something
		}
	}

	/**
	 * getSessionId method to retrieve active id
	 *
	 * @return String
	 * @throws Exception
	 */
	public String getSessionId() {
		return sessionId.get();
	}

	/**
	 * getSessionBrowser method to retrieve active browser
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getSessionBrowser() {
		return sessionBrowser.get();
	}

	/**
	 * getSessionVersion method to retrieve active version
	 *
	 * @return String
	 * @throws Exception
	 */
	public String getSessionVersion() {
		return sessionVersion.get();
	}

	/**
	 * getSessionPlatform method to retrieve active platform
	 * 
	 * @return String
	 * @throws Exception
	 */
	public String getSessionPlatform() {
		return sessionPlatform.get();
	}

	/**
	 * Process Chrome Options method to override default browser driver behavior
	 *
	 * @param caps
	 *            - the ChromeOptions object
	 * @param options
	 *            - the key: value pair map
	 * @throws Exception
	 */
	private void processCHOptions(ChromeOptions chOptions, Map<String, Object>[] options) {
		for (int i = 0; i < options.length; i++) {
			Object[] keys = options[i].keySet().toArray();
			Object[] values = options[i].values().toArray();
			// same as Desired Caps except the following difference
			for (int j = 0; j < keys.length; j++) {
				if (values[j] instanceof Integer) {
					values[j] = (int) values[j];
					chOptions.setExperimentalOption("prefs", options[i]);
				}
				// etc...
			}
		}
	}

}