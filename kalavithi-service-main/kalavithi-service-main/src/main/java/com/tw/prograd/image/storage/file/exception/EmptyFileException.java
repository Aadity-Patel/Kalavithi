package com.tw.prograd.image.storage.file.exception;

public class EmptyFileException extends StorageException {
    public EmptyFileException(String message) {
        super(message);
    }

    public EmptyFileException(String message, Throwable cause) {
        super(message, cause);
    }
}
