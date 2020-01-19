package org.kok202.dluid.domain.exception;

public class UnreachableOutputLayerException extends AbstractInvalidLayerException {
    public UnreachableOutputLayerException(String layerId) {
        super(layerId, "Input layer can not reach output layer!");
    }
}