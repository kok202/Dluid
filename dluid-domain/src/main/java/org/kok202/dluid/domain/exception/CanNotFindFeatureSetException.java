package org.kok202.dluid.domain.exception;

import lombok.Getter;

@Getter
public class CanNotFindFeatureSetException extends AbstractInvalidLayerException {

    public CanNotFindFeatureSetException(String layerId) {
        super(layerId, "Feature set is not set.");
    }
}
