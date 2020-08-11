package org.kokzoz.dluid.domain.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kokzoz.dluid.domain.entity.Dimension;

@Getter
@AllArgsConstructor
public enum DimensionType {
    ONE_DIMENSION(1, false),
    ONE_DIMENSION_WITH_CHANNEL(1, true),
    TWO_DIMENSION(2, false),
    TWO_DIMENSION_WITH_CHANNEL(2, true),
    THREE_DIMENSION(3, false),
    THREE_DIMENSION_WITH_CHANNEL(3, true),;

    private int dimension;
    private boolean hasChannel;
}
