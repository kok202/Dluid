package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class RNNOutputLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.RNN_OUTPUT_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new RnnOutputLayer.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        RnnOutputLayer.Builder rnnOutputBuilder = (RnnOutputLayer.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        RnnOutputLayer.Builder rnnOutputBuilder = (RnnOutputLayer.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            rnnOutputBuilder.nIn(layer.getProperties().getInputSizeY());
        if(layer.getProperties().getOutputSize() != null)
            rnnOutputBuilder.nOut(layer.getProperties().getOutputSizeY());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(rnnOutputBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(rnnOutputBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            rnnOutputBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            rnnOutputBuilder.dropOut(layer.getProperties().getDropout());
    }
}
