package org.kokzoz.dluid.canvas.content;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.CanvasFacade;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.canvas.block.BlockNodeFactory;
import org.kokzoz.dluid.canvas.entity.ExtraBlockProperty;
import org.kokzoz.dluid.canvas.polygon.block.HexahedronFace;
import org.kokzoz.dluid.canvas.singleton.CanvasSingleton;
import org.kokzoz.dluid.canvas.util.BlockNodeUtil;
import org.kokzoz.dluid.canvas.util.PickResultNodeUtil;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.entity.Layer;
import org.kokzoz.dluid.domain.entity.LayerFactory;
import org.kokzoz.dluid.domain.entity.enumerator.LayerType;
import org.kokzoz.dluid.domain.exception.BlockConnectionImpossibleException;
import org.kokzoz.dluid.domain.exception.IllegalConnectionRequest;

public class BlockConnectionHandler {
    private static boolean isClicked = false;
    private static BlockNode pastPickedBlockNode = null;
    private static Point2D startPoint;
    private static Point2D endPoint;

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
            startPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            endPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY());
            BlockConnectionPayload blockConnectionPayload = new BlockConnectionPayload();
            blockConnectionPayload.setStart(startPoint);
            blockConnectionPayload.setEnd(endPoint);
            blockConnectionPayload.setVisible(true);
            CanvasFacade.dispatchAction(ActionType.BLOCK_CONNECTION_START, blockConnectionPayload);
        }
        else{
            releaseConnectionProcess();
        }
    }

    // While
    public static void setOnMouseMoved(Group sceneRoot, MouseEvent mouseEvent){
        if(isClicked){
            endPoint = new Point2D(mouseEvent.getX(), mouseEvent.getY())
                .add(isUpward()
                    ? CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_UPWARD
                    : CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_DOWNWARD);
            BlockConnectionPayload blockConnectionPayload = new BlockConnectionPayload();
            blockConnectionPayload.setStart(startPoint);
            blockConnectionPayload.setEnd(endPoint);
            blockConnectionPayload.setVisible(true);
            CanvasFacade.dispatchAction(ActionType.BLOCK_CONNECTION_MOVE, blockConnectionPayload);
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

                if(isUpward() && currentPickedBlockNode.isPossibleToAppendBackByConnection()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, currentPickedBlockNode, pastPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(currentPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, pastPickedBlockNode);
                }
                else if(!isUpward() && currentPickedBlockNode.isPossibleToAppendFrontByConnection()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, pastPickedBlockNode, currentPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(pastPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, currentPickedBlockNode);
                }
                else {
                    throw new BlockConnectionImpossibleException();
                }
                CanvasSingleton.getInstance().getBlockNodeManager().reshapeAllBlock();
                CanvasSingleton.getInstance().getBlockNodeManager().replaceAllBlock();
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
        layer.getProperties().getInput().setX(BlockNodeUtil.getBlockNodeOutputX(sourceBlockNode.getBlockLayer()));
        layer.getProperties().getInput().setY(BlockNodeUtil.getBlockNodeOutputY(sourceBlockNode.getBlockLayer()));
        layer.getProperties().getOutput().setX(BlockNodeUtil.getBlockNodeInputX(destinationBlockNode.getBlockLayer()));
        layer.getProperties().getOutput().setY(BlockNodeUtil.getBlockNodeInputY(destinationBlockNode.getBlockLayer()));

        BlockNode pipeBlockNode = BlockNodeFactory.create(layer);
        ExtraBlockProperty extraBlockProperty = pipeBlockNode.getBlockInfo().getExtra();
        extraBlockProperty.setTopSkewed(topSkewed);
        extraBlockProperty.setBottomSkewed(bottomSkewed);
        pipeBlockNode.setHeight(height);
        pipeBlockNode.addedToScene(sceneRoot, position);
        pipeBlockNode.reshapeBlockModel();
        return pipeBlockNode;
    }

    private static void releaseConnectionProcess(){
        isClicked = false;
        pastPickedBlockNode = null;
        CanvasFacade.dispatchAction(ActionType.BLOCK_CONNECTION_RELEASE);
    }

    private static boolean isUpward() {
        return (startPoint.getY() - endPoint.getY()) > 0;
    }
}
