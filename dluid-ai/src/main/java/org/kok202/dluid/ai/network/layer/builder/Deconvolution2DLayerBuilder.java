package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.Deconvolution2D;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

public class Deconvolution2DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.DECONVOLUTION_2D_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Deconvolution2D.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        ConvolutionLayer.Builder convolutionLayerBuilder = (ConvolutionLayer.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            convolutionLayerBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            convolutionLayerBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            convolutionLayerBuilder.setStride(layer.getProperties().getStrideSize());
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        ConvolutionLayer.Builder convolutionLayerBuilder = (ConvolutionLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            convolutionLayerBuilder.nIn(layer.getProperties().getInputChannelSize());
        if(layer.getProperties().getOutputSize() != null)
            convolutionLayerBuilder.nOut(layer.getProperties().getOutputChannelSize());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitWrapperUtil.applyWeightInit(convolutionLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            convolutionLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            convolutionLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
