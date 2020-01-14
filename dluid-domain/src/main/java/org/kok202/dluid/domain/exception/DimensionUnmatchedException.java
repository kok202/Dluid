package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class DimensionUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String destinationLayerId;
    private String destinationInputSize;

    public DimensionUnmatchedException(String sourceLayerId, int[] out, String destinationLayerId, int[] in) {
        super("LayerType dimension is not matched");
        // TODO : 1차원 레이어인데 [10, 1, 1]로 표기되는걸 고칠 순 없을까?
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(out);
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(in);
    }

}
