package org.kok202.deepblock.domain.exception;

public class CanNotFindGraphNodeException extends RuntimeException {
    public CanNotFindGraphNodeException(String message){
        super("Can not find graph node : " + message);
    }
}