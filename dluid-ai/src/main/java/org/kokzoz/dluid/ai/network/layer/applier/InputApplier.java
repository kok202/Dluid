package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class InputApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof FeedForwardLayer.Builder ||
                builder instanceof ConvolutionLayer.BaseConvBuilder) &&
                layerProperties.getInputSize() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof ConvolutionLayer.BaseConvBuilder){
            ConvolutionLayer.BaseConvBuilder convolutionLayerBuilder = ((ConvolutionLayer.BaseConvBuilder) builder);
            convolutionLayerBuilder.nIn(layerProperties.getInputSizeZ());
        }else if(builder instanceof FeedForwardLayer.Builder){
            FeedForwardLayer.Builder feedForwardLayerBuilder = ((FeedForwardLayer.Builder) builder);
            feedForwardLayerBuilder.nIn(layerProperties.getInputVolume());
        }
    }
}
