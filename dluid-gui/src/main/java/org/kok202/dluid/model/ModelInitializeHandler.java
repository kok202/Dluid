package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.List;

public class ModelInitializeHandler {

    public static void initializeModel(){
        AppFacade.clearTrainingLog();
        AppFacade.appendTextOnTrainingLog("Try to create model.");
        validateModel();
        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
        List<GraphNode<BlockNode>> startGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isStartLayerType());
//        GraphManager<Layer> layerGraphManager = LayerGraphManagerUtil.convertToLayerGraph(CanvasFacade.findTrainInputGraphNode());
//        AIFacade.initializeModel(layerGraphManager);
        logDone();
    }

    private static void validateModel(){
        try {
            BlockNodeGraphValidator.validateInputBlockNodeExist();
            BlockNodeGraphValidator.validateOutputBlockNodeExist();
            BlockNodeGraphValidator.validateAllBlockNodeDimension();
            BlockNodeGraphValidator.validateMergeBlockNode();
            BlockNodeGraphValidator.validateSwitchBlockNode();
        }catch(Exception exception){
            logError(exception.getMessage());
        }
    }

    private static void logError(String message){
        AppFacade.appendTextOnTrainingLog(message);
        AppFacade.appendTextOnTrainingLog("Try to create model. [Fail]");
        throw new RuntimeException(message);
    }

    private static void logDone(){
        AppFacade.appendTextOnTrainingLog("Try to create model. [Successful]");
    }
}
