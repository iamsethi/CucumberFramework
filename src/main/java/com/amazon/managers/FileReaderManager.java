package com.amazon.managers;

import com.amazon.dataProviders.ConfigFileReader;

public class FileReaderManager {// Singleton Design Pattern
	/*
	 * Logger : Singleton classes are used in log file generations. Log files are
	 * created by logger class object. Suppose an application where the logging
	 * utility has to produce one log file based on the messages received from the
	 * users. If there is multiple client application using this logging utility
	 * class they might create multiple instances of this class and it can
	 * potentially cause issues during concurrent access to the same logger file. We
	 * can use the logger utility class as a singleton and provide a global point of
	 * reference, so that each user can use this utility and no 2 users access it at
	 * same time. 
	 * 
	 * Configuration File: This is another potential candidate for
	 * Singleton pattern because this has a performance benefit as it prevents
	 * multiple users to repeatedly access and read the configuration file or
	 * properties file. It creates a single instance of the configuration file which
	 * can be accessed by multiple calls concurrently as it will provide static
	 * config data loaded into in-memory objects. The application only reads from
	 * the configuration file at the first time and there after from second call
	 * onwards the client applications read the data from in-memory objects
	 */

	private static FileReaderManager fileReaderManager = new FileReaderManager();
	private static ConfigFileReader configFileReader;

	private FileReaderManager() {
	}

	public static FileReaderManager getInstance() {
		return fileReaderManager;
	}

	public ConfigFileReader getConfigReader() {
		return (configFileReader == null) ? new ConfigFileReader() : configFileReader;
	}

}