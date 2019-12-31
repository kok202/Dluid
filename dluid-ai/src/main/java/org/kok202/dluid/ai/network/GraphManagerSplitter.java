package org.kok202.dluid.ai.network;

import lombok.Getter;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class GraphManagerSplitter {

    private long outputLayerId;
    private Map<Long, GraphManager<Layer>> splittedLayerGraphManager;
    private Map<Long, List<Long>> linkageFromTo;

    public GraphManagerSplitter() {
        splittedLayerGraphManager = new HashMap<>();
        linkageFromTo = new HashMap<>();
    }

    public void split(GraphManager<Layer> layerGraphManager){
        GraphNode<Layer> outputLayerGraphNode = layerGraphManager.findGraphNode(layerGraphNode -> ((Layer)layerGraphNode).getType() == LayerType.OUTPUT_LAYER);
        outputLayerId = outputLayerGraphNode.getData().getId();

        GraphManager<Layer> instanceLayerGraphManager = new GraphManager<>();
        instanceLayerGraphManager.registerSoloNode(outputLayerGraphNode.getData());
        long inputLayerId = link(instanceLayerGraphManager, outputLayerGraphNode, outputLayerGraphNode.getIncomingNodes());
        splittedLayerGraphManager.put(inputLayerId, instanceLayerGraphManager);
    }

    private long link(GraphManager<Layer> graphManager, GraphNode<Layer> toGraphNode, List<GraphNode<Layer>> fromGraphNodeList){
        if(toGraphNode.getData().getType().isInputLayerType()){
            return toGraphNode.getData().getId();
        }

        if(toGraphNode.getData().getType().isSwitchLayerType()){
            for(GraphNode<Layer> fromGraphNode : fromGraphNodeList) {
                GraphManager<Layer> instanceLayerGraphManager = new GraphManager<>();
                instanceLayerGraphManager.registerSoloNode(fromGraphNode.getData());

                long instanceInputLayerId = link(instanceLayerGraphManager, fromGraphNode, fromGraphNode.getIncomingNodes());
                splittedLayerGraphManager.put(instanceInputLayerId, instanceLayerGraphManager);
                linkageFromTo.computeIfAbsent(instanceInputLayerId, k -> new ArrayList<>());
                linkageFromTo.get(instanceInputLayerId).add(toGraphNode.getData().getId());
            }
            return toGraphNode.getData().getId();
        }

        long inputLayerId = -1;
        for(GraphNode<Layer> fromGraphNode : fromGraphNodeList) {
            Layer fromLayer = fromGraphNode.getData();
            Layer toLayer = toGraphNode.getData();
            graphManager.linkFromNewData(fromLayer, toLayer);
            inputLayerId = link(graphManager, fromGraphNode, fromGraphNode.getIncomingNodes());
        }
        return inputLayerId;
    }
}
