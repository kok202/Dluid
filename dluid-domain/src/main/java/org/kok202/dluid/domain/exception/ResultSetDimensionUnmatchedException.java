package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.Arrays;

@Getter
public class ResultSetDimensionUnmatchedException extends RuntimeException {

    private String outputLayerId;
    private String outputLayerDimension;
    private String ResultSetDimension;

    public ResultSetDimensionUnmatchedException(long outputLayerId, int[] outputLayerDimension, int[] ResultSetDimension) {
        super("Result set dimension is not matched");
        this.outputLayerId = String.valueOf(outputLayerId);
        this.outputLayerDimension = Arrays.toString(outputLayerDimension);
        this.ResultSetDimension = Arrays.toString(ResultSetDimension);
    }

}
