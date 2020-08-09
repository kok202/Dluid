package org.kokzoz.dluid.canvas.util;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.kokzoz.dluid.canvas.scene.Camera;
import org.kokzoz.dluid.canvas.singleton.CanvasSingleton;

public class CanvasPointUtil {
    public static Point3D raycastingNomalized(Point2D mousePoint) {
        return raycasting(mousePoint).normalize();
    }

    public static Point3D raycasting(Point2D mousePoint) {
        // ex. screen y size = 600
        // ex. mouse y position = 200
        Point2D sceneSize = new Point2D(
                CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneWidth(),
                CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneHeight());
        double sceneRatio = sceneSize.getX() / sceneSize.getY();

        // mapped to [-1, 0, +1] (ex. mouseRatioFromCenter = -1/3)
        Point2D mouseRatioFromCenter = new Point2D(
                2 * mousePoint.getX() / sceneSize.getX() - 1,
                2 * mousePoint.getY() / sceneSize.getY() - 1);

        // convert to vector in canvas using camera position z.
        Camera camera = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getCamera();
        double halfFov = camera.getFieldOfView() * 0.5f; // Standard of FOV is vertical
        double tanHalfFov = Math.tan(Math.toRadians(halfFov));
        double depth = camera.getDepth();
        double x = depth * tanHalfFov * mouseRatioFromCenter.getX() * sceneRatio;
        double y = depth * tanHalfFov * mouseRatioFromCenter.getY();

        // Because coordination is upside downed.
        return new Point3D(-x, -y,1);
    }


}
