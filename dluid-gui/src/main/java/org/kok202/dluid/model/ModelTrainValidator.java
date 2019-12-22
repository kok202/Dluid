package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.domain.exception.FeatureSetDimensionUnmatchedException;
import org.kok202.dluid.domain.exception.InvalidBatchSize;
import org.kok202.dluid.domain.exception.InvalidParameterException;
import org.kok202.dluid.domain.exception.ResultSetDimensionUnmatchedException;

public class ModelTrainValidator {

    public static void validate(){
        AppFacade.clearTrainingLog();
        AppFacade.appendTextOnTrainingLog("Check training possible.");
//        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
//        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
//        List<GraphNode<BlockNode>> startGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isStartLayerType());
        validateFeatureSetDimension();
        validateResultSetDimension();
        validateParameterSetting();
        AppFacade.appendTextOnTrainingLog("Check training possible. [Successful]");
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

    private static void validateParameterSetting() throws InvalidParameterException, InvalidBatchSize {
        if(AIFacade.getTrainEpoch() <= 0){
            printErrorLog(InvalidParameterException.class.getSimpleName());
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.invalidBatchSize.content"));
        }
        if(AIFacade.getTrainLearningRate() <= 0 || AIFacade.getTrainLearningRate() >= 1){
            printErrorLog(InvalidParameterException.class.getSimpleName());
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.learningRate.content"));
        }
        if(AIFacade.getTrainTotalRecordSize() <= 0){
            printErrorLog(InvalidParameterException.class.getSimpleName());
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.totalRecordSize.content"));
        }
        if(AIFacade.getTrainBatchSize() > AIFacade.getTrainTotalRecordSize()){
            printErrorLog(InvalidBatchSize.class.getSimpleName());
            throw new InvalidBatchSize(AIFacade.getTrainTotalRecordSize());
        }
        if(AIFacade.getTrainWeightInit() == null) {
            printErrorLog(InvalidParameterException.class.getSimpleName());
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullWeightInit.content"));
        }
        if(AIFacade.getTrainOptimizer() == null) {
            printErrorLog(InvalidParameterException.class.getSimpleName());
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullOptimizer.content"));
        }
    }

    private static void printErrorLog(String message){
        AppFacade.appendTextOnTrainingLog(message);
        AppFacade.appendTextOnTrainingLog("Check training possible. [Fail]");
    }
}
