package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.enumerator.LayerType;
import org.kok202.deepblock.application.global.AppWidgetSingleton;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.block.BlockNodeFactory;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;
import org.kok202.deepblock.canvas.interfaces.CanvasConstant;
import org.kok202.deepblock.canvas.polygon.block.HexahedronFace;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.canvas.util.PickResultNodeUtil;
import org.kok202.deepblock.domain.exception.IllegalConnectionRequest;

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
            if(pastPickedBlockNode.getBlockInfo().getLayer().getType() == LayerType.PIPE_LAYER){
                releaseConnectionProcess();
                return;
            }

            isClicked = true;
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

    // While
    public static void setOnMouseMoved(Group sceneRoot, MouseEvent mouseEvent){
        if(isClicked){
            boolean isUpward = AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .isUpward();
            AppWidgetSingleton.getInstance()
                    .getContentRootController()
                    .getTabsController()
                    .getTabModelDesignController()
                    .getBlockConnectionManager()
                    .setEnd(new Point2D(mouseEvent.getX(),mouseEvent.getY()).add(
                            isUpward?
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
                    currentPickedBlockNode.getBlockInfo().getLayer().getType() == LayerType.PIPE_LAYER){
                    releaseConnectionProcess();
                    return;
                }

                boolean isUpward = AppWidgetSingleton.getInstance()
                        .getContentRootController()
                        .getTabsController()
                        .getTabModelDesignController()
                        .getBlockConnectionManager()
                        .isUpward();
                if(isUpward && currentPickedBlockNode.isPossibleToAppendBack()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, currentPickedBlockNode, pastPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(currentPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, pastPickedBlockNode);
                }
                else if(!isUpward && currentPickedBlockNode.isPossibleToAppendFront()){
                    BlockNode pipeBlockNode = insertPipeBlockNode(sceneRoot, pastPickedBlockNode, currentPickedBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().linkToNewData(pastPickedBlockNode, pipeBlockNode);
                    CanvasSingleton.getInstance().getBlockNodeManager().link(pipeBlockNode, currentPickedBlockNode);
                }
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

        SkewedBlockProperty skewedBlockProperty = new SkewedBlockProperty();
        skewedBlockProperty.setTopSkewed(topSkewed);
        skewedBlockProperty.setBottomSkewed(bottomSkewed);
        Layer layer = new Layer(LayerType.PIPE_LAYER);
        layer.setExtra(skewedBlockProperty);

        BlockNode pipeBlockNode = BlockNodeFactory.create(layer);
        pipeBlockNode.setHeight(height);
        pipeBlockNode.addedToScene(sceneRoot, position);
        pipeBlockNode.reshapeBlockModel();
        return pipeBlockNode;
    }

    private static void releaseConnectionProcess(){
        isClicked = false;
        pastPickedBlockNode = null;
        AppWidgetSingleton.getInstance()
                .getContentRootController()
                .getTabsController()
                .getTabModelDesignController()
                .getBlockConnectionManager()
                .setVisible(false);
    }
}
