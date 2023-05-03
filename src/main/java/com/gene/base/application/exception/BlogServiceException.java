package com.gene.base.application.exception;

import lombok.Data;

/**
 * BlogServiceException
 */
@Data
public class BlogServiceException extends RuntimeException {

    String errorMessage;

    int errorCode;

    /**
     * BlogServiceException with error message.
     *
     * @param errorMessage error message
     */
    public BlogServiceException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * BlogServiceException with error message and throwable error
     *
     * @param errorMessage error message
     * @param error        error
     */
    public BlogServiceException(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }

    public BlogServiceException(String errorMessage, int errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }

}
