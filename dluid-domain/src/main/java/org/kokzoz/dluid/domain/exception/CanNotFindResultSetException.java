package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

@Getter
public class CanNotFindResultSetException extends AbstractInvalidLayerException {

    public CanNotFindResultSetException(String layerId) {
        super(layerId, "Result set is not set.");
    }
}
