package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.deeplearning4j.nn.conf.graph.MergeVertex;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.GraphNode;

public class MergeLayerGenerator extends AbstractLayerGenerator {

    @Override
    public boolean support(GraphNode<Layer> currentLayerGraphNode) {
        return currentLayerGraphNode.getData().getType() == LayerType.MERGE_LAYER;
    }

    @Override
    public void generate(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder) {
        String currentLayer = parseCurrentNodeId(currentLayerGraphNode);
        String[] layerFrom = collectFromNodeIds(currentLayerGraphNode);
        graphBuilder.addVertex(currentLayer, new MergeVertex(), layerFrom);
    }

}
