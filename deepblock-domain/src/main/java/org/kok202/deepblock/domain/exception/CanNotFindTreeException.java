package org.kok202.deepblock.domain.exception;

public class CanNotFindTreeException extends RuntimeException {
    public CanNotFindTreeException(String message){
        super("Can not find tree : " + message);
    }
}