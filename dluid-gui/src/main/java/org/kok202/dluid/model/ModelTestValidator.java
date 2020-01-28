package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.CanNotFindFeatureSetException;
import org.kok202.dluid.domain.exception.FeatureSetVolumeUnmatchedException;
import org.kok202.dluid.domain.exception.ResultSetVolumeUnmatchedException;
import org.kok202.dluid.domain.structure.GraphNode;

class ModelTestValidator {

    static void validate(){
        validateDataSetExist();
        validateDataSetDimension();
    }

    private static void validateDataSetExist() throws FeatureSetVolumeUnmatchedException {
        String testInputLayerId = AppFacade.getTestInputLayerId();
        if(AIFacade.getTestFeatureSet().getNumericRecordSet() == null)
            throw new CanNotFindFeatureSetException(testInputLayerId);
    }

    private static void validateDataSetDimension() throws FeatureSetVolumeUnmatchedException, ResultSetVolumeUnmatchedException {
        int featureSetSize = AIFacade.getTestFeatureSet().getNumericRecordSet().getRecordSize();
        String testInputLayerId = AppFacade.getTestInputLayerId();
        GraphNode<BlockNode> testInputGraphNode = CanvasFacade.findGraphNodeByLayerId(testInputLayerId);
        int inputBlockNodeVolume = testInputGraphNode.getData().getBlockLayer().getProperties().getOutputVolume();

        if(featureSetSize != inputBlockNodeVolume)
            throw new FeatureSetVolumeUnmatchedException(testInputLayerId, inputBlockNodeVolume, featureSetSize);
    }

}
