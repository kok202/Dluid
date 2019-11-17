package org.kok202.deepblock.canvas.scene;

import javafx.geometry.Point3D;
import javafx.scene.PerspectiveCamera;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import org.kok202.deepblock.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public class Camera extends PerspectiveCamera {
    private MouseEvent previousMouseEvent;
    private Rotate rotateX;
    private Rotate rotateY;
    private Rotate rotateZ;
    private Translate translate;
    private double depth = -10;

    public Camera() {
        super(true);
        rotateX = new Rotate(0, Rotate.X_AXIS);
        rotateY = new Rotate(0, Rotate.Y_AXIS);
        rotateZ = new Rotate(0, Rotate.Z_AXIS);
        translate = new Translate(0,0, depth);
        setFarClip(CanvasConstant.CAMERA_FAR_CLIP);
        setNearClip(CanvasConstant.CAMERA_NEAR_CLIP);
        setVerticalFieldOfView(true);
        getTransforms().addAll(rotateX, rotateY, rotateZ, translate);
    }

    public MouseEvent setOnMousePressed(MouseEvent mouseEvent){
        return previousMouseEvent = mouseEvent;
    }

    public MouseEvent setOnMouseDragged(MouseEvent mouseEvent){
        // Move
        if(previousMouseEvent.getButton() == MouseButton.MIDDLE &&
                   mouseEvent.getButton() == MouseButton.MIDDLE) {
            double movedDeltaX = (mouseEvent.getX() - previousMouseEvent.getX()) * CanvasSingleton.getInstance().getCameraMovementSpeed();
            double movedDeltaY = (mouseEvent.getY() - previousMouseEvent.getY()) * CanvasSingleton.getInstance().getCameraMovementSpeed();
            translate.setX(translate.getX() + movedDeltaX);
            translate.setY(translate.getY() + movedDeltaY);
            previousMouseEvent = mouseEvent;
        }

        // Rotate camera
        if(previousMouseEvent.getButton() == MouseButton.SECONDARY &&
                   mouseEvent.getButton() == MouseButton.SECONDARY) {
            double movedDeltaX = (mouseEvent.getX() - previousMouseEvent.getX()) * CanvasSingleton.getInstance().getCameraRotationSpeed();
            double movedDeltaY = (mouseEvent.getY() - previousMouseEvent.getY()) * CanvasSingleton.getInstance().getCameraRotationSpeed();
            if (Math.abs(rotateX.getAngle() - movedDeltaY) < CanvasConstant.CAMERA_MAX_ANGLE)
                rotateX.setAngle(rotateX.getAngle() - movedDeltaY);
            if(Math.abs(rotateY.getAngle() + movedDeltaX) < CanvasConstant.CAMERA_MAX_ANGLE)
                rotateY.setAngle(rotateY.getAngle() + movedDeltaX);
            previousMouseEvent = mouseEvent;
        }
        return mouseEvent;
    }

    public ScrollEvent setOnScroll(ScrollEvent scrollEvent) {
        // Scale up & down
        double zoomSize = scrollEvent.getDeltaY() * CanvasSingleton.getInstance().getCameraZoomSize();
        if(depth + zoomSize < CanvasConstant.CAMERA_DEPTH_MIN) {
            return scrollEvent;
        }
        if(depth + zoomSize > CanvasConstant.CAMERA_DEPTH_MAX) {
            return scrollEvent;
        }

        depth += zoomSize;
        translate.setZ(depth);
        return scrollEvent;
    }

    public double getDepth() {
        return depth;
    }

    public Point3D getCurrentPosition(){
        // Very important : order
        Point3D point3D = new Point3D(0,0, 0);
        point3D = translate.transform(point3D.getX(), point3D.getY(), point3D.getZ());
        point3D = rotateZ.transform(point3D.getX(), point3D.getY(), point3D.getZ());
        point3D = rotateY.transform(point3D.getX(), point3D.getY(), point3D.getZ());
        point3D = rotateX.transform(point3D.getX(), point3D.getY(), point3D.getZ());
        return new Point3D(point3D.getX(), point3D.getY(), point3D.getZ());
    }
}
