package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.NeuralNetConfiguration.ListBuilder;
import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.network.layer.LayerBuilderFactory;
import org.kok202.dluid.domain.structure.GraphNode;

public class DefaultLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(GraphNode<Layer> currentLayerGraphNode) {
        return true;
    }

    @Override
    public void generate(GraphNode<Layer> currentLayerGraphNode, ListBuilder neuralNetListBuilder) {
        Builder builder = LayerBuilderFactory.create(currentLayerGraphNode.getData());
        neuralNetListBuilder.layer(builder.build());
    }

}
