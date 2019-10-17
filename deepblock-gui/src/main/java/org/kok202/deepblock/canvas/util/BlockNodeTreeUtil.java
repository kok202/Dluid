package org.kok202.deepblock.canvas.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.domain.structure.TreeManager;
import org.kok202.deepblock.domain.structure.TreeNode;

import java.util.List;

public class BlockNodeTreeUtil {

    // TODO : 테스트 필요
    public static TreeManager<Layer> convertToLayerTree(TreeNode<BlockNode> startTreeNode){
        Layer startLayerNode = startTreeNode.getData().getBlockInfo().getLayer();
        TreeManager<Layer> treeManager = new TreeManager<>();
        treeManager.registerSoloNode(startLayerNode);
        link(treeManager, startTreeNode, startTreeNode.getOutgoingNodes());
        return treeManager;
    }

    private static void link(TreeManager<Layer> treeManager, TreeNode<BlockNode> from, List<TreeNode<BlockNode>> toList){
        toList.forEach(toTreeNode -> {
            Layer fromLayer = from.getData().getBlockInfo().getLayer();
            Layer toLayer = toTreeNode.getData().getBlockInfo().getLayer();
            treeManager.linkToNewData(fromLayer, toLayer);
            link(treeManager, toTreeNode, toTreeNode.getOutgoingNodes());
        });
    }
}
