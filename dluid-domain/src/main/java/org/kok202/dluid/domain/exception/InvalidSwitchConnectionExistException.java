package org.kok202.dluid.domain.exception;

public class InvalidSwitchConnectionExistException extends AbstractInvalidLayerException {
    public InvalidSwitchConnectionExistException(long layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
