package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.BaseRecurrentLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

public abstract class AbstractRecurrentLayerBuilder extends AbstractLayerBuilder {
    @Override
    protected void setAddOnProperties(Layer layer, BaseLayer.Builder builder) {
        BaseRecurrentLayer.Builder baseRecurrentLayer = (BaseRecurrentLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, BaseLayer.Builder builder) {
        BaseRecurrentLayer.Builder baseRecurrentLayerBuilder = (BaseRecurrentLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            baseRecurrentLayerBuilder.nIn(layer.getProperties().getInputVolume());
        if(layer.getProperties().getOutputSize() != null)
            baseRecurrentLayerBuilder.nOut(layer.getProperties().getOutputVolume());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitWrapperUtil.applyWeightInit(baseRecurrentLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            baseRecurrentLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            baseRecurrentLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}