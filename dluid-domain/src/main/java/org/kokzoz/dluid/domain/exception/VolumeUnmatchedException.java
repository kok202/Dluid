package org.kokzoz.dluid.domain.exception;

import lombok.Getter;
import org.kokzoz.dluid.domain.entity.Dimension;

import java.util.Arrays;

@Getter
public class VolumeUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String sourceLayerOutputVolume;
    private String destinationLayerId;
    private String destinationInputSize;
    private String destinationInputVolume;

    public VolumeUnmatchedException(String sourceLayerId, Dimension output, String destinationLayerId, Dimension input) {
        super("LayerType volume is not matched");
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(output.asArray());
        this.sourceLayerOutputVolume = String.valueOf(output.getVolume());
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(input.asArray());
        this.destinationInputVolume = String.valueOf(input.getVolume());
    }

}
