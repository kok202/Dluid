package org.kok202.dluid.canvas.content;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.dluid.AppFacade;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.LayerType;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.block.BlockNodeFactory;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;
import org.kok202.dluid.canvas.polygon.block.HexahedronFace;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.PickResultNodeUtil;
import org.kok202.dluid.domain.exception.BlockConnectionImpossibleException;
import org.kok202.dluid.domain.exception.IllegalConnectionRequest;
import org.kok202.dluid.model.LayerFactory;

public class BlockConnectionHandler {
    private static boolean isClicked = false;
    private static BlockNode pastPickedBlockNode = null;

    public static void setOnMouseClicked(Group sceneRoot, MouseEvent mouseEvent){
        if(mouseEvent.getClickCount() <= 1)
            return;

        PickResult pickResult = mouseEvent.getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pickResultNode instanceof HexahedronFace){
            pastPickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
            if(pastPickedBlockNode.getBlockLayer().getType() == LayerType.PIPE_LAYER){
                releaseConnectionProcess();
                return;
            }

            isClicked = true;
            AppFacade.setConnectionArrowStart(new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            AppFacade.setConnectionArrowEnd(new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            AppFacade.setConnectionArrowVisible(true);
        }
        else{
            releaseConnectionProcess();
        }
    }

    // While
    public static void setOnMouseMoved(Group sceneRoot, MouseEvent mouseEvent){
        if(isClicked){
            boolean isUpward = AppFacade.isConnectionArrowUpward();
            AppFacade.setConnectionArrowEnd(new
                    Point2D(mouseEvent.getX(),mouseEvent.getY())
                    .add(isUpward?
                            CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_UPWARD :
                            CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_DOWNWARD));
        }
    }

    // End
    public static void setOnMousePressed(Group sceneRoot, MouseEvent mouseEvent){
        if(isClicked){
            PickResult pickResult = mouseEvent.getPickResult();
            Node pickResultNode = pickResult.getIntersectedNode();
            if(pickResultNode instanceof HexahedronFace){
                BlockNode currentPickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                if(pastPickedBlockNode == currentPickedBlockNode ||
                    pastPickedBlockNode.getBlockLayer().getType() == LayerType.PIPE_LAYER ||
                    currentPickedBlockNode.getBlockLayer().getType() == LayerType.PIPE_LAYER){
                    releaseConnectionProcess();
                    return;
                }

                boolean isUpward = AppFacade.isConnectionArrowUpward();
                if(isUpward && currentPickedBlockNode.isPossibleToAppendBackByConnection()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, currentPickedBlockNode, pastPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(currentPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, pastPickedBlockNode);
                }
                else if(!isUpward && currentPickedBlockNode.isPossibleToAppendFrontByConnection()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, pastPickedBlockNode, currentPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(pastPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, currentPickedBlockNode);
                }
                else {
                    throw new BlockConnectionImpossibleException();
                }
                CanvasSingleton.getInstance().getBlockNodeManager().reshapeAllBlockByType();
            }
            releaseConnectionProcess();
        }
    }

    private static BlockNode insertPipeBlockNode(Group sceneRoot, BlockNode sourceBlockNode, BlockNode destinationBlockNode){
        Point3D position = sourceBlockNode.getBottomCenterPosition();
        position = position.add(destinationBlockNode.getTopCenterPosition());
        position = position.multiply(0.5);

        Point3D topSkewed = sourceBlockNode.getBlockInfo().getPosition().subtract(position);
        Point3D bottomSkewed = destinationBlockNode.getBlockInfo().getPosition().subtract(position);
        double height = destinationBlockNode.getTopCenterPosition().getY() - sourceBlockNode.getBottomCenterPosition().getY();
        if(height < 0){
            releaseConnectionProcess();
            throw new IllegalConnectionRequest();
        }

        Layer layer = LayerFactory.create(LayerType.PIPE_LAYER);
        layer.getProperties().setInputSize(
            sourceBlockNode.getBlockLayer().getProperties().getOutputSize()[0],
            sourceBlockNode.getBlockLayer().getProperties().getOutputSize()[1]);
        layer.getProperties().setOutputSize(
            sourceBlockNode.getBlockLayer().getProperties().getOutputSize()[0],
            sourceBlockNode.getBlockLayer().getProperties().getOutputSize()[1]);

        BlockNode pipeBlockNode = BlockNodeFactory.create(layer);
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) pipeBlockNode.getBlockInfo().getExtra();
        skewedBlockProperty.setTopSkewed(topSkewed);
        skewedBlockProperty.setBottomSkewed(bottomSkewed);
        pipeBlockNode.setHeight(height);
        pipeBlockNode.addedToScene(sceneRoot, position);
        pipeBlockNode.reshapeBlockModel();
        return pipeBlockNode;
    }

    private static void releaseConnectionProcess(){
        isClicked = false;
        pastPickedBlockNode = null;
        AppFacade.setConnectionArrowVisible(false);
    }
}
