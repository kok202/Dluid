package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.polygon.Pipe;
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
        PickResult pickResult = mouseEvent.getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pickResultNode instanceof HexahedronVerticalFace){
            if(isClicked){
                BlockNode currentPickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                if(pastPickedBlockNode == currentPickedBlockNode)
                    return;

                // TODO : pipe 도 블락으로 간주하기
                if(isClickedOnTop && pickResult.getIntersectedNode() instanceof HexahedronBottomFace){
                    CanvasSingleton.getInstance().getBlockNodeManager().link(currentPickedBlockNode, pastPickedBlockNode);
                    drawPipe(sceneRoot, currentPickedBlockNode, pastPickedBlockNode);
                }
                else if(!isClickedOnTop && pickResult.getIntersectedNode() instanceof HexahedronTopFace){
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pastPickedBlockNode, currentPickedBlockNode);
                    drawPipe(sceneRoot, pastPickedBlockNode, currentPickedBlockNode);
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
                pastPickedBlockNodeFace = (HexahedronVerticalFace) pickResultNode;
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

    private static void drawPipe(Group sceneRoot, BlockNode sourceBlockNode, BlockNode destinationBlockNode){
        Pipe pipe = Pipe.builder()
                .leftTopFront(new Point3D(-1, -1, -1))
                .leftTopBack(new Point3D(-1, -1, 1))
                .rightTopFront(new Point3D(1, -1, -1))
                .rightTopBack(new Point3D(1, -1, 1))
                .rightBottomFront(new Point3D(0, 1, -1))
                .rightBottomBack(new Point3D(0, 1, 1))
                .leftBottomFront(new Point3D(-2, 1, -1))
                .leftBottomBack(new Point3D(-2, 1, 1))
                .build();
        sceneRoot.getChildren().addAll(pipe.getFaces());
    }
    private static void releaseConnectionProcess(){
        if(pastPickedBlockNodeFace != null)
            pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND);
        isClicked = false;
        pastPickedBlockNode = null;
        pastPickedBlockNodeFace = null;
    }
}
