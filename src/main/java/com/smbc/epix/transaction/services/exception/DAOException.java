package com.smbc.epix.transaction.services.exception;

public class DAOException extends RuntimeException {

    private static final long serialVersionUID = -4347560831851976495L;

    /**
     * Constructs a new DAO runtime exception
     */
    public DAOException() {
        super();
    }

    /**
     * Constructs a new DAO runtime exception with the specified detail message.
     *
     * @param message the detail message. The detail message is saved for later retrieval by the
     *                {@link #getMessage()} method.
     */
    public DAOException(String message) {
        super(message);
    }

    /**
     * Constructs a new DAO runtime exception with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the
     *                {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public DAOException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new DAO runtime exception with the specified cause.
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public DAOException(Throwable cause) {
        super(cause);
    }
}
