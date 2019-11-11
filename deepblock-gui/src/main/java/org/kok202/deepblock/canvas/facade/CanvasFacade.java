package org.kok202.deepblock.canvas.facade;

import lombok.Data;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.application.content.material.insertion.MaterialInsertionInfoHolder;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.domain.structure.GraphNode;

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

    public static void alignBlockNode(){
        CanvasSingleton.getInstance().getBlockNodeManager().alignBlockNode();
    }

    public static GraphNode<BlockNode> findTrainInputGraphNode(){
        return CanvasSingleton.getInstance().getBlockNodeManager().findTrainInputGraphNode();
    }

    public static boolean isPossibleToAddLayerType(LayerType layerType) {
        switch (layerType){
            case INPUT_LAYER:
                return isPossibleToAddLayerType(LayerType.TRAIN_INPUT_LAYER) &&
                        isPossibleToAddLayerType(LayerType.TEST_INPUT_LAYER);
            case TRAIN_INPUT_LAYER:
                for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes())
                    if(graphNode.getData().getBlockInfo().getLayer().getType().isTrainInputLayerType())
                        return false;
                return true;
            case TEST_INPUT_LAYER:
                for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes())
                    if(graphNode.getData().getBlockInfo().getLayer().getType().isTestInputLayerType())
                        return false;
                return true;
            case OUTPUT_LAYER:
                for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes())
                    if(graphNode.getData().getBlockInfo().getLayer().getType().isOutputLayerType())
                        return false;
                return true;
        }
        return true;
    }

}
