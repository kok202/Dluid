package org.kokzoz.dluid.canvas.singleton.structure;

import javafx.geometry.Point3D;
import lombok.Data;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.canvas.entity.MergeBlockProperty;
import org.kokzoz.dluid.canvas.util.BlockNodeUtil;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;
import org.kokzoz.dluid.domain.structure.GraphManager;
import org.kokzoz.dluid.domain.structure.GraphNode;
import org.kokzoz.dluid.domain.util.MathUtil;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
public class BlockNodeManager extends GraphManager<BlockNode>{


    public void removeGraphNode(String layerId) {
        GraphNode<BlockNode> targetBlockNodeGraphNode = findGraphNodeByLayerId(layerId);
        targetBlockNodeGraphNode.getData().getBlockLayer().delete();

        // Remove all directly connected pipe layer
        Stream.concat(
                targetBlockNodeGraphNode.getIncomingNodes().stream(),
                targetBlockNodeGraphNode.getOutgoingNodes().stream())
                .forEach(blockNodeGraphNode -> {
                    if (blockNodeGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                        removeGraphNode(blockNodeGraphNode.getData().getBlockLayer().getId());
                });

        // Remove me
        removeGraphNode(
                blockNodeObj -> {
                    BlockNode blockNode = (BlockNode) blockNodeObj;
                    return blockNode.getBlockLayer().getId().equals(layerId);
                },
                graphNode -> {
                    GraphNode<BlockNode> blockGraphNode = (GraphNode<BlockNode>) graphNode;
                    blockGraphNode.getData().deleteHexahedrons();
                });

        reshapeAllBlock();
        replaceAllBlock();
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(String layerId) {
        return findGraphNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockLayer().getId().equals(layerId);
        });
    }

    public List<GraphNode<BlockNode>> findAllReachableNode(String layerId) {
        return findAllReachableNode(findGraphNodeByLayerId(layerId));
    }

    public void reshapeBlock(String layerId){
        GraphNode<BlockNode> graphNode = findGraphNodeByLayerId(layerId);
        BlockNode blockNode = graphNode.getData();
        blockNode.reshapeBlockModel();
        reshapeAllBlock();
        replaceAllBlock();
    }

    public void reshapeAllBlock(){
        reshapeAllMergeBlock();
        reshapeAllPipeBlock();
    }

    public void replaceAllBlock(){
        replaceAllBlockByInclinedBlock();
        replaceAllBlockByChannelBlock();
    }

