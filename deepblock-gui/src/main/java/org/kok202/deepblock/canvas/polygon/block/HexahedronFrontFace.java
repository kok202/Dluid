package org.kok202.deepblock.canvas.polygon.block;

import javafx.geometry.Point3D;
import lombok.Getter;

@Getter
public class HexahedronFrontFace extends HexahedronFace {
    public HexahedronFrontFace(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront,
            Hexahedron hexahedron) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront, BlockFace.FRONT, hexahedron);
    }
}
