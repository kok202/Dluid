package org.kokzoz.dluid.domain.exception;

public class InvalidImagePropertyException extends RuntimeException {
    public InvalidImagePropertyException(){
        super("Image property is null");
    }
}