package org.kok202.deepblock.domain.exception;

public class CanNotFindBlockNodeException extends RuntimeException {
    public CanNotFindBlockNodeException(String message){
        super("Can not find block node : " + message);
    }
}