package org.kok202.dluid.ai.network.layer.applier;

import org.deeplearning4j.nn.conf.layers.BaseLayer;
import org.deeplearning4j.nn.conf.layers.Layer.Builder;
import org.kok202.dluid.domain.entity.LayerProperties;
import org.kok202.dluid.domain.entity.enumerator.ActivationWrapper;
import org.nd4j.linalg.activations.Activation;

public class ActivationApplier extends AbstractApplier {

    @Override
    public boolean support(Builder builder, LayerProperties layerProperties){
        return builder instanceof BaseLayer.Builder && layerProperties.getActivationFunction() != null;
    }

    @Override
    public void apply(Builder builder, LayerProperties layerProperties){
        BaseLayer.Builder baseLayerBuilder = ((BaseLayer.Builder) builder);
        ActivationWrapper activationWrapper = layerProperties.getActivationFunction();
        switch (activationWrapper){
            case IDENTITY:
                baseLayerBuilder.activation(Activation.IDENTITY);
                break;
            case LEAKYRELU:
                baseLayerBuilder.activation(Activation.LEAKYRELU);
                break;
            case RELU:
                baseLayerBuilder.activation(Activation.RELU);
                break;
            case SIGMOID:
                baseLayerBuilder.activation(Activation.SIGMOID);
                break;
            case SOFTMAX:
                baseLayerBuilder.activation(Activation.SOFTMAX);
                break;
            case TANH:
                baseLayerBuilder.activation(Activation.TANH);
                break;
        }
    }

}
