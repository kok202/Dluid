package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.*;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.HexahedronBottomFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronTopFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronVerticalFace;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.canvas.util.PickResultNodeUtil;
import org.kok202.deepblock.domain.exception.IllegalConnectionRequest;

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
                    CanvasSingleton.getInstance().getBlockNodeManager().link(currentPickedBlockNode, pastPickedBlockNode);
                    reshapeSourceBlockNode(currentPickedBlockNode, pastPickedBlockNode);
                }
                else if(!isClickedOnTop && pickResult.getIntersectedNode() instanceof HexahedronTopFace){
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pastPickedBlockNode, currentPickedBlockNode);
                    reshapeSourceBlockNode(pastPickedBlockNode, currentPickedBlockNode);
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

    private static void reshapeSourceBlockNode(BlockNode sourceBlockNode, BlockNode destinationBlockNode){
        Point3D topSkewed = new Point3D(0,0, 0);
        Point3D bottomSkewed = new Point3D(destinationBlockNode.getBlockInfo().getPosition().getX() - sourceBlockNode.getBlockInfo().getPosition().getX(), 0,0);
        Point3D newPosition = new Point3D(
                (sourceBlockNode.getBlockInfo().getPosition().getX() + destinationBlockNode.getBlockInfo().getPosition().getX()) / 2 -
                        (bottomSkewed.getX() / 2),
                ((sourceBlockNode.getBlockInfo().getPosition().getY() - sourceBlockNode.getBlockInfo().getHeight() / 2) +
                        (destinationBlockNode.getBlockInfo().getPosition().getY() - destinationBlockNode.getBlockInfo().getHeight() / 2)) / 2,
                (sourceBlockNode.getBlockInfo().getPosition().getZ() + destinationBlockNode.getBlockInfo().getPosition().getZ()) / 2);

        double height = destinationBlockNode.getBlockInfo().getPosition().getY() - sourceBlockNode.getBlockInfo().getPosition().getY(); // Caused by coordination.
        height -= destinationBlockNode.getBlockInfo().getHeight()/2;
        height += sourceBlockNode.getBlockInfo().getHeight()/2;
        if(height < 0){
            releaseConnectionProcess();
            throw new IllegalConnectionRequest();
        }

        SkewedBlockProperty skewedBlockProperty = new SkewedBlockProperty();
        skewedBlockProperty.setTopSkewed(topSkewed);
        skewedBlockProperty.setBottomSkewed(bottomSkewed);
        sourceBlockNode.setHeight(height);
        sourceBlockNode.setPosition(newPosition.getX(), newPosition.getY(), newPosition.getZ());
        sourceBlockNode.getBlockInfo().getLayer().setExtra(skewedBlockProperty);
        CanvasSingleton.getInstance().getBlockNodeManager().notifyLayerDataChanged(sourceBlockNode.getBlockInfo().getLayer().getId());
    }

    private static void releaseConnectionProcess(){
        if(pastPickedBlockNodeFace != null)
            pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND);
        isClicked = false;
        pastPickedBlockNode = null;
        pastPickedBlockNodeFace = null;
    }
}
