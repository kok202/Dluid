package org.kokzoz.dluid.ai.network.layer.builder;

import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;

public class OutputLayerBuilder extends AbstractLayerBuilder {
    @Override
    public boolean support(Layer layer) {
        return layer.getType() == LayerType.OUTPUT_LAYER;
    }

    @Override
    protected Builder createBuilder(Layer layer) {
        return new OutputLayer.Builder();
    }
}
