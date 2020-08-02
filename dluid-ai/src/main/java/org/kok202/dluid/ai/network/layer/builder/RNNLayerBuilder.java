package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.recurrent.SimpleRnn;
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
}
