package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class DimensionUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String sourceLayerOutputDimension;
    private String destinationLayerId;
    private String destinationInputSize;
    private String sourceLayerInputDimension;

    public DimensionUnmatchedException(String sourceLayerId, int[] out, int outDimension, String destinationLayerId, int[] in, int inDimension) {
        super("LayerType dimension is not matched");
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(out);
        this.sourceLayerOutputDimension = String.valueOf(outDimension);
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(in);
        this.sourceLayerInputDimension = String.valueOf(inDimension);
    }

}
