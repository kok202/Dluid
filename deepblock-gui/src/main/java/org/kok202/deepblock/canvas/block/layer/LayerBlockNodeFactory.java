package org.kok202.deepblock.canvas.block.layer;

import org.kok202.deepblock.ai.entity.Layer;

public class LayerBlockNodeFactory{
    public static LayerBlockNode create(Layer layer){
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
            case SPLIT_IN_LAYER:
                return new SplitInBlockNode(layer);
            case SPLIT_OUT_LAYER:
                return new SplitOutBlockNode(layer);
        }
        return null;
    }
}
