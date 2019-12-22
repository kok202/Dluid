package org.kok202.dluid.model;

import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.DimensionUnmatchedException;
import org.kok202.dluid.domain.exception.MergeConnectionImpossibleException;
import org.kok202.dluid.domain.exception.SwitchConnectionImpossibleException;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.stream.Collectors;

public class BlockNodeGraphValidator {

    public static void validateInputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
        if(inputGraphNode.isEmpty())
            throw new RuntimeException("Can not find input layer!");
    }

    public static void validateOutputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
        if(outputGraphNode.isEmpty())
            throw new RuntimeException("Can not find output layer!");
    }

    public static void validateAllBlockNodeDimension() throws DimensionUnmatchedException{
        List<GraphNode<BlockNode>> allGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> true);
        for (GraphNode<BlockNode> currentGraphNode : allGraphNode) {
            long sourceOutputSize =
                    currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[0] *
                    currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[1];

            List<GraphNode<BlockNode>> currentOutgoingGraphNodes = currentGraphNode.getOutgoingNodes();
            for (GraphNode<BlockNode> currentOutgoingGraphNode : currentOutgoingGraphNodes) {
                long destinationInputSize =
                        currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize()[0] *
                        currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize()[1];
                if (sourceOutputSize != destinationInputSize)
                    throw new DimensionUnmatchedException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getOutputSize());
            }
        }
    }

    public static void validateMergeBlockNode() throws MergeConnectionImpossibleException{
        List<GraphNode<BlockNode>> allMergeGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.MERGE_LAYER);
        for (GraphNode<BlockNode> mergeGraphNode : allMergeGraphNode) {
            List<Long> sourceLayerIdsOfMergeBlockNode = mergeGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(layerId -> layerId != -1)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfMergeBlockNode.stream().distinct().count() != 1)
                throw new MergeConnectionImpossibleException(mergeGraphNode.getData().getBlockLayer().getId());
        }
    }

    public static void validateSwitchBlockNode() throws SwitchConnectionImpossibleException {
        List<GraphNode<BlockNode>> allSwitchGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.SWITCH_LAYER);
        for (GraphNode<BlockNode> switchGraphNode : allSwitchGraphNode) {
            List<Long> sourceLayerIdsOfSwitchBlockNode = switchGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(layerId -> layerId != -1)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfSwitchBlockNode.size() != sourceLayerIdsOfSwitchBlockNode.stream().distinct().count())
                throw new SwitchConnectionImpossibleException(switchGraphNode.getData().getBlockLayer().getId());
        }
    }
}
