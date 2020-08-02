package org.kok202.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.entity.LayerProperties;
import org.kok202.dluid.domain.entity.enumerator.BiasInitializer;

public class BiasInitApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof BaseLayer.Builder && layerProperties.getBiasInitializer() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        BiasInitializer biasInitializer = layerProperties.getBiasInitializer();
        biasInitializer = (biasInitializer == BiasInitializer.FOLLOW_GLOBAL_SETTING)? AIFacade.getTrainBiasInitializer() : biasInitializer;

        BaseLayer.Builder baseLayerBuilder = ((BaseLayer.Builder) builder);
        baseLayerBuilder.biasInit(biasInitializer.getBias());
    }

}
