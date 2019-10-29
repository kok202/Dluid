package org.kok202.deepblock.canvas.entity;

import lombok.Data;

@Data
public class SplitBlockProperty {
    private int[] splitLeftSize;
    private int[] splitRightSize;

    public SplitBlockProperty(){
        splitLeftSize = new int[]{5,1};
        splitRightSize = new int[]{5,1};
    }
}
