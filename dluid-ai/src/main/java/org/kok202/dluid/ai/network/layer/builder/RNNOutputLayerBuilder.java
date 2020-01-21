package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.RnnOutputLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitWrapperUtil;

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
            rnnOutputBuilder.nIn(layer.getProperties().getInputVolume());
        if(layer.getProperties().getOutputSize() != null)
            rnnOutputBuilder.nOut(layer.getProperties().getOutputVolume());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(rnnOutputBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitWrapperUtil.applyWeightInit(rnnOutputBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            rnnOutputBuilder.activation(layer.getProperties().getActivationFunction().getActivation());
        if(layer.getProperties().getDropout() != 0)
            rnnOutputBuilder.dropOut(layer.getProperties().getDropout());
    }
}
