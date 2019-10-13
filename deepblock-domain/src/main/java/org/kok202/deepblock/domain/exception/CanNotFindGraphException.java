package org.kok202.deepblock.domain.exception;

public class CanNotFindGraphException extends RuntimeException {
    public CanNotFindGraphException(String message){
        super("Can not find graph : " + message);
    }
}