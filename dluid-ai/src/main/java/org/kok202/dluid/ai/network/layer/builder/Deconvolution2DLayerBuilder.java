package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Deconvolution2D;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.util.BiasInitUtil;
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
        Deconvolution2D.Builder deconvolutionLayerBuilder = (Deconvolution2D.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            deconvolutionLayerBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            deconvolutionLayerBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            deconvolutionLayerBuilder.setStride(layer.getProperties().getStrideSize());
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        Deconvolution2D.Builder deconvolutionLayerBuilder = (Deconvolution2D.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            deconvolutionLayerBuilder.nIn(layer.getProperties().getInputSizeZ()); // channel size
        if(layer.getProperties().getOutputSize() != null)
            deconvolutionLayerBuilder.nOut(layer.getProperties().getOutputSizeZ()); // channel size
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(deconvolutionLayerBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitWrapperUtil.applyWeightInit(deconvolutionLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            deconvolutionLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            deconvolutionLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
