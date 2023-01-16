package com.virtual.powerplant.exceptionhandlers;

import jakarta.servlet.http.HttpServletRequest;

public interface IExceptionHandler<T extends Exception, R> {

    /**
     * Handles an exception and processes it.
     * @param exception - The exception to handle.
     * @return - The processed result
     */
    R handle(final T exception, final HttpServletRequest request);
}

