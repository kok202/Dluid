package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.*;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.block.BlockNodeFactory;
import org.kok202.deepblock.canvas.entity.PipeBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.HexahedronBottomFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronTopFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronVerticalFace;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.canvas.util.PickResultNodeUtil;

public class BlockConnectionHandler {
    private static boolean isClicked = false;
    private static boolean isClickedOnTop = false;
    private static BlockNode pastPickedBlockNode = null;
    private static HexahedronFace pastPickedBlockNodeFace = null;

    public static void setOnMouseClicked(MouseEvent mouseEvent, Group sceneRoot){
        if(mouseEvent.getButton() != MouseButton.PRIMARY)
            return;

        PickResult pickResult = mouseEvent.getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pickResultNode instanceof HexahedronVerticalFace){
            if(isClicked){
                BlockNode currentPickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                if(pastPickedBlockNode == currentPickedBlockNode)
                    return;

                if(isClickedOnTop && pickResult.getIntersectedNode() instanceof HexahedronBottomFace){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, currentPickedBlockNode, pastPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(currentPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, pastPickedBlockNode);
                }
                else if(!isClickedOnTop && pickResult.getIntersectedNode() instanceof HexahedronTopFace){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, pastPickedBlockNode, currentPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pastPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, currentPickedBlockNode);
                }
                releaseConnectionProcess();
            }
            else{
                isClicked = true;
                isClickedOnTop = pickResult.getIntersectedNode() instanceof HexahedronTopFace;
                BlockNode pickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                if((isClickedOnTop && !pickedBlockNode.isPossibleToAppendFront()) ||
                    (!isClickedOnTop && !pickedBlockNode.isPossibleToAppendBack())){
                    releaseConnectionProcess();
                    return;
                }
                pastPickedBlockNode = pickedBlockNode;
                pastPickedBlockNodeFace = PickResultNodeUtil.convertToBlockHexahedronFace(pickResult);
                // FIXME : 색이 안변해...
                pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_TRY_TO_APPEND);
            }
        }
        else{
            releaseConnectionProcess();
        }
    }

    public static void setOnKeyTyped(KeyEvent keyEvent){
        if(keyEvent.getCode() == KeyCode.ESCAPE){
            releaseConnectionProcess();
        }
    }

    private static BlockNode insertPipeBlockNode(Group sceneRoot, BlockNode sourceBlockNode, BlockNode destinationBlockNode){
        Point3D position = sourceBlockNode.getPosition();
        position = position.add(destinationBlockNode.getPosition());
        position = position.multiply(0.5);

        Point2D topSkewed = new Point2D(
                sourceBlockNode.getPosition().getX() - position.getX(),
                sourceBlockNode.getPosition().getY() - position.getY());
        Point2D bottomSkewed = new Point2D(
                destinationBlockNode.getPosition().getX() - position.getX(),
                destinationBlockNode.getPosition().getY() - position.getY());
        double height = sourceBlockNode.getPosition().getZ() - destinationBlockNode.getPosition().getZ();

        PipeBlockProperty pipeBlockProperty = new PipeBlockProperty();
        pipeBlockProperty.setTopSkewed(topSkewed);
        pipeBlockProperty.setBottomSkewed(bottomSkewed);
        pipeBlockProperty.setHeight(height);
        Layer layer = new Layer(LayerType.PIPE_LAYER);
        layer.setExtra(pipeBlockProperty);

        BlockNode pipeBlockNode = BlockNodeFactory.create(layer);
        pipeBlockNode.addedToScene(sceneRoot, position);
        return pipeBlockNode;
    }

    private static void releaseConnectionProcess(){
        if(pastPickedBlockNodeFace != null)
            pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND);
        isClicked = false;
        pastPickedBlockNode = null;
        pastPickedBlockNodeFace = null;
    }
}
