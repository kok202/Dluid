package org.kok202.deepblock.domain.exception;

public class IllegalLayerRequest extends RuntimeException {
    public IllegalLayerRequest(){
        super("IllegalLayerRequest Exception. type is illegal type type.");
    }
}
