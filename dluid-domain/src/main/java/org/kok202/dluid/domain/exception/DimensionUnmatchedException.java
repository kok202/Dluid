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
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(out);
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(in);
    }

}
