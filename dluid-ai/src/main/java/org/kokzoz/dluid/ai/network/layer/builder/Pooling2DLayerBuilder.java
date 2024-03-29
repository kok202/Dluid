package org.kokzoz.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.Pooling2D;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class Pooling2DLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.POOLING_2D;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new Pooling2D.Builder();
    }
}
