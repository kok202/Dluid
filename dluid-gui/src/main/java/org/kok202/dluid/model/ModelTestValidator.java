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
        long testInputLayerId = AppFacade.getTestInputLayerId();
        if(AIFacade.getTestFeatureSet().getNumericRecordSet() == null)
            throw new CanNotFindFeatureSetException(testInputLayerId);
    }

    private static void validateDataSetDimension() throws FeatureSetDimensionUnmatchedException, ResultSetDimensionUnmatchedException {
        BlockNode outputBlockNode = CanvasFacade.findOutputLayer().get().getData();
        int outputBlockNodeSize =
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[0] *
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[1];

        int featureSetSize = AIFacade.getTestFeatureSet().getNumericRecordSet().getRecordSize();
        int resultSetSize = AIFacade.getTestResultSet().getNumericRecordSet().getRecordSize();

        long testInputLayerId = AppFacade.getTestInputLayerId();
        GraphNode<BlockNode> testInputGraphNode = CanvasFacade.findGraphNodeByLayerId(testInputLayerId);
        int inputBlockNodeSize =
                testInputGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[0] *
                testInputGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[1];

        if(featureSetSize != inputBlockNodeSize)
            throw new FeatureSetDimensionUnmatchedException(testInputLayerId, inputBlockNodeSize, featureSetSize);
        if(resultSetSize != outputBlockNodeSize)
            throw new ResultSetDimensionUnmatchedException(outputBlockNode.getBlockLayer().getId(), outputBlockNodeSize, testInputLayerId, resultSetSize);
    }

}
