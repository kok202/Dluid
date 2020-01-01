package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.kok202.dluid.ai.entity.Layer;

public abstract class AbstractLayerGenerator {

    public abstract boolean support(Layer layer);
    public abstract void generate(Layer layer, ListBuilder neuralNetBuilder);

}
