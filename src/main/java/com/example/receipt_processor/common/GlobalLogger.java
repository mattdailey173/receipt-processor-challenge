package com.example.receipt_processor.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class GlobalLogger {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLogger.class);

    // Example method to log informational messages
    public void info(String message) {
        logger.info(message);
    }

    // Example method to log warnings
    public void warn(String message) {
        logger.warn(message);
    }

    // Example method to log errors with exception details
    public void error(String message, Throwable exception) {
        logger.error(message, exception);
    }

    // Example method to log debug messages
    public void debug(String message) {
        logger.debug(message);
    }
}
