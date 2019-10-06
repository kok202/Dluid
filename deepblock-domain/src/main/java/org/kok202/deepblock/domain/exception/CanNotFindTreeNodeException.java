package org.kok202.deepblock.domain.exception;

public class CanNotFindTreeNodeException extends RuntimeException {
    public CanNotFindTreeNodeException(String message){
        super("Can not find tree node : " + message);
    }
}