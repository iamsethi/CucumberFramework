package com.amazon.interfaces;

import org.apache.log4j.Logger;

public interface Log {

	// Initialize Log4j logs
	public static Logger Log = Logger.getLogger(Log.class.getName());

	public static void info(Object message) {
		Log.info(message.toString());
	}

	public static void error(Object message) {
		Log.error(message);
	}

	public static void fatal(Object message) {
		Log.fatal(message);
	}

	public static void debug(Object message) {
		Log.debug(message);
	}

}