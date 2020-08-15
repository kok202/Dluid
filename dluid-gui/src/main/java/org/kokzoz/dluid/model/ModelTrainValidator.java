package org.kokzoz.dluid.model;

import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.ai.singleton.structure.DataSetManager;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.domain.exception.*;
import org.kokzoz.dluid.singleton.AppPropertiesSingleton;

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
        int outputBlockNodeVolume = outputBlockNode.getBlockLayer().getProperties().getOutput().getVolume();

        for (Map.Entry<String, DataSetManager> entry : AIFacade.getTrainDataSetManagerMap().entrySet()) {
            String inputLayerId = entry.getKey();
            DataSetManager dataSetManager = entry.getValue();
            int featureSetSize = dataSetManager.getManagedFeatureRecordSet().getNumericRecordSet().getRecordSize();
            int resultSetSize = dataSetManager.getManagedResultRecordSet().getNumericRecordSet().getRecordSize();
            BlockNode inputBlockNode = CanvasFacade.findGraphNodeByLayerId(inputLayerId).getData();

            int inputBlockNodeVolume = inputBlockNode.getBlockLayer().getProperties().getInput().getVolume();

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
