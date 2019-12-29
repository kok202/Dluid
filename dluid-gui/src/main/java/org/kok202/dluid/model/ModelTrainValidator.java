package org.kok202.dluid.model;

import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.application.singleton.AppPropertiesSingleton;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.*;

import java.util.Map;

class ModelTrainValidator {

    static void validate(){
        validateDataSetExist();
        validateDataSetDimension();
        validateParameterSetting();
    }

    private static void validateDataSetExist() throws FeatureSetDimensionUnmatchedException {
        for (Map.Entry<Long, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            Long inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            if(dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet() == null)
                throw new CanNotFindFeatureSetException(inputLayerId);
            if(dataSetManager.getManagedResultRecordSet().getNumericRecordSet() == null)
                throw new CanNotFindResultSetException(inputLayerId);
        }
    }

    private static void validateDataSetDimension() throws FeatureSetDimensionUnmatchedException, ResultSetDimensionUnmatchedException {
        BlockNode outputBlockNode = CanvasFacade.findOutputLayer().get().getData();
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

            if(featureSetSize != inputBlockNodeSize)
                throw new FeatureSetDimensionUnmatchedException(inputLayerId, inputBlockNodeSize, featureSetSize);
            if(resultSetSize != outputBlockNodeSize)
                throw new ResultSetDimensionUnmatchedException(outputBlockNode.getBlockLayer().getId(), outputBlockNodeSize, inputLayerId, resultSetSize);
        }
    }

    private static void validateParameterSetting() throws InvalidParameterException, InvalidBatchSize {
        if(AIFacade.getTrainEpoch() <= 0)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.invalidBatchSize.content"));
        if(AIFacade.getTrainLearningRate() <= 0 || AIFacade.getTrainLearningRate() >= 1)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.learningRate.content"));
        if(AIFacade.getTrainTotalRecordSize() <= 0)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.totalRecordSize.content"));
        if(AIFacade.getTrainBatchSize() > AIFacade.getTrainTotalRecordSize())
            throw new InvalidBatchSize(AIFacade.getTrainTotalRecordSize());
        if(AIFacade.getTrainWeightInit() == null)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullWeightInit.content"));
        if(AIFacade.getTrainOptimizer() == null)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.nullOptimizer.content"));
    }
}
