package org.kok202.dluid.canvas.polygon;

import javafx.geometry.Point3D;
import lombok.Builder;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.polygon.block.Hexahedron;

public class Pipe extends Hexahedron {
    private BlockNode sourceBlockNode;
    private BlockNode destinationBlockNode;

    @Builder
    public Pipe(Point3D leftTopFront, Point3D leftTopBack,
                           Point3D leftBottomFront, Point3D leftBottomBack,
                           Point3D rightTopFront, Point3D rightTopBack,
                           Point3D rightBottomFront, Point3D rightBottomBack,
                           BlockNode sourceBlockNode,
                           BlockNode destinationBlockNode) {
        super(leftTopFront, leftTopBack,
                leftBottomFront, leftBottomBack,
                rightTopFront, rightTopBack,
                rightBottomFront, rightBottomBack,
                null,
                null);
        this.sourceBlockNode = sourceBlockNode;
        this.destinationBlockNode = destinationBlockNode;
    }
}
