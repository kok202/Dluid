package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

public class OutputLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.OUTPUT_LAYER;
    }

    @Override
    protected BaseLayer.Builder createBuilder(Layer layer) {
        return new OutputLayer.Builder(layer.getProperties().getLossFunction());
    }

    @Override
    protected void setAddOnProperties(Layer layer, BaseLayer.Builder builder) {
        OutputLayer.Builder outputLayerBuilder = (OutputLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, BaseLayer.Builder builder) {
        OutputLayer.Builder outputLayerBuilder = (OutputLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            outputLayerBuilder.nIn(layer.getProperties().getInputSize()[0] * layer.getProperties().getInputSize()[1]);
        if(layer.getProperties().getOutputSize() != null)
            outputLayerBuilder.nOut(layer.getProperties().getOutputSize()[0] * layer.getProperties().getOutputSize()[1]);
        if(layer.getProperties().getWeightInit() != null)
            WeightInitWrapperUtil.applyWeightInit(layer, outputLayerBuilder);
        if(layer.getProperties().getActivationFunction() != null)
            outputLayerBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            outputLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
