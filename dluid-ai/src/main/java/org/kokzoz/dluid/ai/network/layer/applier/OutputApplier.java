package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.BaseRecurrentLayer;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.FeedForwardLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class OutputApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof FeedForwardLayer.Builder ||
                builder instanceof ConvolutionLayer.BaseConvBuilder) &&
                layerProperties.getOutput() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof ConvolutionLayer.BaseConvBuilder){
            ConvolutionLayer.BaseConvBuilder convolutionLayerBuilder = ((ConvolutionLayer.BaseConvBuilder) builder);
            convolutionLayerBuilder.nOut(layerProperties.getOutput().getChannel());
        } else if(builder instanceof BaseRecurrentLayer.Builder){
            BaseRecurrentLayer.Builder baseRecurrentLayerBuilder = ((BaseRecurrentLayer.Builder) builder);
            baseRecurrentLayerBuilder.nOut(layerProperties.getOutput().getY());
        } else if (builder instanceof RnnOutputLayer.Builder){
            RnnOutputLayer.Builder rnnOutputLayer = ((RnnOutputLayer.Builder) builder);
            rnnOutputLayer.nOut(layerProperties.getOutput().getY());
        }  else if(builder instanceof FeedForwardLayer.Builder){
            FeedForwardLayer.Builder feedForwardLayerBuilder = ((FeedForwardLayer.Builder) builder);
            feedForwardLayerBuilder.nOut(layerProperties.getOutput().getVolume());
        }
    }
}
