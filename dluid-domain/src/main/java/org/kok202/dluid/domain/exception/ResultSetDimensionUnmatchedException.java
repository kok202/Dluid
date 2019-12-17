package org.kok202.dluid.domain.exception;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class ResultSetDimensionUnmatchedException extends Exception {

    private String outputLayerId;
    private String outputLayerDimension;
    private String ResultSetDimension;

    public ResultSetDimensionUnmatchedException(long outputLayerId, int[] outputLayerDimension, int[] ResultSetDimension) {
        super("Result set dimension is not matched");
        this.outputLayerId = String.valueOf(outputLayerId);
        this.outputLayerDimension = Stream.of(outputLayerDimension).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
        this.ResultSetDimension = Stream.of(ResultSetDimension).map(String::valueOf).collect(Collectors.joining(" ")) + "]";
    }

}
