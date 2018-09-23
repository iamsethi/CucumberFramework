package com.amazon.utils;

import org.apache.log4j.Logger;

public class Log {

	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Log.class.getName());

	public static void info(Object message) {
		Log.info(message.toString());
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