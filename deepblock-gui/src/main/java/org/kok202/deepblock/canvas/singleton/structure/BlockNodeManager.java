package org.kok202.deepblock.canvas.singleton.structure;

import javafx.geometry.Point2D;
import lombok.Data;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.block.activation.ActivationBlockNode;
import org.kok202.deepblock.canvas.block.mono.MonoBlockNode;
import org.kok202.deepblock.canvas.block.stereo.SplitBlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.domain.exception.CanNotFindGraphNodeException;
import org.kok202.deepblock.domain.structure.GraphManager;
import org.kok202.deepblock.domain.structure.GraphNode;

import java.util.Collection;

@Data
public class BlockNodeManager extends GraphManager<BlockNode>{

    public void removeGraphNode(long layerId) {
        removeGraphNode(
                blockNodeObj -> {
                    BlockNode blockNode = (BlockNode) blockNodeObj;
                    return blockNode.getBlockInfo().getLayer().getId() == layerId;
                },
                graphNode -> {
                    GraphNode<BlockNode> blockGraphNode = (GraphNode<BlockNode>) graphNode;
                    blockGraphNode.getData().deleteHexahedrons();
                });
    }

    public GraphNode<BlockNode> findTestInputGraphNode(){
        for(BlockNode blockNode : getDataNodes()) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if (layerType == LayerType.INPUT_LAYER || layerType == LayerType.TEST_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Test input block node");
    }

    public GraphNode<BlockNode> findTrainInputGraphNode(){
        Collection<BlockNode> blockNodes = getDataNodes();
        for(BlockNode blockNode : blockNodes) {
            LayerType layerType = blockNode.getBlockInfo().getLayer().getType();
            if(layerType == LayerType.INPUT_LAYER || layerType == LayerType.TRAIN_INPUT_LAYER)
                return findGraphNodeByData(blockNode);
        }
        throw new CanNotFindGraphNodeException("Train input block node");
    }

    public GraphNode<BlockNode> findGraphNodeByLayerId(long layerId) {
        return findGraphNode(blockNodeObj -> {
            BlockNode blockNode = (BlockNode) blockNodeObj;
            return blockNode.getBlockInfo().getLayer().getId() == layerId;
        });
    }

    public void notifyLayerDataChanged(long layerId){
        GraphNode<BlockNode> graphNode = findGraphNodeByLayerId(layerId);
        BlockNode blockNode = graphNode.getData();
        Layer layer = blockNode.getBlockInfo().getLayer();
        LayerProperties layerProperties = layer.getProperties();

        if(blockNode instanceof ActivationBlockNode){
            ActivationBlockNode activationBlockNode = (ActivationBlockNode) blockNode;
            activationBlockNode.reshapeBlockModel(
                    layer,
                    new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                    new Point2D(layerProperties.getOutputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getOutputSize()[1] * CanvasConstant.NODE_UNIT));
        }
        else if(blockNode instanceof MonoBlockNode){
            MonoBlockNode monoBlockNode = (MonoBlockNode) blockNode;
            monoBlockNode.reshapeBlockModel(
                    layer,
                    new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                    new Point2D(layerProperties.getOutputSize()[0] * CanvasConstant.NODE_UNIT,layerProperties.getOutputSize()[1] * CanvasConstant.NODE_UNIT));
        }
        else if(blockNode instanceof SplitBlockNode){
            SplitBlockNode splitBlockNode = (SplitBlockNode) blockNode;
            splitBlockNode.reshapeBlockModel(layer);
        }

        graphNode.getIncomingNodes().forEach(incomingNode -> {
            LayerType parentLayerType = incomingNode.getData().getBlockInfo().getLayer().getType();
            if(parentLayerType.isInputLayerType()) {
                ActivationBlockNode parentInputBlockNode = (ActivationBlockNode) incomingNode.getData();
                parentInputBlockNode.reshapeBlockModel(
                        layer,
                        new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT),
                        new Point2D(layerProperties.getInputSize()[0] * CanvasConstant.NODE_UNIT, layerProperties.getInputSize()[1] * CanvasConstant.NODE_UNIT));
            }
        });
    }
}
