package org.kokzoz.dluid.canvas.polygon.block;

import javafx.geometry.Point3D;
import lombok.Getter;
import org.kokzoz.dluid.canvas.polygon.Sprite;

@Getter
public abstract class HexahedronFace extends Sprite {
    protected BlockFace blockFaceDirection;
    protected Hexahedron hexahedron;

    protected HexahedronFace(
            Point3D leftTopFront, Point3D rightTopFront,
            Point3D leftBottomFront, Point3D rightBottomFront,
            BlockFace blockFaceDirection,
            Hexahedron hexahedron) {
        super(leftTopFront, rightTopFront, leftBottomFront, rightBottomFront);
        this.hexahedron = hexahedron;
        this.blockFaceDirection = blockFaceDirection;
    }
}
