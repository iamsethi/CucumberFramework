package com.amazon.cucumber;

import com.amazon.managers.FileReaderManager;
import com.amazon.managers.PageObjectManager;
import com.amazon.managers.WebDriverManager;

public class TestContext {
	private WebDriverManager webDriverManager;
	private FileReaderManager fileReaderManager;
	private PageObjectManager pageObjectManager;

	public TestContext() {
		webDriverManager = WebDriverManager.getInstance();
		fileReaderManager = FileReaderManager.getInstance();
		pageObjectManager = new PageObjectManager(webDriverManager.getDriver());
	}

	public WebDriverManager getWebDriverManager() {
		return webDriverManager;
	}

	public PageObjectManager getPageObjectManager() {
		return pageObjectManager;
	}

	public FileReaderManager getFileReaderManager() {
		return fileReaderManager;
	}

}