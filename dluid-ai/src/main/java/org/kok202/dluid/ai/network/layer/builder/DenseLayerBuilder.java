package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

public class DenseLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.DENSE_LAYER;
    }

    @Override
    protected BaseLayer.Builder createBuilder(Layer layer) {
        return new DenseLayer.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, BaseLayer.Builder builder) {
        DenseLayer.Builder denseLayerBuilder = (DenseLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, BaseLayer.Builder builder) {
        DenseLayer.Builder denseLayerBuilder = (DenseLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            denseLayerBuilder.nIn(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1]);
        if(layer.getProperties().getOutputSize() != null)
            denseLayerBuilder.nOut(layer.getProperties().getOutputSize()[0] * layer.getProperties().getOutputSize()[1]);
        if(layer.getProperties().getWeightInit() != null)
            WeightInitWrapperUtil.applyWeightInit(layer, denseLayerBuilder);
        if(layer.getProperties().getActivationFunction() != null)
            denseLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            denseLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
