package org.kok202.dluid.domain.exception;

public class InvalidSwitchConnectionExistException extends AbstractInvalidLayerException {
    public InvalidSwitchConnectionExistException(String layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
