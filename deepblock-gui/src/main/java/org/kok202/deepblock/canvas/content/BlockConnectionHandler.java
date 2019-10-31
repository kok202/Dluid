package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.deepblock.application.global.AppWidgetSingleton;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.HexahedronFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronTopFace;
import org.kok202.deepblock.canvas.polygon.block.HexahedronVerticalFace;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.canvas.util.PickResultNodeUtil;
import org.kok202.deepblock.domain.exception.IllegalConnectionRequest;

public class BlockConnectionHandler {
    private static boolean isClicked = false;
    private static boolean isClickedStartAtTop = false;
    private static BlockNode pastPickedBlockNode = null;
    private static HexahedronFace pastPickedBlockNodeFace = null;

    public static void setOnMousePressed(MouseEvent mouseEvent){
        if(mouseEvent.getButton() != MouseButton.PRIMARY)
            return;

        PickResult pickResult = mouseEvent.getPickResult();
        Node pickResultNode = pickResult.getIntersectedNode();
        if(pickResultNode instanceof HexahedronVerticalFace){
            isClicked = true;
            isClickedStartAtTop = pickResult.getIntersectedNode() instanceof HexahedronTopFace;
            BlockNode pickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
            if((isClickedStartAtTop && !pickedBlockNode.isPossibleToAppendFront()) ||
                    (!isClickedStartAtTop && !pickedBlockNode.isPossibleToAppendBack())){
                releaseConnectionProcess();
                return;
            }
            pastPickedBlockNode = pickedBlockNode;
            pastPickedBlockNodeFace = PickResultNodeUtil.convertToBlockHexahedronFace(pickResult);
            pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_TRY_TO_APPEND);
            AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .setStart(new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .setEnd(new Point2D(mouseEvent.getX(),mouseEvent.getY()));
            AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .setVisible(true);
        }
        else{
            releaseConnectionProcess();
        }
    }

    public static void setOnMouseDragged(MouseEvent mouseEvent){
        if(isClicked){
            AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .setEnd(new Point2D(mouseEvent.getX(),mouseEvent.getY()).add(
                            isClickedStartAtTop?
                                CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_START_AT_TOP :
                                CanvasConstant.CUBIC_CURVE_END_GAP_WHEN_START_AT_BOTTOM));
        }
    }

    public static void setOnMouseReleased(MouseEvent mouseEvent){
        if(isClicked){
            PickResult pickResult = mouseEvent.getPickResult();
            Node pickResultNode = pickResult.getIntersectedNode();
            if(pickResultNode instanceof HexahedronFace){
                BlockNode currentPickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
                if(pastPickedBlockNode == currentPickedBlockNode)
                    return;

                if(isClickedStartAtTop && currentPickedBlockNode.isPossibleToAppendBack()){
                    CanvasSingleton.getInstance().getBlockNodeManager().link(currentPickedBlockNode, pastPickedBlockNode);
                    reshapeTopBlockNode(currentPickedBlockNode, pastPickedBlockNode);
//                    reshapeBottomBlockNode(currentPickedBlockNode, pastPickedBlockNode);
                }
                else if(!isClickedStartAtTop && currentPickedBlockNode.isPossibleToAppendFront()){
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pastPickedBlockNode, currentPickedBlockNode);
                    reshapeTopBlockNode(pastPickedBlockNode, currentPickedBlockNode);
                }
            }
            releaseConnectionProcess();
        }
    }

    private static void reshapeTopBlockNode(BlockNode sourceBlockNode, BlockNode destinationBlockNode){
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

    private static void reshapeBottomBlockNode(BlockNode sourceBlockNode, BlockNode destinationBlockNode){
        Point3D topSkewed = new Point3D(sourceBlockNode.getBlockInfo().getPosition().getX() - destinationBlockNode.getBlockInfo().getPosition().getX(),0, 0);
        Point3D bottomSkewed = new Point3D(0, 0,0);
        Point3D newPosition = new Point3D(
                (sourceBlockNode.getBlockInfo().getPosition().getX() + destinationBlockNode.getBlockInfo().getPosition().getX()) / 2 -
                        (topSkewed.getX() / 2),
                ((sourceBlockNode.getBlockInfo().getPosition().getY() + sourceBlockNode.getBlockInfo().getHeight() / 2) +
                        (destinationBlockNode.getBlockInfo().getPosition().getY() + destinationBlockNode.getBlockInfo().getHeight() / 2)) / 2,
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
        destinationBlockNode.setHeight(height);
        destinationBlockNode.setPosition(newPosition.getX(), newPosition.getY(), newPosition.getZ());
        destinationBlockNode.getBlockInfo().getLayer().setExtra(skewedBlockProperty);
        CanvasSingleton.getInstance().getBlockNodeManager().notifyLayerDataChanged(destinationBlockNode.getBlockInfo().getLayer().getId());
    }

    private static void releaseConnectionProcess(){
        if(pastPickedBlockNodeFace != null)
            pastPickedBlockNodeFace.setColor(CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND);
        isClicked = false;
        pastPickedBlockNode = null;
        pastPickedBlockNodeFace = null;
        AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .setVisible(false);
    }
}
