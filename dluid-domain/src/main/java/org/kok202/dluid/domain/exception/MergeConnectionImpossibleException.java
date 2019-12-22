package org.kok202.dluid.domain.exception;

public class MergeConnectionImpossibleException extends AbstractInvalidLayerException {
    public MergeConnectionImpossibleException(long layerId){
        super(layerId, "Upward connection is impossible on merge and switch layer.");
    }
}
