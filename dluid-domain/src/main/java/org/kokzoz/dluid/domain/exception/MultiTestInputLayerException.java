package org.kokzoz.dluid.domain.exception;

public class MultiTestInputLayerException extends RuntimeException {
    public MultiTestInputLayerException(){
        super("You can use only one test input layer.");
    }
}
