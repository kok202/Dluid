package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.layers.BaseLayer.Builder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.ai.network.layer.LayerBuilderFactory;
import org.kok202.dluid.domain.structure.GraphNode;

public class OutputLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(GraphNode<Layer> currentLayerGraphNode) {
        return currentLayerGraphNode.getData().getType() == LayerType.INPUT_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.TRAIN_INPUT_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.TEST_INPUT_LAYER;
    }

    @Override
    public void generate(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder) {
        Builder builder = LayerBuilderFactory.create(currentLayerGraphNode.getData());
        String currentLayer = parseCurrentNodeId(currentLayerGraphNode);
        String layerFrom = collectFromNodeIds(currentLayerGraphNode)[0];
        graphBuilder.addLayer(currentLayer, builder.build(), layerFrom);
        graphBuilder.setOutputs(currentLayer);
    }

}
