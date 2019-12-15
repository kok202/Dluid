package org.kok202.dluid.domain.exception;

public class MultiInputOutputLayerException extends RuntimeException {
    public MultiInputOutputLayerException(){
        super("There are too many input / output layer");
    }
}
