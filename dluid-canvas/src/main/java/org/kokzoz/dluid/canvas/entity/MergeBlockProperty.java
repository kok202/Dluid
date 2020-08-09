package org.kokzoz.dluid.canvas.entity;

import lombok.Data;

@Data
public class MergeBlockProperty extends ExtraBlockProperty {
    private int pointingIndex;

    public int getPointingIndex(int excludeMaxSize){
        pointingIndex = Math.max(pointingIndex, 0);
        pointingIndex = Math.min(pointingIndex, excludeMaxSize - 1);
        return pointingIndex;
    }
}
