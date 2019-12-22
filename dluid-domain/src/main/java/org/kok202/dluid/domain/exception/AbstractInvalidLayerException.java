package org.kok202.dluid.domain.exception;

import lombok.Getter;

@Getter
public abstract class AbstractInvalidLayerException extends RuntimeException {
    private long layerId;
    public AbstractInvalidLayerException(long layerId, String message){
        super(message);
        this.layerId = layerId;
    }
}
