package org.kok202.dluid.model;

import org.apache.commons.lang3.StringUtils;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.*;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

class ModelGraphValidator {

    static void validateModelIsCorrect(){
        validateModelParamIsValid();
        validateTrainInputBlockNodeExist();
        validateOutputBlockNodeExist();
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

    private static void validateAllBlockNodeDimension() throws DimensionUnmatchedException{
        List<GraphNode<BlockNode>> allGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> true);
        for (GraphNode<BlockNode> currentGraphNode : allGraphNode) {
            long sourceOutputDimension = currentGraphNode.getData().getBlockLayer().getProperties().getOutputDimension();
            long sourceOutputVolume = currentGraphNode.getData().getBlockLayer().getProperties().getOutputVolume();

            List<GraphNode<BlockNode>> currentOutgoingGraphNodes = currentGraphNode.getOutgoingNodes();
            for (GraphNode<BlockNode> currentOutgoingGraphNode : currentOutgoingGraphNodes) {
                long destinationInputDimension = currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputDimension();
                long destinationInputVolume = currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputVolume();
                if (sourceOutputDimension != destinationInputDimension)
                    throw new DimensionUnmatchedReshapeNeededException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputDimension(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getInputDimension(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize());
                if (sourceOutputVolume != destinationInputVolume)
                    throw new DimensionUnmatchedException(
                            currentGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputDimension(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getOutputSize(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getId(),
                            currentGraphNode.getData().getBlockLayer().getProperties().getInputDimension(),
                            currentOutgoingGraphNode.getData().getBlockLayer().getProperties().getInputSize());
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
}
