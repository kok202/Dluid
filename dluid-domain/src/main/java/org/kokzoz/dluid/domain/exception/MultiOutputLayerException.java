package org.kokzoz.dluid.domain.exception;

public class MultiOutputLayerException extends RuntimeException {
    public MultiOutputLayerException(){
        super("You can use only one output layer.");
    }
}
