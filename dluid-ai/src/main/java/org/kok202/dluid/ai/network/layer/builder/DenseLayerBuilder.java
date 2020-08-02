package org.kok202.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
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
}
