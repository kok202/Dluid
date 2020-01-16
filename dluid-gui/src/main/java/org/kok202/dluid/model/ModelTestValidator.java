package org.kok202.dluid.model;

import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.CanNotFindFeatureSetException;
import org.kok202.dluid.domain.exception.FeatureSetDimensionUnmatchedException;
import org.kok202.dluid.domain.exception.ResultSetDimensionUnmatchedException;
import org.kok202.dluid.domain.structure.GraphNode;

class ModelTestValidator {

    static void validate(){
        validateDataSetExist();
        validateDataSetDimension();
    }

    private static void validateDataSetExist() throws FeatureSetDimensionUnmatchedException {
        String testInputLayerId = AppFacade.getTestInputLayerId();
        if(AIFacade.getTestFeatureSet().getNumericRecordSet() == null)
            throw new CanNotFindFeatureSetException(testInputLayerId);
    }

    private static void validateDataSetDimension() throws FeatureSetDimensionUnmatchedException, ResultSetDimensionUnmatchedException {
        int featureSetSize = AIFacade.getTestFeatureSet().getNumericRecordSet().getRecordSize();
        String testInputLayerId = AppFacade.getTestInputLayerId();
        GraphNode<BlockNode> testInputGraphNode = CanvasFacade.findGraphNodeByLayerId(testInputLayerId);
        int inputBlockNodeVolume = testInputGraphNode.getData().getBlockLayer().getProperties().getOutputVolume();

        if(featureSetSize != inputBlockNodeVolume)
            throw new FeatureSetDimensionUnmatchedException(testInputLayerId, inputBlockNodeVolume, featureSetSize);
    }

}
