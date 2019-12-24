package org.kok202.dluid.model;

import org.kok202.dluid.CanvasFacade;
import org.kok202.dluid.ai.AIFacade;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.domain.exception.CanNotFindFeatureSetException;
import org.kok202.dluid.domain.exception.CanNotFindResultSetException;
import org.kok202.dluid.domain.exception.FeatureSetDimensionUnmatchedException;
import org.kok202.dluid.domain.exception.ResultSetDimensionUnmatchedException;
import org.kok202.dluid.domain.structure.GraphNode;

class ModelTestValidator {

    static void validate(){
        validateDataSetExist();
        validateDataSetDimension();
    }

    private static void validateDataSetExist() throws FeatureSetDimensionUnmatchedException {
        GraphNode<BlockNode> testInputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isTestInputLayerType()).get(0);
        long testInputLayerId = testInputGraphNode.getData().getBlockLayer().getId();

        if(AIFacade.getTestFeatureSet().getNumericRecordSet() == null)
            throw new CanNotFindFeatureSetException(testInputLayerId);
        if(AIFacade.getTestResultSet().getNumericRecordSet() == null)
            throw new CanNotFindResultSetException(testInputLayerId);
    }

    private static void validateDataSetDimension() throws FeatureSetDimensionUnmatchedException, ResultSetDimensionUnmatchedException {
        BlockNode outputBlockNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isOutputLayerType()).get(0).getData();
        int outputBlockNodeSize =
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[0] *
                outputBlockNode.getBlockLayer().getProperties().getOutputSize()[1];

        int featureSetSize = AIFacade.getTestFeatureSet().getNumericRecordSet().getRecordSize();
        int resultSetSize = AIFacade.getTestResultSet().getNumericRecordSet().getRecordSize();

        GraphNode<BlockNode> testInputGraphNode = CanvasFacade.findAllGraphNode(blockNodeGraphNode -> blockNodeGraphNode.getData().getBlockLayer().getType().isTestInputLayerType()).get(0);
        long testInputLayerId = testInputGraphNode.getData().getBlockLayer().getId();
        int inputBlockNodeSize =
                testInputGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[0] *
                testInputGraphNode.getData().getBlockLayer().getProperties().getOutputSize()[1];

        if(featureSetSize != inputBlockNodeSize)
            throw new FeatureSetDimensionUnmatchedException(testInputLayerId, inputBlockNodeSize, featureSetSize);
        if(resultSetSize != outputBlockNodeSize)
            throw new ResultSetDimensionUnmatchedException(outputBlockNode.getBlockLayer().getId(), outputBlockNodeSize, testInputLayerId, resultSetSize);
    }

}
