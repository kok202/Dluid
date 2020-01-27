package org.kok202.dluid.domain.exception;

// TODO[v2.0.0] : Not a scope of first milestone [v1.0.0]
@Deprecated
public class MultiInputLayerException extends RuntimeException {
    public MultiInputLayerException(){
        super("You can use only one input layer.");
    }
}
