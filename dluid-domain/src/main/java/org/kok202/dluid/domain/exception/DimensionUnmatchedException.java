package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class DimensionUnmatchedException extends Exception {

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
        this.sourceLayerOutputSize = Stream.of(out).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
        this.destinationLayerId = String.valueOf(destinationLayerId);
        this.destinationInputSize = Stream.of(in).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
    }

}
