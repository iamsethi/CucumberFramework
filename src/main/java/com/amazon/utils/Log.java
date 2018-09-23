package com.amazon.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class Log {

	private static void init() {
		
		// OR for property file, should use any one of these
		// PropertyConfigurator.configure("myapp-log4j.properties");
	}

	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Log.class.getName());

	public static void info(String message) {
		Log.info(message);
	}

	public static void error(String message) {
		Log.error(message);
	}

	public static void fatal(String message) {
		Log.fatal(message);
	}

	public static void debug(String message) {
		Log.debug(message);
	}

}