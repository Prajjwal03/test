package com.smbc.epix.transaction.services.exception;

/**
 * This should be thrown if some error occurred while
 * reading properties from property file or if property file doesn't exist
 */
public class PropertyFileNotFoundException extends RuntimeException {

    public PropertyFileNotFoundException() {
        super();
    }

    public PropertyFileNotFoundException(String message) {
        super(message);
    }

    public PropertyFileNotFoundException(Throwable cause) {
        super(cause);
    }

    public PropertyFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
