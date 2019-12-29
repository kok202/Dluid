package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.GraphNode;

public class NoopLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(GraphNode<Layer> currentLayerGraphNode) {
        return currentLayerGraphNode.getData().getType() == LayerType.PIPE_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.RESHAPE_LAYER ||
                currentLayerGraphNode.getData().getType() == LayerType.SWITCH_LAYER;
    }

    @Override
    public void generate(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder) {

    }

}
