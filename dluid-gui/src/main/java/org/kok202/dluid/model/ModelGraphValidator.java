package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.*;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.stream.Collectors;

public class ModelGraphValidator {

    public static void validate(){
        validateTrainInputBlockNodeExist();
        validateTestInputBlockNodeExist();
        validateOutputBlockNodeExist();
        validateAllBlockNodeDimension();
        validateMergeBlockNode();
        validateSwitchBlockNode();
    }

    private static void validateTrainInputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isTrainInputLayerType());
        if(inputGraphNode.isEmpty()) {
            printErrorLog(CanNotFindInputLayerException.class.getSimpleName());
            throw new CanNotFindInputLayerException();
        }
    }

    private static void validateTestInputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isTestInputLayerType());
        if(inputGraphNode.isEmpty()) {
            printErrorLog(CanNotFindInputLayerException.class.getSimpleName());
            throw new CanNotFindInputLayerException();
        }
    }

    private static void validateOutputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
        if(outputGraphNode.isEmpty()){
            printErrorLog(CanNotFindOutputLayerException.class.getSimpleName());
            throw new CanNotFindOutputLayerException();
        }
    }

    private static void validateAllBlockNodeDimension() throws DimensionUnmatchedException{
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
                if (sourceOutputSize != destinationInputSize) {
                    printErrorLog(DimensionUnmatchedException.class.getSimpleName());
                    throw new DimensionUnmatchedException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize());
                }
            }
        }
    }

    private static void validateMergeBlockNode() throws InvalidMergeConnectionExistException {
        List<GraphNode<BlockNode>> allMergeGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.MERGE_LAYER);
        for (GraphNode<BlockNode> mergeGraphNode : allMergeGraphNode) {
            List<Long> sourceLayerIdsOfMergeBlockNode = mergeGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(layerId -> layerId != -1)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfMergeBlockNode.stream().distinct().count() != 1) {
                printErrorLog(InvalidMergeConnectionExistException.class.getSimpleName());
                throw new InvalidMergeConnectionExistException(mergeGraphNode.getData().getBlockLayer().getId());
            }
        }
    }

    private static void validateSwitchBlockNode() throws InvalidSwitchConnectionExistException {
        List<GraphNode<BlockNode>> allSwitchGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.SWITCH_LAYER);
        for (GraphNode<BlockNode> switchGraphNode : allSwitchGraphNode) {
            List<Long> sourceLayerIdsOfSwitchBlockNode = switchGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdByLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(layerId -> layerId != -1)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfSwitchBlockNode.size() != sourceLayerIdsOfSwitchBlockNode.stream().distinct().count()) {
                printErrorLog(InvalidSwitchConnectionExistException.class.getSimpleName());
                throw new InvalidSwitchConnectionExistException(switchGraphNode.getData().getBlockLayer().getId());
            }
        }
    }

    private static void printErrorLog(String message){
        AppFacade.appendTextOnTrainingLog(message);
        AppFacade.appendTextOnTrainingLog("Try to create model. [Fail]");
    }
}
