package org.kok202.deepblock.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.entity.SkewedBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public abstract class MonoBlockNode extends BlockNode {

    public MonoBlockNode(Layer layer) {
        super(layer);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = new Point2D(
                layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT);

        Point3D topSkewed = getTopSkewed(layer);
        Point3D bottomSkewed = getBottomSkewed(layer);
        BlockHexahedron layerHexahedron = createHexahedron(
                topSize, topSkewed,
                bottomSize, bottomSkewed,
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

    public final void reshapeBlockModel() {
        deleteHexahedrons();
        Layer layer = getBlockInfo().getLayer();
        Point2D topSize = new Point2D(layer.getProperties().getInputSize()[0], layer.getProperties().getInputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(layer.getProperties().getOutputSize()[0], layer.getProperties().getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point3D topSkewed = getTopSkewed(layer);
        Point3D bottomSkewed = getBottomSkewed(layer);

        BlockHexahedron layerHexahedron = reshapeHexahedron(
                topSize, topSkewed,
                bottomSize, bottomSkewed,
                getBlockInfo().getHeight(),
                getBlockInfo().getPosition());
        getBlockHexahedronList().add(layerHexahedron);
        refreshBlockCover();
    }

    private BlockHexahedron reshapeHexahedron(Point2D topSize, Point3D topSkewed, Point2D bottomSize, Point3D bottomSkewed, double height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, topSkewed, bottomSize, bottomSkewed, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    @Override
    public void setHeight(double height){
        getBlockInfo().setHeight(height);
    }

    @Override
    public void setPosition(double x, double y, double z){
        getBlockHexahedronList().forEach(blockHexahedron -> blockHexahedron.setPosition(x,y,z));
        getBlockInfo().setPosition(x, y, z);
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return true;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return true;
    }

    private Point3D getTopSkewed(Layer layer){
        if(layer.getExtra() == null)
            return new Point3D(0, 0, 0);
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) layer.getExtra();
        return (skewedBlockProperty.getTopSkewed() == null)?
                new Point3D(0, 0, 0):
                skewedBlockProperty.getTopSkewed();
    }

    private Point3D getBottomSkewed(Layer layer){
        if(layer.getExtra() == null)
            return new Point3D(0, 0, 0);
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) layer.getExtra();
        return (skewedBlockProperty.getBottomSkewed() == null)?
                new Point3D(0, 0, 0):
                skewedBlockProperty.getBottomSkewed();
    }

    @Override
    public Point3D getTopCenterPosition(int index){
        return getBlockInfo().getPosition()
                .add(new Point3D(0, -getBlockInfo().getHeight() / 2, 0))
                .add(getTopSkewed(getBlockInfo().getLayer()));
    }

    @Override
    public Point3D getBottomCenterPosition(int index){
        return getBlockInfo().getPosition()
                .add(new Point3D(0, getBlockInfo().getHeight() / 2, 0))
                .add(getBottomSkewed(getBlockInfo().getLayer()));
    }
}
