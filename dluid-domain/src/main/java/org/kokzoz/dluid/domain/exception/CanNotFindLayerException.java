package org.kokzoz.dluid.domain.exception;

public class CanNotFindLayerException extends AbstractInvalidLayerException {
    public CanNotFindLayerException(String layerId) {
        super(layerId, "Can not find layer! Is really exist?");
    }
}