package org.kok202.dluid.ai.util;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.domain.entity.enumerator.WeightInitializer;

public class WeightInitUtil {

    public static void applyWeightInit(BaseLayer.Builder builder, WeightInitializer weightInitializer){
        switch(weightInitializer){
            case ZERO:
                builder.weightInit(WeightInit.ZERO);
                break;
            case ONES:
                builder.weightInit(WeightInit.ONES);
                break;
            case NORMAL:
                builder.weightInit(WeightInit.NORMAL);
                break;
            case UNIFORM:
                builder.weightInit(WeightInit.UNIFORM);
                break;
            case XAVIER:
                builder.weightInit(WeightInit.XAVIER);
                break;
            case DISTRIBUTION_ZERO_TO_ONE:
                builder.weightInit(new UniformDistribution(0, 1));
                break;
            case DISTRIBUTION_PLUS_MINUS_ONE:
                builder.weightInit(new UniformDistribution(-1, 1));
                break;
            case FOLLOW_GLOBAL_SETTING:
                applyWeightInit(builder, AIFacade.getTrainWeightInitializer());
                break;
        }
    }

    public static void applyWeightInit(NeuralNetConfiguration.Builder builder, WeightInitializer weightInitializer) {
        switch(weightInitializer){
            case ZERO:
                builder.weightInit(WeightInit.ZERO);
                break;
            case ONES:
                builder.weightInit(WeightInit.ONES);
                break;
            case NORMAL:
                builder.weightInit(WeightInit.NORMAL);
                break;
            case UNIFORM:
                builder.weightInit(WeightInit.UNIFORM);
                break;
            case XAVIER:
                builder.weightInit(WeightInit.XAVIER);
                break;
            case DISTRIBUTION_ZERO_TO_ONE:
                builder.weightInit(new UniformDistribution(0, 1));
                break;
            case DISTRIBUTION_PLUS_MINUS_ONE:
                builder.weightInit(new UniformDistribution(-1, 1));
                break;
            case FOLLOW_GLOBAL_SETTING:
                applyWeightInit(builder, AIFacade.getTrainWeightInitializer());
                break;
        }
    }
}
