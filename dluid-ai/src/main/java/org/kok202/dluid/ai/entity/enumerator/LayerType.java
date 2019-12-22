package org.kok202.dluid.ai.entity.enumerator;

public enum LayerType {
    NULL,
    DENSE_LAYER,
    CONVOLUTION_1D_LAYER,
    CONVOLUTION_2D_LAYER,
    DECONVOLUTION_2D_LAYER,
    BASE_RECURRENT_LAYER,
    LSTM,

    LOSS_LAYER,
    CNN_LOSS_LAYER,
    RNN_LOSS_LAYER,

    OUTPUT_LAYER,
    BATCH_NORMALIZATION,

    /**********************************************************
     * NOT REAL LAYER
     **********************************************************/
    INPUT_LAYER,
    TRAIN_INPUT_LAYER,
    TEST_INPUT_LAYER,
    PIPE_LAYER,
    MERGE_LAYER,
    RESHAPE_LAYER,
    SWITCH_LAYER,

    /**********************************************************
     * NOT SUPPORT
     **********************************************************/
    DROPOUT_LAYER,
    BASE_PRETAIN_NETWORK,
    PRELU_LAYER,

    ELEMENT_WISE_MULTIPLICATION_LAYER,
    EMBEDDING_LAYER,
    EMBEDDING_SEQUENCE_LAYER,
    REPEAT_VECTOR;

    public boolean isInputLayerType(){
        return this == INPUT_LAYER || this == TRAIN_INPUT_LAYER || this == TEST_INPUT_LAYER;
    }

    public boolean isStartLayerType(){
        return isInputLayerType() || this == SWITCH_LAYER;
    }

    public boolean isTrainInputLayerType(){
        return this == INPUT_LAYER || this == TRAIN_INPUT_LAYER;
    }

    public boolean isTestInputLayerType(){
        return this == INPUT_LAYER || this == TEST_INPUT_LAYER;
    }

    public boolean isOutputLayerType(){
        return this == OUTPUT_LAYER;
    }
}