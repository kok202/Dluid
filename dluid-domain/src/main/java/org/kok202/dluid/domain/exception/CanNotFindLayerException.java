package org.kok202.dluid.domain.exception;

public class CanNotFindLayerException extends AbstractInvalidLayerException {
    public CanNotFindLayerException(long layerId) {
        super(layerId, "Can not find layer! Is really exist?");
    }
}