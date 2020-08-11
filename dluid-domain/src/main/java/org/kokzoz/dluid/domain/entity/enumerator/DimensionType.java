package org.kokzoz.dluid.domain.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.kokzoz.dluid.domain.entity.Dimension;

@Getter
@AllArgsConstructor
public enum DimensionType {
    ONE_DIMENSION(),
    TWO_DIMENSION(),
    TWO_DIMENSION_WITH_CHANNEL(),
    THREE_DIMENSION(),
    THREE_DIMENSION_WITH_CHANNEL(),;
}
