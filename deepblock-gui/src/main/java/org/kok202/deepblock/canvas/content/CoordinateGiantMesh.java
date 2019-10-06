package org.kok202.deepblock.canvas.content;

import javafx.geometry.Point3D;
import org.kok202.deepblock.canvas.polygon.Sprite;

public class CoordinateGiantMesh extends Sprite {
    public CoordinateGiantMesh(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront);
    }
}
