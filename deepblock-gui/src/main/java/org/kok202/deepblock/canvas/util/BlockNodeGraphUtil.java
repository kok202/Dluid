package org.kok202.deepblock.canvas.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.domain.structure.GraphManager;
import org.kok202.deepblock.domain.structure.GraphNode;

import java.util.List;

public class BlockNodeGraphUtil {

    public static GraphManager<Layer> convertToLayerGraph(GraphNode<BlockNode> startGraphNode){
        Layer startLayerNode = startGraphNode.getData().getBlockInfo().getLayer();
        GraphManager<Layer> graphManager = new GraphManager<>();
        graphManager.registerSoloNode(startLayerNode);
        link(graphManager, startGraphNode, startGraphNode.getOutgoingNodes());
        return graphManager;
    }

    private static void link(GraphManager<Layer> graphManager, GraphNode<BlockNode> from, List<GraphNode<BlockNode>> toList){
        toList.stream().forEach(toGraphNode -> {
            Layer fromLayer = from.getData().getBlockInfo().getLayer();
            Layer toLayer = toGraphNode.getData().getBlockInfo().getLayer();
            graphManager.linkToNewData(fromLayer, toLayer);
            link(graphManager, toGraphNode, toGraphNode.getOutgoingNodes());
        });
    }
}
