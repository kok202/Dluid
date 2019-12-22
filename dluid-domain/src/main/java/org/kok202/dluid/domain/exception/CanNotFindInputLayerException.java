package org.kok202.dluid.domain.exception;

public class CanNotFindInputLayerException extends RuntimeException {
    public CanNotFindInputLayerException(){
        super("Can not find input layer! At least one input layer must exist.");
    }
}