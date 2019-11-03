package org.kok202.deepblock.canvas.block;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.activation.*;
import org.kok202.deepblock.canvas.block.mono.InputBlockNode;
import org.kok202.deepblock.canvas.block.mono.ReshapeBlockNode;
import org.kok202.deepblock.canvas.block.mono.TestInputBlockNode;
import org.kok202.deepblock.canvas.block.mono.TrainInputBlockNode;

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
            case TRAIN_INPUT_LAYER:
                return new TrainInputBlockNode(layer);
            case TEST_INPUT_LAYER:
                return new TestInputBlockNode(layer);
            case OUTPUT_LAYER:
                return new OutputBlockNode(layer);
            case RESHAPE_LAYER:
                return new ReshapeBlockNode(layer);
        }
        return null;
    }
}
