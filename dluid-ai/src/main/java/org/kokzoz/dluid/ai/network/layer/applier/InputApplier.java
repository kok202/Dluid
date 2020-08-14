package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.BaseRecurrentLayer;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class InputApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof FeedForwardLayer.Builder) &&
                layerProperties.getInput() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof ConvolutionLayer.BaseConvBuilder){
            ConvolutionLayer.BaseConvBuilder convolutionLayerBuilder = ((ConvolutionLayer.BaseConvBuilder) builder);
            convolutionLayerBuilder.nIn(layerProperties.getInput().getChannel());
        } else if(builder instanceof BaseRecurrentLayer.Builder){
            BaseRecurrentLayer.Builder baseRecurrentLayerBuilder = ((BaseRecurrentLayer.Builder) builder);
            baseRecurrentLayerBuilder.nIn(layerProperties.getInput().getY());
        } else if (builder instanceof RnnOutputLayer.Builder){
            RnnOutputLayer.Builder rnnOutputLayer = ((RnnOutputLayer.Builder) builder);
            rnnOutputLayer.nIn(layerProperties.getInput().getY());
        } else if(builder instanceof FeedForwardLayer.Builder){
            FeedForwardLayer.Builder feedForwardLayerBuilder = ((FeedForwardLayer.Builder) builder);
            feedForwardLayerBuilder.nIn(layerProperties.getInput().getVolume());
        }
    }
}
