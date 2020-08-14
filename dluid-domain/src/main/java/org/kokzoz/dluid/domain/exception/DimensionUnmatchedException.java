package org.kokzoz.dluid.domain.exception;

import lombok.Getter;
import org.kokzoz.dluid.domain.entity.Dimension;

import java.util.Arrays;

@Getter
public class DimensionUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String sourceLayerOutputDimension;
    private String destinationLayerId;
    private String destinationInputSize;
    private String sourceLayerInputDimension;

    public DimensionUnmatchedException(String sourceLayerId, Dimension output, String destinationLayerId, Dimension input) {
        super("LayerType dimension is not matched");
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(output.asArray());
        this.sourceLayerOutputDimension = output.getType().toString();
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(input.asArray());
        this.sourceLayerInputDimension = input.getType().toString();
    }

}
