package org.kok202.deepblock.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public abstract class MonoBlockNode extends BlockNode {

    public MonoBlockNode(Layer layer) {
        super(layer);
    }

    @Override
    protected void createBlockModel(Layer layer){
        Point2D topSize = new Point2D(
                layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT);

        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) layer.getExtra();
        BlockHexahedron layerHexahedron = createHexahedron(
                topSize, skewedBlockProperty.getTopSkewed(),
                bottomSize, skewedBlockProperty.getBottomSkewed(),
                getBlockInfo().getHeight());
        getBlockHexahedronList().add(layerHexahedron);
    }

    private BlockHexahedron createHexahedron(
            Point2D topSize, Point3D topSkewed,
            Point2D bottomSize, Point3D bottomSkewed,
            double height) {
        topSize = topSize.multiply(0.5);
        bottomSize = bottomSize.multiply(0.5);
        double halfNodeHeight = height / 2;
        return BlockHexahedron.builder()
                .leftTopFront(new Point3D(-topSize.getX() + topSkewed.getX(), -halfNodeHeight, -topSize.getY() + topSkewed.getZ()))
                .leftTopBack(new Point3D(-topSize.getX()  + topSkewed.getX(), -halfNodeHeight, topSize.getY() + topSkewed.getZ()))
                .leftBottomFront(new Point3D(-bottomSize.getX() + bottomSkewed.getX(),  halfNodeHeight, -bottomSize.getY() + bottomSkewed.getZ()))
                .leftBottomBack(new Point3D(-bottomSize.getX() + bottomSkewed.getX(),  halfNodeHeight, bottomSize.getY() + bottomSkewed.getZ()))
                .rightTopFront(new Point3D(topSize.getX() + topSkewed.getX(), -halfNodeHeight, -topSize.getY() + topSkewed.getZ()))
                .rightTopBack(new Point3D(topSize.getX() + topSkewed.getX(), -halfNodeHeight, topSize.getY() + topSkewed.getZ()))
                .rightBottomFront(new Point3D(bottomSize.getX() + bottomSkewed.getX(),  halfNodeHeight, -bottomSize.getY() + bottomSkewed.getZ()))
                .rightBottomBack(new Point3D(bottomSize.getX() + bottomSkewed.getX(),  halfNodeHeight, bottomSize.getY() + bottomSkewed.getZ()))
                .textureSources(null)
                .colors(null)
                .blockNode(this)
                .build();
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return true;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return true;
    }
}
