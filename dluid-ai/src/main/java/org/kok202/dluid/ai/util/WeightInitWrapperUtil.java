package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.WeightInitializer;

import java.util.Optional;

public class WeightInitWrapperUtil {

    public static void applyWeightInit(BaseLayer.Builder builder, WeightInitializer weightInitializer){
        weightInitializer = (weightInitializer == WeightInitializer.FOLLOW_GLOBAL_SETTING)?
                AIFacade.getTrainWeightInitializer() :
                weightInitializer;

        if(WeightInitWrapperUtil.getDistribution(weightInitializer).isPresent())
            builder.weightInit(WeightInitWrapperUtil.getDistribution(weightInitializer).get());
        else
            builder.weightInit(WeightInitWrapperUtil.getWeightInit(weightInitializer));
    }

    public static void applyWeightInit(NeuralNetConfiguration.Builder builder, WeightInitializer weightInitializer) {
        if(WeightInitWrapperUtil.getDistribution(weightInitializer).isPresent())
            builder.weightInit(WeightInitWrapperUtil.getDistribution(weightInitializer).get());
        else
            builder.weightInit(WeightInitWrapperUtil.getWeightInit(weightInitializer));
    }

    private static Optional<UniformDistribution> getDistribution(WeightInitializer weightInitializer){
        if(weightInitializer == WeightInitializer.DISTRIBUTION_ZERO_TO_ONE)
            return Optional.of(new UniformDistribution(0, 1));
        else if(weightInitializer == WeightInitializer.DISTRIBUTION_PLUS_MINUS_ONE)
            return Optional.of(new UniformDistribution(-1, 1));
        return Optional.empty();
    }

    private static WeightInit getWeightInit(WeightInitializer weightInitializer){
        if(weightInitializer == WeightInitializer.FOLLOW_GLOBAL_SETTING)
            return AIFacade.getTrainWeightInitializer().getWeightInit();
        return weightInitializer.getWeightInit();
    }

}
