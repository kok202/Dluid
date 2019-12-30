package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;

public class Convolution2DLayerBuilder extends AbstractConvolutionLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.CONVOLUTION_2D_LAYER;
    }

    @Override
    protected BaseLayer.Builder createBuilder(Layer layer) {
        return new ConvolutionLayer.Builder();
    }
}
