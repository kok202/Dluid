package org.kok202.dluid;

import lombok.Data;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.application.content.design.material.insertion.MaterialInsertionInfoHolder;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class CanvasFacade {

    /*************************************************************************************************
     /* Singleton itself
     *************************************************************************************************/
    public static void initialize(){
        CanvasSingleton.getInstance().afterAllWidgetSet();
    }

    /*************************************************************************************************
    /* Canvas -> MainContent
    *************************************************************************************************/
    public static void hoveringListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        CanvasSingleton.getInstance()
                .getMainCanvas()
                .getMainContent()
                .getBlockInsertionHandler()
                .hoveringListener(materialInsertionInfoHolder);
    }

    public static void insertListener(MaterialInsertionInfoHolder materialInsertionInfoHolder){
        CanvasSingleton.getInstance()
                .getMainCanvas()
                .getMainContent()
                .getBlockInsertionHandler()
                .insertListener(materialInsertionInfoHolder);
    }

    /*************************************************************************************************
    /* BlockNodeManager
    *************************************************************************************************/
    public static void notifyLayerDataChanged(long layerId){
        CanvasSingleton.getInstance().getBlockNodeManager().notifyLayerDataChanged(layerId);
    }

    public static void removeGraphNode(long layerId) {
        CanvasSingleton.getInstance().getBlockNodeManager().removeGraphNode(layerId);
    }

    public static GraphNode<BlockNode> findTrainInputGraphNode(){
        return CanvasSingleton.getInstance().getBlockNodeManager().findTrainInputGraphNode();
    }

    public static List<Layer> findIncomingLayers(long layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(layerId)
                .getIncomingNodes()
                .stream()
                .map(graphNode -> graphNode.getData().getBlockInfo().getLayer())
                .collect(Collectors.toList());
    }

    public static List<Layer> findOutgoingLayers(long layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(layerId)
                .getIncomingNodes()
                .stream()
                .map(graphNode -> graphNode.getData().getBlockInfo().getLayer())
                .collect(Collectors.toList());
    }
}
