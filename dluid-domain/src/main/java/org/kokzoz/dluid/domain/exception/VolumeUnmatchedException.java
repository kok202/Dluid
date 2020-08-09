package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class VolumeUnmatchedException extends RuntimeException {

    private String sourceLayerId;
    private String sourceLayerOutputSize;
    private String sourceLayerOutputVolume;
    private String destinationLayerId;
    private String destinationInputSize;
    private String destinationInputVolume;

    public VolumeUnmatchedException(String sourceLayerId, int[] out, int outVolume, String destinationLayerId, int[] in, int inVolume) {
        super("LayerType volume is not matched");
        this.sourceLayerId = sourceLayerId;
        this.sourceLayerOutputSize = Arrays.toString(out);
        this.sourceLayerOutputVolume = String.valueOf(outVolume);
        this.destinationLayerId = destinationLayerId;
        this.destinationInputSize = Arrays.toString(in);
        this.destinationInputVolume = String.valueOf(inVolume);
    }

}
