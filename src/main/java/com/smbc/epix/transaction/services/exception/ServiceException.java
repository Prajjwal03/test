package com.smbc.epix.transaction.services.exception;

/**
 * Wrap Service layer exception and return to Controller layer.
 */
public class ServiceException extends RuntimeException {
    private static final long serialVersionUID = 686773146868305469L;

    private String messageCode;

    /**
     * Constructs a new Service runtime exception
     */
    public ServiceException() {
        super();
    }

    /**
     * Constructs a new Service runtime exception with the specified detail message.
     *
     * @param messageCode
     */
    public ServiceException(String messageCode) {
        this.messageCode = messageCode;
    }


    /**
     * Constructs a new Service runtime exception with the specified cause.
     *
     * @param cause the cause
     */
    public ServiceException(Throwable cause) {
        super(cause);
    }


    /**
     * Constructs a new Service runtime exception with the specified detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new Service runtime exception with the specified detail message and message code.
     *
     * @param messageCode the message code
     * @param message     the detail message
     */
    public ServiceException(String messageCode, String message) {
        super(message);
        this.messageCode = messageCode;
    }

    /**
     * Constructs a new Service runtime exception with the specified detail message, message code and cause.
     *
     * @param messageCode the message code
     * @param message     the detail message
     * @param cause       the cause
     */
    public ServiceException(String messageCode, String message, Throwable cause) {
        super(message, cause);
        this.messageCode = messageCode;
    }
}
