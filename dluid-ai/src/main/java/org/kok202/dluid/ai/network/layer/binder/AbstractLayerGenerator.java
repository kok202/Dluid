package org.kok202.dluid.ai.network.layer.binder;

import org.deeplearning4j.nn.conf.ComputationGraphConfiguration.GraphBuilder;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractLayerGenerator {

    public abstract boolean support(GraphNode<Layer> currentLayerGraphNode);
    public abstract void generate(GraphNode<Layer> currentLayerGraphNode, GraphBuilder graphBuilder);

    protected static String[] collectFromNodeIds(GraphNode<Layer> layerGraphNode){
        List<GraphNode<Layer>> incomingNodes = layerGraphNode.getIncomingNodes();
        if(incomingNodes.isEmpty())
            return new String[]{};

        List<String> resultStrings = new ArrayList<>();
        for (GraphNode<Layer> incomingNode : incomingNodes) {
            if(incomingNode.getData().getType() == LayerType.RESHAPE_LAYER || incomingNode.getData().getType() == LayerType.PIPE_LAYER){
                resultStrings.addAll(Arrays.asList(collectFromNodeIds(incomingNode)));
            }
            else {
                String nodeId = String.valueOf(incomingNode.getData().getId());
                resultStrings.add(nodeId);
            }
        }
        return resultStrings.toArray(new String[0]);
    }

    protected static String parseCurrentNodeId(GraphNode<Layer> layerGraphNode){
        return String.valueOf(layerGraphNode.getData().getId());
    }

}
