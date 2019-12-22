package org.kok202.dluid.domain.exception;

public class MultiTestInputLayerException extends RuntimeException {
    public MultiTestInputLayerException(){
        super("You can use only one test input layer.");
    }
}
