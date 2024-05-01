package com.smbc.epix.transaction.services.exception;

/** This Exception is to be used to catch an exception occurring from Product API calls */
public class ProductApiException extends RuntimeException {

    private static final long serialVersionUID = 5825189818064090620L;

    public ProductApiException() {
        super();
    }

    /**
     * {@inheritDoc}
     */
    public ProductApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    /**
     * {@inheritDoc}
     */
    public ProductApiException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * {@inheritDoc}
     */
    public ProductApiException(String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public ProductApiException(Throwable cause) {
        super(cause);
    }
}
