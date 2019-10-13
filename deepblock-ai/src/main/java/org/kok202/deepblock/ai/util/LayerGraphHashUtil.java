package org.kok202.deepblock.ai.util;

import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.domain.structure.Graph;
import org.kok202.deepblock.domain.structure.GraphNode;

public class LayerGraphHashUtil {
    public static int getHashCode(Graph<Layer> layerGraph){
        if(layerGraph == null)
            return 0;
        return getLayerGraphNodeHashString(layerGraph.getRoot()).hashCode();
    }

    private static String getLayerGraphNodeHashString(GraphNode<Layer> layerGraphNode){
        if(layerGraphNode == null)
            return "";
        String hashCodeBuffer = "" + layerGraphNode.getData().hashCode();
        for(GraphNode<Layer> child : layerGraphNode.getAdjacentNodes())
            hashCodeBuffer += getLayerGraphNodeHashString(child);
        return hashCodeBuffer;
    }
}
