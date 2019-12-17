package org.kok202.dluid;

import lombok.Data;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
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

    public static List<Layer> findIncomingLayers(long layerId){
        return CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(layerId)
                .getIncomingNodes()
                .stream()
                .map(incomingGraphNode -> {
                    if(incomingGraphNode.getData().getBlockLayer().getType() == LayerType.PIPE_LAYER)
                        return incomingGraphNode.getIncomingNodes().get(0).getData().getBlockLayer();
                    return incomingGraphNode.getData().getBlockLayer();
                })
                .collect(Collectors.toList());
    }

    public static long findSourceLayerIdByLayerId(long layerId){
        GraphNode<BlockNode> sourceLayerGraphNode = CanvasSingleton.getInstance().getBlockNodeManager().findSourceByLayerId(layerId);
        return (sourceLayerGraphNode != null)? sourceLayerGraphNode.getData().getBlockLayer().getId() : -1;
    }

    public static GraphNode<BlockNode> findGraphNodeByLayerId(long layerId){
        return CanvasSingleton.getInstance().getBlockNodeManager().findGraphNodeByLayerId(layerId);
    }

    public static GraphNode<BlockNode> findTrainInputGraphNode(){
        return CanvasSingleton.getInstance().getBlockNodeManager().findTrainInputGraphNode();
    }
}
