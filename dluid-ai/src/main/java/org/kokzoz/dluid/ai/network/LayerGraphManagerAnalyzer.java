package org.kokzoz.dluid.ai.network;

import lombok.Getter;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.exception.CanNotFindLayerException;
import org.kokzoz.dluid.domain.structure.GraphManager;
import org.kokzoz.dluid.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public class LayerGraphManagerAnalyzer {
    private GraphManager<Layer> layerGraphManager;
    private List<String> inputLayerIds;
    private Map<String, Map<String, Layer>> multiLayerManager;

    public LayerGraphManagerAnalyzer() {
        multiLayerManager = new HashMap<>();
    }

    public void analyze(GraphManager<Layer> layerGraphManager){
        this.layerGraphManager = layerGraphManager;
        this.inputLayerIds = layerGraphManager.getDataNodes()
                .stream()
                .filter(layer -> layer.getType().isInputLayerType())
                .map(Layer::getId)
                .collect(Collectors.toList());

        for (String inputLayerId : inputLayerIds) {
            GraphNode<Layer> inputLayerGraphNode = layerGraphManager.findGraphNode(layerGraphNode -> ((Layer)layerGraphNode).getId().equals(inputLayerId));
            List<GraphNode<Layer>> reachableLayerGraphNodes = layerGraphManager.findAllReachableNode(inputLayerGraphNode);
            Map<String, Layer> multiLayersFromInputLayer = Stream.concat(Stream.of(inputLayerGraphNode), reachableLayerGraphNodes.stream())
                    .filter(reachableLayerGraphNode -> !reachableLayerGraphNode.getData().getType().isAssistLayerType())
                    .collect(Collectors.toMap(
                            reachableLayerGraphNode -> reachableLayerGraphNode.getData().getId(),
                            reachableLayerGraphNode -> reachableLayerGraphNode.getData()));

            multiLayerManager.put(inputLayerId, multiLayersFromInputLayer);
        }

    }

    public List<Layer> getLayerFroms(Layer layer, String sourceInputLayerId) {
        GraphNode<Layer> targetLayerGraphNode = null;
        for(GraphNode<Layer> layerGraphNode : layerGraphManager.getGraphNodes()){
            if(layerGraphNode.getData().getId().equals(layer.getId())) {
                targetLayerGraphNode = layerGraphNode;
                break;
            }
        }

        if(targetLayerGraphNode == null)
            throw new CanNotFindLayerException(layer.getId());

        List<Layer> layerFroms = new ArrayList<>();
        for (GraphNode<Layer> parentLayerGraphNode : targetLayerGraphNode.getIncomingNodes()) {

            if(parentLayerGraphNode.getData().getType().isAssistLayerType()){
                layerFroms.addAll(getLayerFroms(parentLayerGraphNode.getData(), sourceInputLayerId));
            }

            // Although following up this parent node, it can not reach the input node that you want to search for.
            // So skip it.
            if(multiLayerManager.get(sourceInputLayerId).get(parentLayerGraphNode.getData().getId()) == null)
                continue;

            layerFroms.add(parentLayerGraphNode.getData());
        }
        return layerFroms;
    }
}
