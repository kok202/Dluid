package org.kokzoz.dluid.domain.exception;

public class ModelIsNotInitializeException extends RuntimeException {
    public ModelIsNotInitializeException(){
        super("Model is not initialize. You need to initialize model first.");
    }
}
