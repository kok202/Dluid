package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.util.ActivationUtil;
import org.kok202.dluid.ai.util.BiasInitUtil;
import org.kok202.dluid.ai.util.WeightInitUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.LayerType;

public class LSTMLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.LSTM;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new LSTM.Builder();
    }

    @Override
    protected void setAddOnProperties(Layer layer, Builder builder) {
        LSTM.Builder lstmBuilder = (LSTM.Builder) builder;
    }

    @Override
    protected void setCommonProperties(Layer layer, Builder builder) {
        LSTM.Builder lstmBuilder = (LSTM.Builder) builder;
        if(layer.getProperties().getInputSize() != null)
            lstmBuilder.nIn(layer.getProperties().getInputSizeY());
        if(layer.getProperties().getOutputSize() != null)
            lstmBuilder.nOut(layer.getProperties().getOutputSizeY());
        if(layer.getProperties().getBiasInitializer() != null)
            BiasInitUtil.applyBiasInit(lstmBuilder, layer.getProperties().getBiasInitializer());
        if(layer.getProperties().getWeightInitializer() != null)
            WeightInitUtil.applyWeightInit(lstmBuilder, layer.getProperties().getWeightInitializer());
        if(layer.getProperties().getActivationFunction() != null)
            lstmBuilder.activation(ActivationUtil.get(layer.getProperties().getActivationFunction()));
        if(layer.getProperties().getDropout() != 0)
            lstmBuilder.dropOut(layer.getProperties().getDropout());
    }
}
