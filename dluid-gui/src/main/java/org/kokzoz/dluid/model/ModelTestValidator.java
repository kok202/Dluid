package org.kokzoz.dluid.model;

import org.kokzoz.dluid.AppFacade;
import org.kokzoz.dluid.ai.AIFacade;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.domain.exception.CanNotFindFeatureSetException;
import org.kokzoz.dluid.domain.exception.FeatureSetVolumeUnmatchedException;
import org.kokzoz.dluid.domain.exception.ResultSetVolumeUnmatchedException;
import org.kokzoz.dluid.domain.structure.GraphNode;

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
        int inputBlockNodeVolume = testInputGraphNode.getData().getBlockLayer().getProperties().getOutput().getVolume();

        if(featureSetSize != inputBlockNodeVolume)
            throw new FeatureSetVolumeUnmatchedException(testInputLayerId, inputBlockNodeVolume, featureSetSize);
    }

}
