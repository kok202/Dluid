package org.kok202.deepblock.domain.exception;

public class EmptyNodeException extends RuntimeException {
    public EmptyNodeException(){
        super("Requested node doesn't have data.");
    }
}