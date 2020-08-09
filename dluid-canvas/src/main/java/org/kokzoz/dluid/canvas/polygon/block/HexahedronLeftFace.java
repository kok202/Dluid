package org.kokzoz.dluid.canvas.polygon.block;

import javafx.geometry.Point3D;
import lombok.Getter;

@Getter
public class HexahedronLeftFace extends HexahedronFace {
    public HexahedronLeftFace(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront,
            Hexahedron hexahedron) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront, BlockFace.LEFT, hexahedron);
    }
}
