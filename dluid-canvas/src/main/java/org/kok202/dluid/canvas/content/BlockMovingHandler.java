package org.kok202.dluid.canvas.content;

import javafx.geometry.Point3D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.PickResult;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.Math3D;
import org.kok202.dluid.canvas.util.PickResultNodeUtil;

public class BlockMovingHandler {

    private static boolean isPicking;
    private static BlockNode pickedBlockNode;

    public static void setOnMousePressed(MouseEvent mouseEvent){
        PickResult pickResult = mouseEvent.getPickResult();
        if(PickResultNodeUtil.isBlockNodeSideFace(pickResult)){
            isPicking = true;
            pickedBlockNode = PickResultNodeUtil.convertToBlockNode(pickResult);
        }
    }

    public static void setOnMouseDragged(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() != MouseButton.PRIMARY)
            return;

        if(isPicking){
            PickResult pickResult = mouseEvent.getPickResult();
            if(PickResultNodeUtil.isBlockNode(pickResult) &&
                PickResultNodeUtil.convertToBlockNode(pickResult) == pickedBlockNode){
                // Best case : Dragging node when mouse is in block node
                Point3D deltaMousePoint3D = pickResult.getIntersectedPoint();
                final Point3D delta = deltaMousePoint3D;
                CanvasSingleton
                        .getInstance()
                        .getBlockNodeManager()
                        .getAllLinkedNodesData(pickedBlockNode)
                        .forEach(blockNode -> {
                            blockNode.translatePosition(delta.getX(), delta.getY(), 0);
                        });
            }
            else if(pickResult.getIntersectedNode() instanceof CoordinateGiantMesh){
                // Another case : Dragging node when mouse is in giant mesh -> change position to intersected position.
                Point3D targetPoint = pickResult.getIntersectedPoint();
                Point3D cameraPoint = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getCamera().getCurrentPosition();
                Point3D intersectedPoint = Math3D.getIntersectPoint(
                        new Point3D(0,0,1),
                        0,
                        targetPoint,
                        cameraPoint);
                Point3D deltaMousePoint3D = intersectedPoint.subtract(pickedBlockNode.getBlockInfo().getPosition());
                final Point3D delta = deltaMousePoint3D;
                CanvasSingleton
                        .getInstance()
                        .getBlockNodeManager()
                        .getAllLinkedNodesData(pickedBlockNode)
                        .forEach(blockNode -> {
                            blockNode.translatePosition(delta.getX(), delta.getY(), 0);
                        });
            }
        }
    }

    public static void setOnMouseReleased(MouseEvent mouseEvent) {
        isPicking=false;
    }
}
