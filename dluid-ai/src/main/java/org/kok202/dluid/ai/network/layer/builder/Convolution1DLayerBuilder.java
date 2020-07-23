package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Convolution1DLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class Convolution1DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.CONVOLUTION_1D_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Convolution1DLayer.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        Convolution1DLayer.Builder convolutionLayerBuilder = (Convolution1DLayer.Builder) builder;
        if(layer.getProperties().getKernelSize() != null)
            convolutionLayerBuilder.setKernelSize(layer.getProperties().getKernelSize());
        if(layer.getProperties().getPaddingSize() != null)
            convolutionLayerBuilder.setPadding(layer.getProperties().getPaddingSize());
        if(layer.getProperties().getStrideSize() != null)
            convolutionLayerBuilder.setStride(layer.getProperties().getStrideSize());
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        Convolution1DLayer.Builder convolutionLayerBuilder = (Convolution1DLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            convolutionLayerBuilder.nIn(layer.getProperties().getInputSizeY()); // channel size
        if(layer.getProperties().getOutputSize() != null)
            convolutionLayerBuilder.nOut(layer.getProperties().getOutputSizeY()); // channel size
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(convolutionLayerBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(convolutionLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            convolutionLayerBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            convolutionLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
