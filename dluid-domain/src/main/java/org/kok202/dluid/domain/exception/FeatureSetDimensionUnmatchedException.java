package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class FeatureSetDimensionUnmatchedException extends RuntimeException {

    private String inputLayerId;
    private String inputLayerDimension;
    private String featureSetDimension;

    public FeatureSetDimensionUnmatchedException(long inputLayerId, int[] inputLayerDimension, int[] featureSetDimension) {
        super("Feature set dimension is not matched");
        this.inputLayerId = String.valueOf(inputLayerId);
        this.inputLayerDimension = Arrays.toString(inputLayerDimension);
        this.featureSetDimension = Arrays.toString(featureSetDimension);
    }

}
