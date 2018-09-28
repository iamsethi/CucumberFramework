package com.amazon.interfaces;

import org.apache.log4j.Logger;

public interface ILog {

	// Initialize Log4j logs

	public static Logger getLogger(Class<?> logClass) {
		return Logger.getLogger(logClass);
	}

	public static String info(Object message) {
		return message.toString();
	}

	public static String error(Object message) {
		return message.toString();
	}

	public static String fatal(Object message) {
		return message.toString();
	}

	public static String debug(Object message) {
		return message.toString();
	}

}