/*******************************************************************************
 * Copyright (C) Endeavour Software Technologies Pvt. Ltd.
 * Confidential and proprietary.
 * CoaLogger.java
 * This class log the msg
 *******************************************************************************/
package com.mgWork.logger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author COA Team The Class SCLogger.
 */
public final class MgLogger {
	
	private MgLogger() {

	}

	/**
	 * Log audit.
	 *
	 * @param obj
	 */
	public static void logAudit(Object obj) {
		Logger logger = getLogger(LoggerConstant.AUDIT_LOGGER);
		logger.info((String) obj);
	}

	/**
	 * Log error.
	 *
	 * @param obj
	 */
	public static void logError(Object obj) {
		Logger logger = getLogger(LoggerConstant.ERROR_LOGGER);
		logger.error((String) obj);
	}

	/**
	 * Log error.
	 *
	 * @param obj
	 * @param e
	 */
	public static void logError(Object obj, Throwable e) {
		Logger logger = getLogger(LoggerConstant.ERROR_LOGGER);
		logger.error((String) obj, e);
	}

	/**
	 * Log debug.
	 *
	 * @param obj
	 */
	public static void logDebug(Object obj) {
		Logger logger = getLogger(LoggerConstant.LOGGER_LOCATION);
		logger.debug((String) obj);
	}

	/**
	 * Log warn.
	 *
	 * @param obj
	 */
	public static void logWarn(Object obj) {
		Logger logger = getLogger(LoggerConstant.LOGGER_LOCATION);
		logger.warn((String) obj);
	}

	/**
	 * Log info.
	 *
	 * @param obj
	 */
	public static void logInfo(Object obj) {
		Logger logger = getLogger(LoggerConstant.LOGGER_LOCATION);
		logger.info((String) obj);
	}

	/**
	 * Log trace.
	 *
	 * @param obj
	 */
	public static void logTrace(Object obj) {
		Logger logger = getLogger(LoggerConstant.INFO_LOGGER);
		logger.info((String) obj);
	}

	/**
	 * Get logger with the given name.
	 *
	 * @param jobName
	 * @return Logger
	 */
	private static Logger getLogger(String jobName) {
//		Properties props = PropertyFileLoader.getProperties(LoggerConstant.LOGGER_PROPERTIES);
//		PropertyConfigurator.configure(props);
		return LoggerFactory.getLogger(jobName);
	}
}
