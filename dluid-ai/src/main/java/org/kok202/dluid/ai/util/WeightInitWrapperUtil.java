package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.WeightInitilaizer;

public class WeightInitWrapperUtil {
    public static void applyWeightInit(Layer layer, BaseLayer.Builder builder){
        if(layer.getProperties().getWeightInit() == WeightInitilaizer.DISTRIBUTION_ZERO_TO_ONE)
            builder.weightInit(new UniformDistribution(0, 1));
        else if(layer.getProperties().getWeightInit() == WeightInitilaizer.DISTRIBUTION_PLUS_MINUS_ONE)
            builder.weightInit(new UniformDistribution(-1, 1));
        else
            builder.weightInit(layer.getProperties().getWeightInit().getWeightInit());
    }
}
