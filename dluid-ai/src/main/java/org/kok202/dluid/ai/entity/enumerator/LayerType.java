package org.kok202.dluid.ai.entity.enumerator;

import lombok.Getter;

public enum LayerType {
    NULL("Null"),
    DENSE_LAYER("FCNN layer"),
    CONVOLUTION_1D_LAYER("1D CNN layer"),
    CONVOLUTION_2D_LAYER("2D CNN layer"),
    DECONVOLUTION_2D_LAYER("2D DeCNN layer"),
    BASE_RECURRENT_LAYER("RNN layer"),
    LSTM("LSTM layer"),

    LOSS_LAYER("Null"),
    CNN_LOSS_LAYER("Null"),
    RNN_LOSS_LAYER("Null"),

    OUTPUT_LAYER("Output layer"),
    BATCH_NORMALIZATION("Batch normalization layer"),

    /**********************************************************
     * NOT REAL LAYER
     **********************************************************/
    INPUT_LAYER("Input layer"),
    PIPE_LAYER("Pipe layer"),
    MERGE_LAYER("Merge layer"), // Deprecated
    RESHAPE_LAYER("Reshape layer"),
    SWITCH_LAYER("Switch layer");

    /**********************************************************
     * NOT SUPPORT
     * DROPOUT_LAYER, BASE_PRETAIN_NETWORK, PRELU_LAYER,
     * ELEMENT_WISE_MULTIPLICATION_LAYER, EMBEDDING_LAYER,
     * EMBEDDING_SEQUENCE_LAYER, REPEAT_VECTOR
     **********************************************************/


    @Getter
    private String readableName;

    LayerType(String readableName) {
        this.readableName = readableName;
    }

    public boolean isInputLayerType(){
        return this == INPUT_LAYER;
    }

    public boolean isStartLayerType(){
        return this == INPUT_LAYER || this == SWITCH_LAYER;
    }

    public boolean isSwitchLayerType(){
        return this == SWITCH_LAYER;
    }

    public boolean isOutputLayerType(){
        return this == OUTPUT_LAYER;
    }

    public boolean isAssistLayerType() {
        return this == PIPE_LAYER || this == SWITCH_LAYER;
    }
}