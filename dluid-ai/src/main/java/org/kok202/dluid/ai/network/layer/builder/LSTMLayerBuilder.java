package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.LSTM;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
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
}
