package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.*;

import java.util.Map;

public class ModelTrainValidator {

    public static void validate(){
        AppFacade.appendTextOnTrainingLog("Check training possible.");
//        List<GraphNode<BlockNode>> inputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isInputLayerType());
//        List<GraphNode<BlockNode>> outputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType());
//        List<GraphNode<BlockNode>> startGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isStartLayerType());
        validateModelIsChanged();
        validateDataSetExist();
        validateDataSetDimension();
        validateParameterSetting();
        AppFacade.appendTextOnTrainingLog("Check training possible. [Successful]");
    }

    private static void validateModelIsChanged() throws ModelIsChangedException {
        boolean isModelChanged = false;
        // TODO : model 변화 감지
        if(isModelChanged){
            throw new ModelIsChangedException();
        }
    }

    private static void validateDataSetExist() throws FeatureSetDimensionUnmatchedException {
        for (Map.Entry<Long, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            Long inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            if(dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet() == null) {
                printErrorLog(CanNotFindFeatureSetException.class.getSimpleName());
                throw new CanNotFindFeatureSetException(inputLayerId);
            }
            if(dataSetManager.getManagedResultRecordSet().getNumericRecordSet() == null){
                printErrorLog(CanNotFindResultSetException.class.getSimpleName());
                throw new CanNotFindResultSetException(inputLayerId);
            }
        }
    }

    private static void validateDataSetDimension() throws FeatureSetDimensionUnmatchedException, ResultSetDimensionUnmatchedException {
        BlockNode outputBlockNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType()).get(0).getData();
        int outputBlockNodeSize =
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[0] *
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[1];

        for (Map.Entry<Long, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            Long inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            int featureSetSize = dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet().getRecordSize();
            int resultSetSize = dataSetManager.getManagedResultRecordSet().getNumericRecordSet().getRecordSize();
            BlockNode inputBlockNode = CanvasFacade.findGraphNodeByLayerId(inputLayerId).getData();

            int inputBlockNodeSize =
                    inputBlockNode.getBlockLayer().getProperties().getOutputSize()[0] *
                    inputBlockNode.getBlockLayer().getProperties().getOutputSize()[1];

            if(featureSetSize != inputBlockNodeSize) {
                printErrorLog(FeatureSetDimensionUnmatchedException.class.getSimpleName());
                throw new FeatureSetDimensionUnmatchedException(inputLayerId, inputBlockNodeSize, featureSetSize);
            }
            if(resultSetSize != outputBlockNodeSize) {
                printErrorLog(ResultSetDimensionUnmatchedException.class.getSimpleName());
                throw new ResultSetDimensionUnmatchedException(outputBlockNode.getBlockLayer().getId(), outputBlockNodeSize, inputLayerId, resultSetSize);
            }
        }
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
