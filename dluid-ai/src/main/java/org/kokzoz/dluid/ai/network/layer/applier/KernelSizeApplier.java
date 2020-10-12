package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling1D;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class KernelSizeApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return (builder instanceof ConvolutionLayer.BaseConvBuilder ||
                builder instanceof Pooling1D.Builder ||
                builder instanceof Pooling2D.Builder) &&
                layerProperties.getKernelSize() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        if(builder instanceof ConvolutionLayer.BaseConvBuilder){
            ConvolutionLayer.BaseConvBuilder baseConvBuilder = (ConvolutionLayer.BaseConvBuilder) builder;
            baseConvBuilder.setKernelSize(layerProperties.getKernelSize());
        }else if(builder instanceof Pooling1D.Builder){
            Pooling1D.Builder poolingBuilder = (Pooling1D.Builder) builder;
            poolingBuilder.setKernelSize(layerProperties.getKernelSize());
        }else if(builder instanceof Pooling2D.Builder){
            Pooling2D.Builder poolingBuilder = (Pooling2D.Builder) builder;
            poolingBuilder.setKernelSize(layerProperties.getKernelSize());
        }
    }
}
