package org.kok202.deepblock.canvas.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.domain.structure.Graph;
import org.kok202.deepblock.domain.structure.GraphNode;

public class BlockNodeGraphUtil {

    public static Graph<Layer> convertToLayerGraph(Graph<BlockNode> blockNodeGraph){
        Graph<Layer> layerGraph = new Graph<>(rootLayerGraphNode);
        return layerGraph;
    }

    private static GraphNode<Layer> convertToLayerGraphNode(GraphNode<BlockNode> graphNode){
        if(graphNode == null)
            return null;

        GraphNode<Layer> parentLayer = new GraphNode<>(graphNode.getData().getBlockInfo().getLayer());
        for(GraphNode<BlockNode> blockNodeGraphNode : graphNode.getAdjacentNodes()){
            GraphNode<Layer> childGraphNode = convertToLayerGraphNode(blockNodeGraphNode);
            if(childGraphNode != null)
                parentLayer.attach(childGraphNode);
        }
        return parentLayer;
    }
}
