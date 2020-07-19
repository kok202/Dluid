package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.recurrent.SimpleRnn;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class RNNLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.BASE_RECURRENT_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new SimpleRnn.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        SimpleRnn.Builder simpleRnnBuilder = (SimpleRnn.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        SimpleRnn.Builder simpleRnnBuilder = (SimpleRnn.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            simpleRnnBuilder.nIn(layer.getProperties().getInputSizeY());
        if(layer.getProperties().getOutputSize() != null)
            simpleRnnBuilder.nOut(layer.getProperties().getOutputSizeY());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(simpleRnnBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(simpleRnnBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            simpleRnnBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            simpleRnnBuilder.dropOut(layer.getProperties().getDropout());
    }
}
