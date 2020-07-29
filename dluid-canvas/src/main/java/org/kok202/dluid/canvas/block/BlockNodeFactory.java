package org.kok202.dluid.canvas.block;

import org.kok202.dluid.canvas.block.activation.*;
import org.kok202.dluid.canvas.block.plain.*;
import org.kok202.dluid.domain.entity.Layer;

public class BlockNodeFactory {
    public static BlockNode create(Layer layer){
        switch (layer.getType()){
            case DENSE_LAYER:
                return new FeedForwardBlockNode(layer);
            case CONVOLUTION_1D_LAYER:
                return new Convolution1DBlockNode(layer);
            case CONVOLUTION_2D_LAYER:
                return new Convolution2DBlockNode(layer);
            case DECONVOLUTION_2D_LAYER:
                return new Deconvolution2DBlockNode(layer);
            case INPUT_LAYER:
                return new InputBlockNode(layer);
            case OUTPUT_LAYER:
                return new OutputBlockNode(layer);
            case RNN_OUTPUT_LAYER:
                return new RNNOutputBlockNode(layer);
            case PIPE_LAYER:
                return new PipeBlockNode(layer);
            case MERGE_LAYER:
                return new MergeBlockNode(layer);
            case RESHAPE_LAYER:
                return new ReshapeBlockNode(layer);
            case SWITCH_LAYER:
                return new SwitchBlockNode(layer);
            case POOLING_1D:
                return new Pooling1DBlockNode(layer);
            case POOLING_2D:
                return new Pooling2DBlockNode(layer);
            case BATCH_NORMALIZATION:
                return new BatchNormalizationBlockNode(layer);
            case BASE_RECURRENT_LAYER:
                return new RNNBlockNode(layer);
            case LSTM:
                return new LSTMBlockNode(layer);
        }
        return null;
    }
}
