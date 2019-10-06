package org.kok202.deepblock.canvas.polygon.block;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import lombok.Builder;
import lombok.Data;
import org.kok202.deepblock.canvas.block.BlockNode;

@Data
public class BlockHexahedron extends Hexahedron {
    private BlockNode blockNode;

    @Builder
    public BlockHexahedron(Point3D leftTopFront, Point3D leftTopBack,
                           Point3D leftBottomFront, Point3D leftBottomBack,
                           Point3D rightTopFront, Point3D rightTopBack,
                           Point3D rightBottomFront, Point3D rightBottomBack,
                           String[] textureSources,
                           Color[] colors,
                           BlockNode blockNode) {
        super(leftTopFront, leftTopBack,
                leftBottomFront, leftBottomBack,
                rightTopFront, rightTopBack,
                rightBottomFront, rightBottomBack,
                textureSources,
                colors);
        this.blockNode = blockNode;
    }
}
