/*******************************************************************************
 * Copyright (C) Endeavour Software Technologies Pvt. Ltd.
 * Confidential and proprietary.
 *******************************************************************************/
package com.mgWork.logger;

/**
 * @author COA Team The Interface LoggerConstant.
 */
public interface LoggerConstant {

	/** The logger properties. */
	String LOGGER_PROPERTIES = "log4j2.yaml";
	
	/** The logger location. */
	String LOGGER_LOCATION = "com.mgWork.logger.AppLogger";

	/** The audit logger. */
	String AUDIT_LOGGER = "AUDIT_LOGGER";

	/** The error logger. */
	String ERROR_LOGGER = "ERROR_LOGGER";

	/** The log file path. */
	String LOG_FILE_PATH = "log";

	/** The trace logger. */
	String INFO_LOGGER = "INFO_LOGGER";
}
