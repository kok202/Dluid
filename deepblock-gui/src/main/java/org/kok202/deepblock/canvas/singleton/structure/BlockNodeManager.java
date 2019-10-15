package org.kok202.deepblock.canvas.singleton.structure;

import javafx.geometry.Point2D;
import lombok.Data;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.block.mono.MonoBlockNode;
import org.kok202.deepblock.canvas.block.stereo.StereoBlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.domain.exception.CanNotFindBlockNodeException;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;
import org.kok202.deepblock.domain.structure.GraphNode;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BlockNodeManager {
    private Map<BlockNode, GraphNode<BlockNode>> blockNodeMap;

    public BlockNodeManager() {
        blockNodeMap = new HashMap<>();
    }

    public void registerIndependentBlockNode(BlockNode blockNode){
        blockNodeMap.put(blockNode, new GraphNode<>(blockNode));
    }

    public void linkFromNewData(BlockNode destinationBlockNode, BlockNode newBlockNode){
        GraphNode<BlockNode> sourceGraphNode = new GraphNode<>(newBlockNode);
        GraphNode<BlockNode> destinationGraphNode = blockNodeMap.get(destinationBlockNode);
        if(destinationGraphNode == null)
            throw new CanNotFindGraphNodeException(destinationBlockNode.toString());
        destinationGraphNode.createEdgeFrom(sourceGraphNode);
        blockNodeMap.put(newBlockNode, new GraphNode<>(newBlockNode));
    }

    public void linkToNewData(BlockNode sourceBlockNode, BlockNode newBlockNode){
        GraphNode<BlockNode> sourceGraphNode = blockNodeMap.get(sourceBlockNode);
        GraphNode<BlockNode> destinationGraphNode = new GraphNode<>(newBlockNode);
        if(sourceGraphNode == null)
            throw new CanNotFindGraphNodeException(sourceGraphNode.toString());
        sourceGraphNode.createEdgeTo(destinationGraphNode);
    }

    public void removeReachableGraphNodeByLayerId(long layerId) {
        GraphNode<BlockNode> startGraphNode = findGraphNodeByLayerId(layerId);
        startGraphNode.removeAllWithReachableNode();
    }

    public GraphNode<BlockNode> findTestInputGraphNode(){
        for(BlockNode blockNode : blockNodeMap.keySet()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if (layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER)
                return blockNodeMap.get(blockNode);
        }
        throw new CanNotFindBlockNodeException("Test input block node");
    }

    public GraphNode<BlockNode> findTrainInputGraphNode(){
        for(BlockNode blockNode : blockNodeMap.keySet()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER)
                return blockNodeMap.get(blockNode);
        }
        throw new CanNotFindBlockNodeException("Train input block node");
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(long layerId) {
        for(BlockNode blockNode : blockNodeMap.keySet()) {
            if(blockNode.getBlockInfo().getLayer().getId() == layerId)
                return blockNodeMap.get(blockNode);
        }
        throw new CanNotFindBlockNodeException(String.valueOf(layerId));
    }

    public List<BlockNode> getAllLinkedBlockNode(BlockNode selectedBlockNode){
        GraphNode<BlockNode> selectedGraphNode = blockNodeMap.get(selectedBlockNode);
        if(selectedGraphNode == null)
            throw new CanNotFindGraphNodeException(selectedGraphNode.toString());
        return selectedGraphNode.getAllLinkedNodes()
                .stream()
                .map(graphNode -> graphNode.getData())
                .collect(Collectors.toList());
    }

    public void notifyLayerDataChanged(long layerId){
        GraphNode<BlockNode> graphNode = findGraphNodeByLayerId(layerId);
        BlockNode blockNode = graphNode.getData();
        LayerProperties layerProperties = blockNode.getBlockInfo().getLayer().getProperties();

        if(blockNode instanceof MonoBlockNode){
            MonoBlockNode monoBlockNode = (MonoBlockNode) blockNode;
            monoBlockNode.reshapeBlockModel(
                    new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                    new Point2D(layerProperties.getOutputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getOutputSize()[1] * CanvasConstant.NODE_UNIT));

            if(graphNode.getParent() != null){
                BlockNode parentBlockNode = graphNode.getParent().getData();
                LayerType parentLayerType = parentBlockNode.getBlockInfo().getLayer().getType();
                if(parentLayerType.isInputLayerType()){
                    MonoBlockNode parentInputBlockNode = (MonoBlockNode) graphNode.getParent().getData();
                    parentInputBlockNode.reshapeBlockModel(
                            new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                            new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT));
                }
            }
        }
        else if(blockNode instanceof StereoBlockNode){
            StereoBlockNode stereoBlockNode = (StereoBlockNode) blockNode;
            stereoBlockNode.reshapeBlockModel(layerProperties);
            // Split in 의 경우 2개의 layer 를 parent 로 가질 수 있고 parent 가 input 이면 리사이징이 자동으로 되어야한다/
        }
    }
}
