package org.kokzoz.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling1D;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class Pooling1DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.POOLING_1D;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Pooling1D.Builder();
    }
}
