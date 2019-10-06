package org.kok202.deepblock.domain.exception;

public class InvalidImagePropertyException extends RuntimeException {
    public InvalidImagePropertyException(){
        super("Image property is null");
    }
}