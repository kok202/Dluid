package org.kok202.dluid.ai.network;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.deeplearning4j.nn.api.Layer;

@Getter
@Builder
@AllArgsConstructor
public class NeuralNetLayer {
    long sequence;
    Layer layer;
}
