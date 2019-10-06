package org.kok202.deepblock.canvas.polygon.block;

import javafx.geometry.Point3D;
import lombok.Getter;

@Getter
public abstract class HexahedronVerticalFace extends HexahedronFace {
    public HexahedronVerticalFace(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront,
            BlockFace blockFaceDirection,
            Hexahedron hexahedron) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront, blockFaceDirection, hexahedron);
    }
}
