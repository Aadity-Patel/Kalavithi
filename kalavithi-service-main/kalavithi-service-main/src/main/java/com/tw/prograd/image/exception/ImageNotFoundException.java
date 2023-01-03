package com.tw.prograd.image.exception;

public class ImageNotFoundException extends ImageException {

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
