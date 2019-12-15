package org.kok202.dluid.domain.exception;


import lombok.Getter;

public class ConvolutionOutputIsNegativeException extends RuntimeException {
    @Getter
    private String outputSize;

    public ConvolutionOutputIsNegativeException(int[] outputSize){
        super("Convolution size is under than 1.");

        String outputSizeString = "";
        for(int outputSizeElement : outputSize){
            outputSizeString += " " + outputSizeElement;
        }
        this.outputSize = outputSizeString;
    }
}
