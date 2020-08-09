package org.kokzoz.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.distribution.UniformDistribution;
import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.deeplearning4j.nn.weights.WeightInit;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.domain.entity.LayerProperties;
import org.kokzoz.dluid.domain.entity.enumerator.WeightInitializer;

public class WeightInitApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof BaseLayer.Builder && layerProperties.getWeightInitializer() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        BaseLayer.Builder baseLayerBuilder = ((BaseLayer.Builder) builder);
        WeightInitializer weightInitializer = layerProperties.getWeightInitializer();
        if(weightInitializer == WeightInitializer.FOLLOW_GLOBAL_SETTING){
            weightInitializer = AIFacade.getTrainWeightInitializer();
        }
        switch(weightInitializer){
            case ZERO:
                baseLayerBuilder.weightInit(WeightInit.ZERO);
                break;
            case ONES:
                baseLayerBuilder.weightInit(WeightInit.ONES);
                break;
            case NORMAL:
                baseLayerBuilder.weightInit(WeightInit.NORMAL);
                break;
            case UNIFORM:
                baseLayerBuilder.weightInit(WeightInit.UNIFORM);
                break;
            case XAVIER:
                baseLayerBuilder.weightInit(WeightInit.XAVIER);
                break;
            case DISTRIBUTION_ZERO_TO_ONE:
                baseLayerBuilder.weightInit(new UniformDistribution(0, 1));
                break;
            case DISTRIBUTION_PLUS_MINUS_ONE:
                baseLayerBuilder.weightInit(new UniformDistribution(-1, 1));
                break;
        }
    }
}