    private void reshapeAllMergeBlock(){
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockLayer().getType() == LayerType.MERGE_LAYER)
                .forEach(graphNodeBlockNode -> {
                    Layer layer = graphNodeBlockNode.getData().getBlockLayer();
                    List<Layer> incomingLayers = CanvasFacade.findIncomingLayers(layer.getId());

                    int inputSize = 0;
                    for (Layer incomingLayer : incomingLayers) {
                        inputSize += incomingLayer.getProperties().getOutput().getVolume();
                    }
                    inputSize = Math.max(inputSize, 1);

                    List<Integer> recommendedDivisors = MathUtil.getDivisors(inputSize);
                    MergeBlockProperty mergeBlockProperty = (MergeBlockProperty) CanvasFacade.findGraphNodeByLayerId(layer.getId()).getData().getBlockInfo().getExtra();
                    int outputSizeY = recommendedDivisors.get(mergeBlockProperty.getPointingIndex(recommendedDivisors.size()));
                    int outputSizeX = inputSize / outputSizeY;
                    layer.getProperties().getInput().setX(outputSizeX);
                    layer.getProperties().getInput().setY(outputSizeY);
                    layer.getProperties().getOutput().setX(outputSizeX);
                    layer.getProperties().getOutput().setY(outputSizeY);
                    graphNodeBlockNode.getData().reshapeBlockModel();
                });
    }

    private void reshapeAllPipeBlock(){
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                .forEach(graphNodeBlockNode -> {
                    GraphNode<BlockNode> sourceGraphNodeBlockNode = graphNodeBlockNode.getIncomingNode().get();
                    GraphNode<BlockNode> destinationGraphNodeBlockNode = graphNodeBlockNode.getOutgoingNode().get(); // Exist only one. because it is pipe block.

                    if(destinationGraphNodeBlockNode.getData().getBlockLayer().getType() == LayerType.MERGE_LAYER){
                        int sourceBlockNodeOutputX = BlockNodeUtil.getBlockNodeOutputX(sourceGraphNodeBlockNode.getData().getBlockLayer());
                        int sourceBlockNodeOutputY = BlockNodeUtil.getBlockNodeOutputY(sourceGraphNodeBlockNode.getData().getBlockLayer());
                        int destinationBlockNodeInputX = BlockNodeUtil.getBlockNodeInputX(destinationGraphNodeBlockNode.getData().getBlockLayer());
                        int destinationBlockNodeInputY = BlockNodeUtil.getBlockNodeInputY(destinationGraphNodeBlockNode.getData().getBlockLayer());
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getInput().setX(sourceBlockNodeOutputX);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getInput().setY(sourceBlockNodeOutputY);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getOutput().setX(destinationBlockNodeInputX);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getOutput().setY(destinationBlockNodeInputY);
                    }
                    else{
                        int sourceBlockNodeOutputX = BlockNodeUtil.getBlockNodeOutputX(sourceGraphNodeBlockNode.getData().getBlockLayer());
                        int sourceBlockNodeOutputY = BlockNodeUtil.getBlockNodeOutputY(sourceGraphNodeBlockNode.getData().getBlockLayer());
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getInput().setX(sourceBlockNodeOutputX);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getInput().setY(sourceBlockNodeOutputY);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getOutput().setX(sourceBlockNodeOutputX);
                        graphNodeBlockNode.getData().getBlockLayer().getProperties().getOutput().setY(sourceBlockNodeOutputY);
                    }
                    graphNodeBlockNode.getData().reshapeBlockModel();
                });
    }

    private void replaceAllBlockByInclinedBlock() {
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockLayer().getType().isInclinedLayerType())
                .forEach(graphNodeBlockNode -> {
                    double incomingSkewedX = 0;
                    double outgoingSkewedX = 0;
                    Optional<GraphNode<BlockNode>> incomingGraphNode = graphNodeBlockNode.getIncomingNode();
                    Optional<GraphNode<BlockNode>> outgoingGraphNode = graphNodeBlockNode.getOutgoingNode();
                    if(incomingGraphNode.isPresent()) {
                        double incomingBottomX = incomingGraphNode.get().getData().getBottomCenterPosition().getX();
                        double currentTopX = graphNodeBlockNode.getData().getTopCenterPosition().getX();
                        incomingSkewedX = incomingBottomX - currentTopX;
                    }
                    if(outgoingGraphNode.isPresent()) {
                        double currentBottomX = graphNodeBlockNode.getData().getBottomCenterPosition().getX();
                        double outgoingTopX = outgoingGraphNode.get().getData().getTopCenterPosition().getX();
                        outgoingSkewedX = currentBottomX - outgoingTopX;
                    }

                    if(incomingSkewedX == 0 && outgoingSkewedX == 0)
                        return;

                    Point3D currentBlockNodePosition = graphNodeBlockNode.getData().getPosition();
                    graphNodeBlockNode.getData().setPosition(currentBlockNodePosition.getX() + incomingSkewedX, currentBlockNodePosition.getY(), currentBlockNodePosition.getZ());

                    final double pushedX = incomingSkewedX + outgoingSkewedX;
                    findAllReachableNode(graphNodeBlockNode.getData().getBlockLayer().getId()).forEach(reachableNode -> {
                        Point3D reachableBlockNodePosition = reachableNode.getData().getPosition();
                        reachableNode.getData().setPosition(reachableBlockNodePosition.getX() + pushedX, reachableBlockNodePosition.getY(), reachableBlockNodePosition.getZ());
                    });
                });
    }

    private void replaceAllBlockByChannelBlock() {
        getGraphNodes()
                .stream()
                .filter(graphNodeBlockNode -> graphNodeBlockNode.getData().getBlockLayer().getProperties().getInput().isHasChannel())
                .forEach(graphNodeBlockNode -> {
                    double incomingOverlappedY = 0;
                    double outgoingOverlappedY = 0;
                    Optional<GraphNode<BlockNode>> incomingGraphNode = graphNodeBlockNode.getIncomingNode();
                    Optional<GraphNode<BlockNode>> outgoingGraphNode = graphNodeBlockNode.getOutgoingNode();
                    if(incomingGraphNode.isPresent()) {
                        double incomingBottomY = incomingGraphNode.get().getData().getBottomCenterPosition().getY();
                        double currentTopY = graphNodeBlockNode.getData().getTopCenterPosition().getY();
                        incomingOverlappedY = incomingBottomY - currentTopY;
                    }
                    if(outgoingGraphNode.isPresent()) {
                        double currentBottomY = graphNodeBlockNode.getData().getBottomCenterPosition().getY();
                        double outgoingTopY = outgoingGraphNode.get().getData().getTopCenterPosition().getY();
                        outgoingOverlappedY = currentBottomY - outgoingTopY;
                    }
                    
                    if(incomingOverlappedY == 0 && outgoingOverlappedY == 0)
                        return;

                    Point3D currentBlockNodePosition = graphNodeBlockNode.getData().getPosition();
                    graphNodeBlockNode.getData().setPosition(currentBlockNodePosition.getX(), currentBlockNodePosition.getY() + incomingOverlappedY, currentBlockNodePosition.getZ());

                    final double pushedY = incomingOverlappedY + outgoingOverlappedY;
                    findAllReachableNode(graphNodeBlockNode.getData().getBlockLayer().getId()).forEach(reachableNode -> {
                        Point3D reachableBlockNodePosition = reachableNode.getData().getPosition();
                        reachableNode.getData().setPosition(reachableBlockNodePosition.getX(), reachableBlockNodePosition.getY() + pushedY, reachableBlockNodePosition.getZ());
                    });
                });
    }

    public List<GraphNode<BlockNode>> findAllGraphNode(Predicate<? super GraphNode<BlockNode>> predicate){
        return getGraphNodes().stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public GraphNode<BlockNode> findStartBlockConnectedWithLayerId(String layerId) {
        GraphNode<BlockNode> startNode = findGraphNodeByLayerId(layerId);
        return findStartBlockConnectedWithLayerIdSearch(startNode);
    }

    private GraphNode<BlockNode> findStartBlockConnectedWithLayerIdSearch(GraphNode<BlockNode> currentNode) {
        if(currentNode.getData().getBlockLayer().getType().isStartLayerType())
            return currentNode;

        List<GraphNode<BlockNode>> incomingNodes = currentNode.getIncomingNodes();
        if(incomingNodes.isEmpty())
            return null;

        for(GraphNode<BlockNode> incomingNode : incomingNodes){
            GraphNode<BlockNode> searchedNode = findStartBlockConnectedWithLayerIdSearch(incomingNode);
            if(searchedNode != null)
                return searchedNode;
        }
        return null;
    }

}
