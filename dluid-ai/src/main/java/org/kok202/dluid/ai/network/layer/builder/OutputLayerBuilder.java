package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.LossFunctionUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class OutputLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.OUTPUT_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new OutputLayer.Builder(LossFunctionUtil.get(layer.getProperties().getLossFunction()));
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        OutputLayer.Builder outputLayerBuilder = (OutputLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        OutputLayer.Builder outputLayerBuilder = (OutputLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            outputLayerBuilder.nIn(layer.getProperties().getInputVolume());
        if(layer.getProperties().getOutputSize() != null)
            outputLayerBuilder.nOut(layer.getProperties().getOutputVolume());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(outputLayerBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(outputLayerBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            outputLayerBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            outputLayerBuilder.dropOut(layer.getProperties().getDropout());
    }
}
