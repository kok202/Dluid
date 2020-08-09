package org.kokzoz.dluid.model;

import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.structure.GraphManager;
import org.kokzoz.dluid.domain.structure.GraphNode;

import java.util.List;

class ModelGraphConverter {

    // Q. Why start at output block node?
    // A. Because output block node is guaranteed unique in canvas.
    static GraphManager<Layer> convertToLayerGraph(GraphNode<BlockNode> outputBlockNodeGraphNode){
        GraphManager<Layer> graphManager = new GraphManager<>();
        graphManager.registerSoloNode(outputBlockNodeGraphNode.getData().getBlockLayer());
        link(graphManager, outputBlockNodeGraphNode, outputBlockNodeGraphNode.getIncomingNodes());
        return graphManager;
    }

    private static void link(GraphManager<Layer> graphManager, GraphNode<BlockNode> toGraphNode, List<GraphNode<BlockNode>> fromGraphNodeList){
        fromGraphNodeList.forEach(fromGraphNode -> {
            Layer fromLayer = fromGraphNode.getData().getBlockLayer();
            Layer toLayer = toGraphNode.getData().getBlockLayer();
            graphManager.linkFromNewData(fromLayer, toLayer);
            link(graphManager, fromGraphNode, fromGraphNode.getIncomingNodes());
        });
    }
}
