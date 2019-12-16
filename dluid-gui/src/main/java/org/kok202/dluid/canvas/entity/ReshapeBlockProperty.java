package org.kok202.dluid.canvas.entity;

import lombok.Data;

@Data
public class ReshapeBlockProperty extends SkewedBlockProperty{
    private int pointingIndex;

    public int getPointingIndex(int excludeMaxSize){
        pointingIndex = Math.max(pointingIndex, 0);
        pointingIndex = Math.min(pointingIndex, excludeMaxSize - 1);
        return pointingIndex;
    }
}
