package org.kok202.dluid.domain.exception;

import lombok.Getter;

@Getter
public class CanNotFindResultSetException extends AbstractInvalidLayerException {

    public CanNotFindResultSetException(long layerId) {
        super(layerId, "Result set is not set.");
    }
}
