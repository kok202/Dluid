package org.kok202.dluid.domain.exception;

import lombok.Getter;

@Getter
public class ResultSetVolumeUnmatchedException extends RuntimeException {

    private String outputLayerId;
    private String outputLayerDimension;
    private String inputLayerId;
    private String ResultSetDimension;

    public ResultSetVolumeUnmatchedException(String outputLayerId, int outputLayerDimension, String inputLayerId, int ResultSetDimension) {
        super("Result set dimension is not matched");
        this.outputLayerId = String.valueOf(outputLayerId);
        this.outputLayerDimension = String.valueOf(outputLayerDimension);
        this.inputLayerId = String.valueOf(inputLayerId);
        this.ResultSetDimension = String.valueOf(ResultSetDimension);
    }

}
