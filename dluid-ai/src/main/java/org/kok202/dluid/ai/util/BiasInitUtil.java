package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.BiasInitializer;

public class BiasInitUtil {

    public static void applyBiasInit(BaseLayer.Builder builder, BiasInitializer biasInitializer){
        biasInitializer = (biasInitializer == BiasInitializer.FOLLOW_GLOBAL_SETTING)? AIFacade.getTrainBiasInitializer() : biasInitializer;
        if(biasInitializer != null)
            builder.biasInit(biasInitializer.getBias());
    }

    public static void applyBiasInit(NeuralNetConfiguration.Builder builder, BiasInitializer biasInitializer) {
        if(biasInitializer != null)
            builder.biasInit(biasInitializer.getBias());
    }

}
