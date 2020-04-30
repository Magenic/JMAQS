package com.magenic.jmaqs.utilities.helper.exceptions;

public class ExecutionFailedException extends Exception {

    /**
     * Instantiates the Execution Failed Exception. This exception is thrown
     * when a function that is invoked fails.
     *
     * @param message The message for the exception
     * @param exception The exception that was thrown by the failed event
     */
    public ExecutionFailedException(String message, Exception exception) {
        super(message, exception);
    }
}
