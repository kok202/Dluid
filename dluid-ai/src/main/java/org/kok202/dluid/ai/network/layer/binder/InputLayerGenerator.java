package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.GraphNode;

public class InputLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(GraphNode<Layer> currentLayerGraphNode) {
        return currentLayerGraphNode.getData().getType() == LayerType.INPUT_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.TRAIN_INPUT_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.TEST_INPUT_LAYER;
    }

    @Override
    public void generate(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder) {
        graphBuilder.addInputs(parseCurrentNodeId(currentLayerGraphNode));
    }

}
