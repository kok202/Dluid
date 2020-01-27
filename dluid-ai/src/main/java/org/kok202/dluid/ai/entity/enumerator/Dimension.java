package org.kok202.dluid.ai.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Dimension {
    ONE_DIMENSION(1, false, "1D"),
    TWO_DIMENSION(2, false, "2D"),
    TWO_DIMENSION_WITH_CHANNEL(2, true, "2D with channel"),
    THREE_DIMENSION(3, false, "3D"),
    THREE_DIMENSION_WITH_CHANNEL(3, true,"3D with channel"),;

    private int dimension;
    private boolean hasChannel;
    private String comment;
}
