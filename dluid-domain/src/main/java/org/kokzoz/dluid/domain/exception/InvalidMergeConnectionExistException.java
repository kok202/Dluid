package org.kokzoz.dluid.domain.exception;

public class InvalidMergeConnectionExistException extends AbstractInvalidLayerException {
    public InvalidMergeConnectionExistException(String layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
