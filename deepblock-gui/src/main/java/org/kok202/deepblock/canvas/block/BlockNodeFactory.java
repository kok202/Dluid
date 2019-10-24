package org.kok202.deepblock.canvas.block;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.mono.*;
import org.kok202.deepblock.canvas.block.stereo.SplitInBlockNode;
import org.kok202.deepblock.canvas.block.stereo.SplitOutBlockNode;
import org.kok202.deepblock.canvas.entity.SplitBlockProperty;

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
            case SPLIT_IN_LAYER:
                SplitBlockProperty splitInBlockProperty = new SplitBlockProperty();
                layer.setExtra(splitInBlockProperty);
                return new SplitInBlockNode(layer);
            case SPLIT_OUT_LAYER:
                SplitBlockProperty splitOutBlockProperty = new SplitBlockProperty();
                layer.setExtra(splitOutBlockProperty);
                return new SplitOutBlockNode(layer);
            case PIPE_LAYER:
                return new PipeBlockNode(layer);
        }
        return null;
    }
}
