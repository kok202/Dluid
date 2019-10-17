package org.kok202.deepblock.canvas.singleton.structure;

import javafx.geometry.Point2D;
import lombok.Data;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.block.mono.MonoBlockNode;
import org.kok202.deepblock.canvas.block.stereo.StereoBlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.domain.exception.CanNotFindTreeNodeException;
import org.kok202.deepblock.domain.structure.TreeManager;
import org.kok202.deepblock.domain.structure.TreeNode;

@Data
public class BlockNodeManager extends TreeManager<BlockNode>{

    public void removeReachableTreeNode(long layerId) {
        removeReachableTreeNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockInfo().getLayer().getId() == layerId;
        });
    }

    public TreeNode<BlockNode> findTestInputTreeNode(){
        for(BlockNode blockNode : getTreeNodeMap().keySet()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if (layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER)
                return getTreeNodeMap().get(blockNode);
        }
        throw new CanNotFindTreeNodeException("Test input block node");
    }

    public TreeNode<BlockNode> findTrainInputTreeNode(){
        for(BlockNode blockNode : getTreeNodeMap().keySet()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER)
                return getTreeNodeMap().get(blockNode);
        }
        throw new CanNotFindTreeNodeException("Train input block node");
    }

    public TreeNode<BlockNode> findTreeNodeByLayerId(long layerId) {
        return findTreeNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockInfo().getLayer().getId() == layerId;
        });
    }

    public void notifyLayerDataChanged(long layerId){
        TreeNode<BlockNode> treeNode = findTreeNodeByLayerId(layerId);
        BlockNode blockNode = treeNode.getData();
        LayerProperties layerProperties = blockNode.getBlockInfo().getLayer().getProperties();

        if(blockNode instanceof MonoBlockNode){
            MonoBlockNode monoBlockNode = (MonoBlockNode) blockNode;
            monoBlockNode.reshapeBlockModel(
                    new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                    new Point2D(layerProperties.getOutputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getOutputSize()[1] * CanvasConstant.NODE_UNIT));
        }
        else if(blockNode instanceof StereoBlockNode){
            StereoBlockNode stereoBlockNode = (StereoBlockNode) blockNode;
            stereoBlockNode.reshapeBlockModel(layerProperties);
        }

        treeNode.getIncomingNodes().forEach(incomingNode -> {
            LayerType parentLayerType = incomingNode.getData().getBlockInfo().getLayer().getType();
            if(parentLayerType.isInputLayerType()) {
                MonoBlockNode parentInputBlockNode = (MonoBlockNode) incomingNode.getData();
                parentInputBlockNode.reshapeBlockModel(
                        new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                        new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT));
            }
        });
    }
}
