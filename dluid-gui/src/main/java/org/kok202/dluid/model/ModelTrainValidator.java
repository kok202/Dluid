package org.kok202.dluid.model;

import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.ai.singleton.structure.DataSetManager;
import org.kok202.dluid.canvas.CanvasFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.*;
import org.kok202.dluid.singleton.AppPropertiesSingleton;

import java.util.Map;

class ModelTrainValidator {

    static void validate(){
        validateDataSetExist();
        validateDataSetDimension();
        validateParameterSetting();
    }

    private static void validateDataSetExist() {
        for (Map.Entry<String, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            String inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            if(dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet() == null)
                throw new CanNotFindFeatureSetException(inputLayerId);
            if(dataSetManager.getManagedResultRecordSet().getNumericRecordSet() == null)
                throw new CanNotFindResultSetException(inputLayerId);
        }
    }

    private static void validateDataSetDimension() throws FeatureSetVolumeUnmatchedException, ResultSetVolumeUnmatchedException {
        BlockNode outputBlockNode = CanvasFacade.findOutputLayer().get().getData();
        int outputBlockNodeVolume = outputBlockNode.getBlockLayer().getProperties().getOutputVolume();

        for (Map.Entry<String, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            String inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            int featureSetSize = dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet().getRecordSize();
            int resultSetSize = dataSetManager.getManagedResultRecordSet().getNumericRecordSet().getRecordSize();
            BlockNode inputBlockNode = CanvasFacade.findGraphNodeByLayerId(inputLayerId).getData();

            int inputBlockNodeVolume = inputBlockNode.getBlockLayer().getProperties().getInputVolume();

            if(featureSetSize != inputBlockNodeVolume)
                throw new FeatureSetVolumeUnmatchedException(inputLayerId, inputBlockNodeVolume, featureSetSize);
            if(resultSetSize != outputBlockNodeVolume)
                throw new ResultSetVolumeUnmatchedException(outputBlockNode.getBlockLayer().getId(), outputBlockNodeVolume, outputBlockNode.getBlockLayer().getId(), resultSetSize);
        }
    }

    private static void validateParameterSetting() throws InvalidParameterException {
        if(AIFacade.getTrainEpoch() <= 0)
            throw new InvalidParameterException(AppPropertiesSingleton.getInstance().get("frame.dialog.paramError.invalidBatchSize.content"));
    }
}
