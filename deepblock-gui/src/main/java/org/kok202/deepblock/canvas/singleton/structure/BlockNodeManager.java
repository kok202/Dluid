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
import org.kok202.deepblock.domain.exception.CanNotFindTreeNodeException;
import org.kok202.deepblock.domain.structure.Tree;
import org.kok202.deepblock.domain.structure.TreeNode;
import org.kok202.deepblock.domain.util.ObjectConverter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class BlockNodeManager {
    private List<BlockNode> blockNodeSet;
    private ArrayList<Tree<BlockNode>> blockNodeTrees;

    public BlockNodeManager() {
        blockNodeSet = new ArrayList<>();
        blockNodeTrees = new ArrayList<>();
    }

    public void registerIndependentBlockNode(BlockNode blockNode){
        blockNodeSet.add(blockNode);
        blockNodeTrees.add(new Tree<>(blockNode));
    }

    public void appendFrontToBlockNode(BlockNode targetBlockNode, BlockNode blockNode){
        blockNodeSet.add(blockNode);
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees){
            TreeNode<BlockNode> targetTreeNode = blockNodeTree.find(targetBlockNode);
            if(targetTreeNode != null){
                TreeNode<BlockNode> targetParentTreeNode = targetTreeNode.getParent();
                TreeNode<BlockNode> treeNode = new TreeNode<>(blockNode);
                treeNode.attach(targetTreeNode);
                if(targetParentTreeNode == null)
                    blockNodeTree.setRoot(treeNode);
                else
                    treeNode.setParent(targetParentTreeNode);
                return;
            }
        }
        throw new CanNotFindTreeNodeException(targetBlockNode.toString());
    }

    public void appendBackToBlockNode(BlockNode targetBlockNode, BlockNode blockNode){
        blockNodeSet.add(blockNode);
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees){
            TreeNode<BlockNode> targetTreeNode = blockNodeTree.find(targetBlockNode);
            if(targetTreeNode != null){
                targetTreeNode.attach(blockNode);
                return;
            }
        }
        throw new CanNotFindTreeNodeException(targetBlockNode.toString());
    }

    public void removeBranchByLayerId(long layerId) {
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees) {
            TreeNode<BlockNode> targetBlockNode = findTreeNodeByLayerId(blockNodeTree.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            targetBlockNode.removeWithDescendants(blockNode -> {
                blockNodeSet.remove(blockNode);
                blockNode.deleteBlockModel();
            });
            if(targetBlockNode == blockNodeTree.getRoot()) {
                blockNodeTrees.remove(blockNodeTree);
                return;
            }
        }
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

            if(treeNode.getParent() != null){
                BlockNode parentBlockNode = treeNode.getParent().getData();
                LayerType parentLayerType = parentBlockNode.getBlockInfo().getLayer().getType();
                if(parentLayerType == LayerType.INPUT_LAYER){
                    MonoBlockNode parentInputBlockNode = (MonoBlockNode) treeNode.getParent().getData();
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

    public Tree<BlockNode> findMainInputTree(){
        BlockNode inputBlockNode = null;
        for(BlockNode blockNode : blockNodeSet){
            if(blockNode.getBlockInfo().getLayer().getType() == LayerType.INPUT_LAYER){
                inputBlockNode = blockNode;
                break;
            }
        }
        if(inputBlockNode == null)
            throw new CanNotFindBlockNodeException("input block node");

        for(Tree<BlockNode> blockNodeTree : blockNodeTrees) {
            TreeNode<BlockNode> targetBlockNode = findTreeNodeByLayerId(blockNodeTree.getRoot(), inputBlockNode.getBlockInfo().getLayer().getId());
            if(targetBlockNode == null)
                continue;
            return blockNodeTree;
        }
        throw new CanNotFindBlockNodeException("input block node");
    }

    public TreeNode<BlockNode> findTreeNodeByLayerId(long layerId) {
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees) {
            TreeNode<BlockNode> targetBlockNode = findTreeNodeByLayerId(blockNodeTree.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            return targetBlockNode;
        }
        throw new CanNotFindBlockNodeException(String.valueOf(layerId));
    }

    public BlockNode findBlockNodeByLayerId(long layerId) {
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees) {
            TreeNode<BlockNode> targetBlockNode = findTreeNodeByLayerId(blockNodeTree.getRoot(), layerId);
            if(targetBlockNode == null)
                continue;
            return targetBlockNode.getData();
        }
        throw new CanNotFindBlockNodeException(String.valueOf(layerId));
    }

    private TreeNode<BlockNode> findTreeNodeByLayerId(TreeNode<BlockNode> currentTreeNode, long layerId) {
        if (currentTreeNode == null || currentTreeNode.getChildren() == null)
            return null;

        for(TreeNode<BlockNode> childTreeNode : currentTreeNode.getChildren()) {
            TreeNode<BlockNode> result = findTreeNodeByLayerId(childTreeNode, layerId);
            if(result != null)
                return result;
        }

        if (currentTreeNode.getData().getBlockInfo().getLayer().getId() == layerId)
            return currentTreeNode;
        return null;
    }

    public List<BlockNode> getAllBlockNodeInTree(BlockNode blockNode){
        Tree<BlockNode> startTree = null;
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees){
            TreeNode<BlockNode> targetTreeNode = blockNodeTree.find(blockNode);
            if(targetTreeNode != null){
                startTree = blockNodeTree;
                break;
            }
        }
        if(startTree == null)
            throw new CanNotFindTreeNodeException("Block id : " + blockNode.getBlockInfo().getId());

        List<BlockNode> allWithoutRoot = startTree.getRoot()
                .getAllDescendants()
                .stream()
                .map(blockNodeTreeNode -> blockNodeTreeNode.getData())
                .collect(Collectors.toList());
        allWithoutRoot.add(startTree.getRoot().getData());
        return allWithoutRoot;
    }

    public List<BlockNode> getAllBlockNodeInDescendant(BlockNode blockNode){
        TreeNode<BlockNode> startTreeNode = null;
        for(Tree<BlockNode> blockNodeTree : blockNodeTrees){
            TreeNode<BlockNode> targetTreeNode = blockNodeTree.find(blockNode);
            if(targetTreeNode != null){
                startTreeNode = targetTreeNode;
                break;
            }
        }
        if(startTreeNode == null)
            throw new CanNotFindTreeNodeException("Block id : " + blockNode.getBlockInfo().getId());

        return startTreeNode.getAllDescendants()
                .stream()
                .map(blockNodeTreeNode -> blockNodeTreeNode.getData())
                .collect(Collectors.toList());
    }

    @Deprecated
    public void showManagement(){
        System.out.println(ObjectConverter.toJson(blockNodeSet));
        System.out.println(ObjectConverter.toJson(blockNodeTrees));
    }
}
