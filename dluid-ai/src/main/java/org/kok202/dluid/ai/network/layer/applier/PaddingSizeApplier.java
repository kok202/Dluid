package org.kok202.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling1D;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.kok202.dluid.domain.entity.LayerProperties;

public class PaddingSizeApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof ConvolutionLayer.BaseConvBuilder ||
                builder instanceof Pooling1D.Builder ||
                builder instanceof Pooling2D.Builder) &&
                layerProperties.getPaddingSize() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof ConvolutionLayer.BaseConvBuilder){
            ConvolutionLayer.BaseConvBuilder baseConvBuilder = (ConvolutionLayer.BaseConvBuilder) builder;
            baseConvBuilder.setPadding(layerProperties.getPaddingSize());
        }else if(builder instanceof Pooling1D.Builder){
            Pooling1D.Builder poolingBuilder = (Pooling1D.Builder) builder;
            poolingBuilder.setStride(layerProperties.getPaddingSize());
        }else if(builder instanceof Pooling2D.Builder){
            Pooling2D.Builder poolingBuilder = (Pooling2D.Builder) builder;
            poolingBuilder.setStride(layerProperties.getPaddingSize());
        }
    }
}
