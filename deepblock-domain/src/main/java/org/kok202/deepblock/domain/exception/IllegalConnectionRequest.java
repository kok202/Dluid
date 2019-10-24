package org.kok202.deepblock.domain.exception;

public class IllegalConnectionRequest extends RuntimeException {
    public IllegalConnectionRequest(){
        super("Source block must be upper than destination block.");
    }
}
