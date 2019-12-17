package org.kok202.dluid.domain.exception;

public class MergeConnectionImpossibleException extends RuntimeException {
    public MergeConnectionImpossibleException(){
        super("Upward connection is impossible on merge and switch layer.");
    }
}
