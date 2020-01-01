package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.LayerBuilderFactory;

public class DefaultLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(Layer layer) {
        return true;
    }

    @Override
    public void generate(Layer layer, ListBuilder neuralNetListBuilder) {
        Builder builder = LayerBuilderFactory.create(layer);
        neuralNetListBuilder.layer(builder.build());
    }

}
