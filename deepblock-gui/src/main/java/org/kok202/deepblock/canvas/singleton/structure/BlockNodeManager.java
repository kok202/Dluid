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
import org.kok202.deepblock.domain.exception.CanNotFindGraphException;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;
import org.kok202.deepblock.domain.structure.Graph;
import org.kok202.deepblock.domain.structure.GraphNode;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BlockNodeManager {
    private List<BlockNode> blockNodeSet;
    private ArrayList<Graph<BlockNode>> blockNodeGraphs;

    public BlockNodeManager() {
        blockNodeSet = new ArrayList<>();
        blockNodeGraphs = new ArrayList<>();
    }

    public void registerIndependentBlockNode(BlockNode blockNode){
        blockNodeSet.add(blockNode);
        blockNodeGraphs.add(new Graph<>(blockNode));
    }

    public void appendFrontToNewBlockNode(BlockNode targetBlockNode, BlockNode newBlockNode){
        blockNodeSet.add(newBlockNode);
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs){
            GraphNode<BlockNode> targetGraphNode = blockNodeGraph.find(targetBlockNode);
            if(targetGraphNode != null){
                GraphNode<BlockNode> targetParentGraphNode = targetGraphNode.getParent();
                GraphNode<BlockNode> newGraphNode = new GraphNode<>(newBlockNode);
                newGraphNode.attach(targetGraphNode);
                if(targetParentGraphNode == null)
                    blockNodeGraph.setRoot(graphNode);
                else
                    graphNode.setParent(targetParentGraphNode);
                return;
            }
        }
        throw new CanNotFindGraphNodeException(targetBlockNode.toString());
    }

    public void appendBackToBlockNode(BlockNode targetBlockNode, BlockNode blockNode){
        blockNodeSet.add(blockNode);
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs){
            GraphNode<BlockNode> targetGraphNode = blockNodeGraph.find(targetBlockNode);
            if(targetGraphNode != null){
                targetGraphNode.attach(blockNode);
                return;
            }
        }
        throw new CanNotFindGraphNodeException(targetBlockNode.toString());
    }

    public void removeBranchByLayerId(long layerId) {
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs) {
            GraphNode<BlockNode> targetBlockNode = findGraphNodeByLayerId(blockNodeGraph.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            targetBlockNode.removeWithDescendants(blockNode -> {
                blockNodeSet.remove(blockNode);
                blockNode.deleteBlockModel();
            });
            if(targetBlockNode == blockNodeGraph.getRoot()) {
                blockNodeGraphs.remove(blockNodeGraph);
                return;
            }
        }
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

    public Graph<BlockNode> findTestInputGraph(){
        for(BlockNode blockNode : blockNodeSet){
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER){
                return findGraphByLayerId(blockNode.getBlockInfo().getLayer().getId());
            }
        }
        throw new CanNotFindBlockNodeException("Test input block node");
    }

    public Graph<BlockNode> findTrainInputGraph(){
        for(BlockNode blockNode : blockNodeSet){
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER){
                return findGraphByLayerId(blockNode.getBlockInfo().getLayer().getId());
            }
        }
        throw new CanNotFindBlockNodeException("Train input block node");
    }

    public Graph<BlockNode> findGraphByLayerId(long layerId){
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs) {
            GraphNode<BlockNode> targetBlockNode = findGraphNodeByLayerId(blockNodeGraph.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            return blockNodeGraph;
        }
        throw new CanNotFindGraphException(String.valueOf(layerId));
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(long layerId) {
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs) {
            GraphNode<BlockNode> targetBlockNode = findGraphNodeByLayerId(blockNodeGraph.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            return targetBlockNode;
        }
        throw new CanNotFindBlockNodeException(String.valueOf(layerId));
    }

    public BlockNode findBlockNodeByLayerId(long layerId) {
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs) {
            GraphNode<BlockNode> targetBlockNode = findGraphNodeByLayerId(blockNodeGraph.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            return targetBlockNode.getData();
        }
        throw new CanNotFindBlockNodeException(String.valueOf(layerId));
    }

    private GraphNode<BlockNode> findGraphNodeByLayerId(GraphNode<BlockNode> currentGraphNode, long layerId) {
        if (currentGraphNode == null || currentGraphNode.getAdjacentNodes() == null)
            return null;

        for(GraphNode<BlockNode> childGraphNode : currentGraphNode.getAdjacentNodes()) {
            GraphNode<BlockNode> result = findGraphNodeByLayerId(childGraphNode, layerId);
            if(result != null)
                return result;
        }

        if (currentGraphNode.getData().getBlockInfo().getLayer().getId() == layerId)
            return currentGraphNode;
        return null;
    }

    public List<BlockNode> getAllBlockNodeInGraph(BlockNode blockNode){
        Graph<BlockNode> startGraph = null;
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs){
            GraphNode<BlockNode> targetGraphNode = blockNodeGraph.find(blockNode);
            if(targetGraphNode != null){
                startGraph = blockNodeGraph;
                break;
            }
        }
        if(startGraph == null)
            throw new CanNotFindGraphNodeException("Block id : " + blockNode.getBlockInfo().getId());

        List<BlockNode> allWithoutRoot = startGraph.getRoot()
                .getAllDescendants()
                .stream()
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData())
                .collect(Collectors.toList());
        allWithoutRoot.add(startGraph.getRoot().getData());
        return allWithoutRoot;
    }

    public List<BlockNode> getAllBlockNodeInDescendant(BlockNode blockNode){
        GraphNode<BlockNode> startGraphNode = null;
        for(Graph<BlockNode> blockNodeGraph : blockNodeGraphs){
            GraphNode<BlockNode> targetGraphNode = blockNodeGraph.find(blockNode);
            if(targetGraphNode != null){
                startGraphNode = targetGraphNode;
                break;
            }
        }
        if(startGraphNode == null)
            throw new CanNotFindGraphNodeException("Block id : " + blockNode.getBlockInfo().getId());

        return startGraphNode.getAllDescendants()
                .stream()
                .map(blockNodeGraphNode -> blockNodeGraphNode.getData())
                .collect(Collectors.toList());
    }
}
