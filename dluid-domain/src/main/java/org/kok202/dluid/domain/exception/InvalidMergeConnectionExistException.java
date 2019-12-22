package org.kok202.dluid.domain.exception;

public class InvalidMergeConnectionExistException extends AbstractInvalidLayerException {
    public InvalidMergeConnectionExistException(long layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
