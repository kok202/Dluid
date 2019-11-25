package org.kok202.dluid.canvas.util;

import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.structure.GraphManager;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;

public class BlockNodeGraphUtil {

    // TODO : 테스트 필요
    public static GraphManager<Layer> convertToLayerGraph(GraphNode<BlockNode> startGraphNode){
        Layer startLayerNode = startGraphNode.getData().getBlockInfo().getLayer();
        GraphManager<Layer> graphManager = new GraphManager<>();
        graphManager.registerSoloNode(startLayerNode);
        link(graphManager, startGraphNode, startGraphNode.getOutgoingNodes());
        return graphManager;
    }

    private static void link(GraphManager<Layer> graphManager, GraphNode<BlockNode> from, List<GraphNode<BlockNode>> toList){
        toList.forEach(toGraphNode -> {
            Layer fromLayer = from.getData().getBlockInfo().getLayer();
            Layer toLayer = toGraphNode.getData().getBlockInfo().getLayer();
            graphManager.linkToNewData(fromLayer, toLayer);
            link(graphManager, toGraphNode, toGraphNode.getOutgoingNodes());
        });
    }
}
