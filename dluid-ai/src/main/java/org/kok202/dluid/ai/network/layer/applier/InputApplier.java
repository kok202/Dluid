package org.kok202.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.FeedForwardLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.domain.entity.LayerProperties;

public class InputApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof FeedForwardLayer.Builder && layerProperties.getInputSize() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        FeedForwardLayer.Builder feedForwardLayerBuilder = ((FeedForwardLayer.Builder) builder);
        feedForwardLayerBuilder.nIn(layerProperties.getInputVolume());
    }
}
