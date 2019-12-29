package org.kok202.dluid.domain.exception;

public class CanNotFindTestInputLayerException extends RuntimeException {
    public CanNotFindTestInputLayerException(){
        super("You need to declare one test input layer.");
    }
}
