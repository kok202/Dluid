package org.kokzoz.dluid.domain.exception;

import lombok.Getter;

@Getter
public class FeatureSetVolumeUnmatchedException extends RuntimeException {

    private String inputLayerId;
    private String inputLayerDimension;
    private String featureSetDimension;

    public FeatureSetVolumeUnmatchedException(String inputLayerId, int inputLayerDimension, int featureSetDimension) {
        super("Feature set dimension is not matched");
        this.inputLayerId = String.valueOf(inputLayerId);
        this.inputLayerDimension = String.valueOf(inputLayerDimension);
        this.featureSetDimension = String.valueOf(featureSetDimension);
    }

}
