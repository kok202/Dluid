package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class DenseLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.DENSE_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new DenseLayer.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        DenseLayer.Builder denseLayerBuilder = (DenseLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        DenseLayer.Builder denseLayerBuilder = (DenseLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            denseLayerBuilder.nIn(layer.getProperties().getInputVolume());
        if(layer.getProperties().getOutputSize() != null)
            denseLayerBuilder.nOut(layer.getProperties().getOutputVolume());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(denseLayerBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(denseLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            denseLayerBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            denseLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
