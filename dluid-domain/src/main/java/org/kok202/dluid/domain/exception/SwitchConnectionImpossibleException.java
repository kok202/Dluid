package org.kok202.dluid.domain.exception;

public class SwitchConnectionImpossibleException extends RuntimeException {
    public SwitchConnectionImpossibleException(){
        super("Upward connection is impossible on merge and switch layer.");
    }
}
