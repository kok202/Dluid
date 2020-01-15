package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class DimensionUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String destinationLayerId;
    private String destinationInputSize;

    public DimensionUnmatchedException(String sourceLayerId, int outDimension, int[] out, String destinationLayerId, int inDimension, int[] in) {
        super("LayerType dimension is not matched");
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.stream(out, 0, outDimension).toString();
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.stream(in, 0, inDimension).toString();
    }

}
