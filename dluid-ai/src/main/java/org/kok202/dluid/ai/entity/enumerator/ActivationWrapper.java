package org.kok202.dluid.ai.entity.enumerator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.nd4j.linalg.activations.Activation;

@AllArgsConstructor
public enum ActivationWrapper {
    IDENTITY(Activation.IDENTITY),
    LEAKYRELU(Activation.LEAKYRELU),
    RELU(Activation.RELU),
    SIGMOID(Activation.SIGMOID),
    SOFTMAX(Activation.SOFTMAX),
    TANH(Activation.TANH);

    @Getter
    private Activation activation;
}
