package org.kokzoz.dluid.model;

import org.apache.commons.lang3.StringUtils;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.domain.entity.enumerator.Dimension;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;
import org.kokzoz.dluid.domain.exception.*;
import org.kokzoz.dluid.domain.structure.GraphNode;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ModelGraphValidator {

    static void validateModelIsCorrect(){
        validateModelParamIsValid();
        validateTrainInputBlockNodeExist();
        validateOutputBlockNodeExist();
        validateReachableOutputBlockNode();
        validateAllBlockNodeDimension();
        validateMergeBlockNode();
        validateSwitchBlockNode();
    }

    private static void validateModelParamIsValid() throws InvalidParameterException {
        if(AIFacade.getTrainLearningRate() <= 0 || AIFacade.getTrainLearningRate() >= 1)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.learningRate.content"));
        if(AIFacade.getTrainWeightInitializer() == null)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullWeightInit.content"));
        if(AIFacade.getTrainOptimizer() == null)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullOptimizer.content"));
    }

    private static void validateTrainInputBlockNodeExist() throws RuntimeException{
        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllInputLayer();
        if(inputGraphNode.isEmpty())
            throw new CanNotFindInputLayerException();
    }

    private static void validateOutputBlockNodeExist() throws RuntimeException{
        Optional<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findOutputLayer();
        if(!outputGraphNode.isPresent())
            throw new CanNotFindOutputLayerException();
    }

    private static void validateAllBlockNodeDimension() throws VolumeUnmatchedException {
        List<GraphNode<BlockNode>> allGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> true);
        for (GraphNode<BlockNode> currentGraphNode : allGraphNode) {
            if(currentGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                continue;

            Dimension sourceOutputDimension = currentGraphNode.getData().getBlockLayer().getProperties().getOutputDimension();
            long sourceOutputVolume = currentGraphNode.getData().getBlockLayer().getProperties().getOutputVolume();

            List<GraphNode<BlockNode>> currentOutgoingGraphNodes = currentGraphNode.getOutgoingNodes();
            for (GraphNode<BlockNode> currentOutgoingGraphNode : currentOutgoingGraphNodes) {
                while(currentOutgoingGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER){
                    currentOutgoingGraphNode = currentOutgoingGraphNode.getOutgoingNode().get();
                }
                Dimension destinationInputDimension = currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputDimension();
                long destinationInputVolume = currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputVolume();
                if (sourceOutputDimension != destinationInputDimension)
                    throw new DimensionUnmatchedException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputDimension().getComment(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputDimension().getComment());
                if (sourceOutputVolume != destinationInputVolume)
                    throw new VolumeUnmatchedException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputVolume(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputVolume());
            }
        }
    }

    private static void validateMergeBlockNode() throws InvalidMergeConnectionExistException {
        List<GraphNode<BlockNode>> allMergeGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.MERGE_LAYER);
        for (GraphNode<BlockNode> mergeGraphNode : allMergeGraphNode) {
            List<String> sourceLayerIdsOfMergeBlockNode = mergeGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdConnectedWithLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfMergeBlockNode.stream().distinct().count() != 1)
                throw new InvalidMergeConnectionExistException(mergeGraphNode.getData().getBlockLayer().getId());
        }
    }

    private static void validateSwitchBlockNode() throws InvalidSwitchConnectionExistException {
        List<GraphNode<BlockNode>> allSwitchGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.SWITCH_LAYER);
        for (GraphNode<BlockNode> switchGraphNode : allSwitchGraphNode) {
            List<String> sourceLayerIdsOfSwitchBlockNode = switchGraphNode
                    .getIncomingNodes()
                    .stream()
                    .map(blockNodeGraphNode -> CanvasFacade.findStartLayerIdConnectedWithLayerId(blockNodeGraphNode.getData().getBlockLayer().getId()))
                    .filter(StringUtils::isNotEmpty)
                    .collect(Collectors.toList());
            if(sourceLayerIdsOfSwitchBlockNode.size() != sourceLayerIdsOfSwitchBlockNode.stream().distinct().count())
                throw new InvalidSwitchConnectionExistException(switchGraphNode.getData().getBlockLayer().getId());
        }
    }

    private static void validateReachableOutputBlockNode() throws InvalidSwitchConnectionExistException {
        List<GraphNode<BlockNode>> inputGraphNodes = CanvasFacade.findAllInputLayer();
        Optional<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findOutputLayer();
        for (GraphNode<BlockNode> inputGraphNode : inputGraphNodes) {
            String inputLayerId = inputGraphNode.getData().getBlockLayer().getId();
            String outputLayerId = outputGraphNode.get().getData().getBlockLayer().getId();
            boolean reachable = CanvasFacade.findAllReachableNode(inputGraphNode.getData().getBlockLayer().getId())
                    .parallelStream()
                    .anyMatch(blockNodeGraphNode ->{
                        String blockNodeGraphNodeLayerId = blockNodeGraphNode.getData().getBlockLayer().getId();
                        return blockNodeGraphNodeLayerId.equals(outputLayerId);
                    });
            if(!reachable)
                throw new UnreachableOutputLayerException(inputLayerId);
        }
    }
}
