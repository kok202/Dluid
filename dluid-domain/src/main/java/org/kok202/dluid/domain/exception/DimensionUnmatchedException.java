package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class DimensionUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String destinationLayerId;
    private String destinationInputSize;

    public DimensionUnmatchedException(long sourceLayerId, int sourceLayerOutputSize, long destinationLayerId, int destinationInputSize) {
        super("LayerType dimension is not matched");
        this.sourceLayerId = String.valueOf(sourceLayerId);
        this.sourceLayerOutputSize = String.valueOf(sourceLayerOutputSize);
        this.destinationLayerId = String.valueOf(destinationLayerId);
        this.destinationInputSize = String.valueOf(destinationInputSize);
    }

    public DimensionUnmatchedException(long sourceLayerId, int[] out, long destinationLayerId, int[] in) {
        super("LayerType dimension is not matched");
        this.sourceLayerId = String.valueOf(sourceLayerId);
        this.sourceLayerOutputSize = Arrays.toString(out);
        this.destinationLayerId = String.valueOf(destinationLayerId);
        this.destinationInputSize = Arrays.toString(in);
    }

}
