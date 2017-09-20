package com.kyivstar.webcam.exception;

/**
 * Created by igor on 20.09.17.
 */
public class StorageServiceException extends RuntimeException {
    private static final long serialVersionUID = -1425530723413621392L;

    public StorageServiceException(String message) {
        super(message);
    }

    public StorageServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public StorageServiceException(Throwable cause) {
        super(cause);
    }
}
