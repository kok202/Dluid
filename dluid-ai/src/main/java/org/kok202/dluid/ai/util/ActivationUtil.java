package org.kok202.dluid.ai.util;

import org.kok202.dluid.domain.entity.enumerator.ActivationWrapper;
import org.nd4j.linalg.activations.Activation;

public class ActivationUtil {
    public static Activation get(ActivationWrapper activationWrapper){
        switch (activationWrapper){
            case IDENTITY:
                return Activation.IDENTITY;
            case LEAKYRELU:
                return Activation.LEAKYRELU;
            case RELU:
                return Activation.RELU;
            case SIGMOID:
                return Activation.SIGMOID;
            case SOFTMAX:
                return Activation.SOFTMAX;
            case TANH:
                return Activation.TANH;
            default:
                return Activation.IDENTITY;
        }
    }
}
