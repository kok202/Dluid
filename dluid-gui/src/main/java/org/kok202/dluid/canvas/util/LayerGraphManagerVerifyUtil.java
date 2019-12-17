package org.kok202.dluid.canvas.util;

import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.stream.Collectors;

public class LayerGraphManagerVerifyUtil {

    public static boolean verify(GraphNode<BlockNode> startGraphNode){
        return true;
    }

    public static boolean verifyMergeBlockNode(BlockNode mergeBlockNode){
        List<Long> sourceLayerIdsOfSwitchBlockNode = CanvasFacade.findGraphNodeByLayerId(mergeBlockNode.getBlockLayer().getId())
                .getIncomingNodes()
                .stream()
                .map(blockNodeGraphNode -> CanvasFacade.findSourceLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                .filter(layerId -> layerId != -1)
                .collect(Collectors.toList());
        return sourceLayerIdsOfSwitchBlockNode.stream().distinct().count() == 1;
    }

    public static boolean verifySwitchBlockNode(BlockNode switchBlockNode){
        List<Long> sourceLayerIdsOfSwitchBlockNode = CanvasFacade.findGraphNodeByLayerId(switchBlockNode.getBlockLayer().getId())
                .getIncomingNodes()
                .stream()
                .map(blockNodeGraphNode -> CanvasFacade.findSourceLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                .filter(layerId -> layerId != -1)
                .collect(Collectors.toList());
        return sourceLayerIdsOfSwitchBlockNode.size() != sourceLayerIdsOfSwitchBlockNode.stream().distinct().count();
    }
}
