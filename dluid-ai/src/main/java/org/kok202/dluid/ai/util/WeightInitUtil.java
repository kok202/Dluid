package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.entity.enumerator.WeightInitializer;

import java.util.Optional;

public class WeightInitUtil {

    public static void applyWeightInit(BaseLayer.Builder builder, WeightInitializer weightInitializer){
        if(WeightInitUtil.getDistribution(weightInitializer).isPresent())
            builder.weightInit(WeightInitUtil.getDistribution(weightInitializer).get());
        else
            builder.weightInit(WeightInitUtil.get(weightInitializer));
    }

    public static void applyWeightInit(NeuralNetConfiguration.Builder builder, WeightInitializer weightInitializer) {
        if(WeightInitUtil.getDistribution(weightInitializer).isPresent())
            builder.weightInit(WeightInitUtil.getDistribution(weightInitializer).get());
        else
            builder.weightInit(WeightInitUtil.get(weightInitializer));
    }

    private static Optional<UniformDistribution> getDistribution(WeightInitializer weightInitializer){
        if(weightInitializer == WeightInitializer.DISTRIBUTION_ZERO_TO_ONE)
            return Optional.of(new UniformDistribution(0, 1));
        else if(weightInitializer == WeightInitializer.DISTRIBUTION_PLUS_MINUS_ONE)
            return Optional.of(new UniformDistribution(-1, 1));
        return Optional.empty();
    }

    private static WeightInit get(WeightInitializer weightInitializer){
        switch(weightInitializer){
            case ZERO:
                return WeightInit.ZERO;
            case ONES:
                return WeightInit.ONES;
            case NORMAL:
                return WeightInit.NORMAL;
            case UNIFORM:
                return WeightInit.UNIFORM;
            case XAVIER:
                return WeightInit.XAVIER;
            case DISTRIBUTION_ZERO_TO_ONE:
            case DISTRIBUTION_PLUS_MINUS_ONE:
                return WeightInit.DISTRIBUTION;
            case FOLLOW_GLOBAL_SETTING:
                return get(AIFacade.getTrainWeightInitializer());
            default:
                return WeightInit.ZERO;
        }
    }
}
