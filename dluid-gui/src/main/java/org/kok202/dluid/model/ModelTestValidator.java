package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.domain.exception.FeatureSetDimensionUnmatchedException;
import org.kok202.dluid.domain.exception.ResultSetDimensionUnmatchedException;

public class ModelTestValidator {

    public static void validate(){
        AppFacade.clearTrainingLog();
        AppFacade.appendTextOnTrainingLog("Check test possible.");
//        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
//        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
//        List<GraphNode<BlockNode>> startGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isStartLayerType());
        validateFeatureSetDimension();
        validateResultSetDimension();
        AppFacade.appendTextOnTrainingLog("Check test possible. [Successful]");
    }

    private static void validateFeatureSetDimension() throws FeatureSetDimensionUnmatchedException {
//        AIFacade.getTrainFeatureSet()
//        throw new FeatureSetDimensionUnmatchedException();
    }

    private static void validateResultSetDimension() throws ResultSetDimensionUnmatchedException {
//        AIFacade.getTestFeatureSet().getNumericRecordSet();
//        AIFacade.getTestResultSet().getNumericRecordSet();
//        throw new ResultSetDimensionUnmatchedException();
    }

    private static void printErrorLog(String message){
        AppFacade.appendTextOnTrainingLog(message);
        AppFacade.appendTextOnTrainingLog("Check training possible. [Fail]");
    }
}
