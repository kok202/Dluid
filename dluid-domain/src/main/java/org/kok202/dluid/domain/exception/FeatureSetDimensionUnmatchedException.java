package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class FeatureSetDimensionUnmatchedException extends Exception {

    private String inputLayerId;
    private String inputLayerDimension;
    private String featureSetDimension;

    public FeatureSetDimensionUnmatchedException(long inputLayerId, int[] inputLayerDimension, int[] featureSetDimension) {
        super("Feature set dimension is not matched");
        this.inputLayerId = String.valueOf(inputLayerId);
        this.inputLayerDimension = Stream.of(inputLayerDimension).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
        this.featureSetDimension = Stream.of(featureSetDimension).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
    }

}
