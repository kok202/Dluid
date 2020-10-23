package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kokzoz.dluid.domain.entity.LayerProperties;

public class DropoutApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof BaseLayer.Builder && layerProperties.getDropout() != 1;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        // dropout = 1 => 100% probability of retaining input
        // dropout = 0 => disallowed
        BaseLayer.Builder baseLayerBuilder = ((BaseLayer.Builder) builder);
        baseLayerBuilder.dropOut(layerProperties.getDropout());
    }
}
