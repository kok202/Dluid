package org.kok202.deepblock.ai.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.Tree;
import org.kok202.deepblock.domain.structure.TreeNode;

public class LayerTreeHashUtil {
    public static int getHashCode(Tree<Layer> layerTree){
        if(layerTree == null)
            return 0;
        return getLayerTreeNodeHashString(layerTree.getRoot()).hashCode();
    }

    private static String getLayerTreeNodeHashString(TreeNode<Layer> layerTreeNode){
        if(layerTreeNode == null)
            return "";
        String hashCodeBuffer = "" + layerTreeNode.getData().hashCode();
        for(TreeNode<Layer> child : layerTreeNode.getChildren())
            hashCodeBuffer += getLayerTreeNodeHashString(child);
        return hashCodeBuffer;
    }
}
