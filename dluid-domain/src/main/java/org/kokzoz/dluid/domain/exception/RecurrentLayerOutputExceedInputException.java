package org.kokzoz.dluid.domain.exception;


import lombok.Getter;

public class RecurrentLayerOutputExceedInputException extends RuntimeException {
    @Getter
    private String inputSize;
    @Getter
    private String outputSize;

    public RecurrentLayerOutputExceedInputException(int inputSize, int outputSize){
        super("Recurrent layer's output size exceed input size.");
        this.inputSize = String.valueOf(inputSize);
        this.outputSize = String.valueOf(outputSize);
    }
}
