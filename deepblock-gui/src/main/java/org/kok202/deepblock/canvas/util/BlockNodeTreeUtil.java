package org.kok202.deepblock.canvas.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.domain.structure.Tree;
import org.kok202.deepblock.domain.structure.TreeNode;

public class BlockNodeTreeUtil {

    public static Tree<Layer> convertToLayerTree(Tree<BlockNode> blockNodeTrees){
        TreeNode<Layer> rootLayerTreeNode = convertToLayerTreeNode(blockNodeTrees.getRoot());
        Tree<Layer> layerTree = new Tree<>(rootLayerTreeNode);
        return layerTree;
    }

    private static TreeNode<Layer> convertToLayerTreeNode(TreeNode<BlockNode> treeNode){
        if(treeNode == null)
            return null;

        TreeNode<Layer> parentLayer = new TreeNode<>(treeNode.getData().getBlockInfo().getLayer());
        for(TreeNode<BlockNode> blockNodeTreeNode : treeNode.getChildren()){
            TreeNode<Layer> childTreeNode = convertToLayerTreeNode(blockNodeTreeNode);
            if(childTreeNode != null)
                parentLayer.attach(childTreeNode);
        }
        return parentLayer;
    }
}
