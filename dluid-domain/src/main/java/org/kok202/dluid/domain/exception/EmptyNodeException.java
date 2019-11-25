package org.kok202.dluid.domain.exception;

public class EmptyNodeException extends RuntimeException {
    public EmptyNodeException(){
        super("Requested node doesn't have data.");
    }
}