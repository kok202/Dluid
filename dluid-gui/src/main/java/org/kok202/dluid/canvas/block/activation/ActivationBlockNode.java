package org.kok202.dluid.canvas.block.activation;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.ai.entity.enumerator.ActivationWrapper;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.canvas.polygon.block.BlockHexahedron;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.BlockNodeUtil;

import java.util.Map;

public abstract class ActivationBlockNode extends BlockNode {
    public static final int LAYER_BLOCK_INDEX = 0;
    public static final int ACTIVATION_BLOCK_INDEX = 1;
    public static final int BLOCK_HEXAHEDRON_SIZE = 2;

    public ActivationBlockNode(Layer layer) {
        super(layer, BLOCK_HEXAHEDRON_SIZE);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = BlockNodeUtil.getBlockNodeTopXY(layer);
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        Point2D middleSize = getMiddleSize(topSize, bottomSize);

        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        Point3D middleSkewed = getMiddleSkewed(topSkewed, bottomSkewed);

        BlockHexahedron layerHexahedron = createHexahedron(topSize, topSkewed, middleSize, middleSkewed, getLayerModelHeight());
        BlockHexahedron activationHexahedron = createHexahedron(middleSize, middleSkewed, bottomSize, bottomSkewed, getActivationModelHeight());
        getBlockHexahedronList().set(LAYER_BLOCK_INDEX, layerHexahedron);
        getBlockHexahedronList().set(ACTIVATION_BLOCK_INDEX, activationHexahedron);
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
                .blockNode(this)
                .build();
    }

    public final void reshapeBlockModel() {
        deleteHexahedrons();
        Layer layer = getBlockLayer();
        Point2D topSize = BlockNodeUtil.getBlockNodeTopXY(layer);
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        Point2D middleSize = getMiddleSize(topSize, bottomSize);
        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        Point3D middleSkewed = getMiddleSkewed(topSkewed, bottomSkewed);

        BlockHexahedron layerHexahedron = reshapeHexahedron(topSize, topSkewed, middleSize, middleSkewed, getLayerModelHeight(), getLayerBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron activationHexahedron = reshapeHexahedron(middleSize, middleSkewed, bottomSize, bottomSkewed, getActivationModelHeight(), getActivationBlockPosition(getBlockInfo().getPosition()));
        getBlockHexahedronList().set(LAYER_BLOCK_INDEX, layerHexahedron);
        getBlockHexahedronList().set(ACTIVATION_BLOCK_INDEX, activationHexahedron);
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
    public final void refreshBlockCover(){
        super.refreshBlockCover();
        Map<BlockFace, Color> outputColor = isActivationFunctionExist()?
                getBlockInfo().getColorMapList().get(ACTIVATION_BLOCK_INDEX) :
                getBlockInfo().getColorMapList().get(LAYER_BLOCK_INDEX);
        getBlockHexahedronList().get(ACTIVATION_BLOCK_INDEX).setColorMap(outputColor);
        getBlockHexahedronList().get(ACTIVATION_BLOCK_INDEX).refreshBlockCover();
    }

    @Override
    public void setHeight(double height){
        getBlockInfo().setHeight(height);
    }

    @Override
    public final void setPosition(double x, double y, double z){
        getBlockHexahedronList().get(LAYER_BLOCK_INDEX).setPosition(getLayerBlockPosition(x, y, z));
        getBlockHexahedronList().get(ACTIVATION_BLOCK_INDEX).setPosition(getActivationBlockPosition(x, y, z));
        getBlockInfo().setPosition(x, y, z);
    }

    private boolean isActivationFunctionExist(){
        return getBlockLayer().getProperties().getActivationFunction() != null &&
                getBlockLayer().getProperties().getActivationFunction() != ActivationWrapper.IDENTITY;
    }

    private double getLayerModelHeight(){
        return getBlockInfo().getHeight() * (1 - CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private double getActivationModelHeight(){
        return getBlockInfo().getHeight() * (CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private Point2D getMiddleSize(Point2D topSize, Point2D bottomSize){
        return new Point2D(
                        bottomSize.getX() + (topSize.getX() - bottomSize.getX()) * CanvasConstant.NODE_ACTIVATION_RATIO,
                        bottomSize.getY() + (topSize.getY() - bottomSize.getY()) * CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private Point3D getLayerBlockPosition(Point3D position){
        return getLayerBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getActivationBlockPosition(Point3D position){
        return getActivationBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getTopSkewed(){
        if(getBlockInfo().getExtra() == null)
            return new Point3D(0, 0, 0);
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) getBlockInfo().getExtra();
        return (skewedBlockProperty.getTopSkewed() == null)?
                new Point3D(0, 0, 0):
                skewedBlockProperty.getTopSkewed();
    }

    private Point3D getBottomSkewed(){
        if(getBlockInfo().getExtra() == null)
            return new Point3D(0, 0, 0);
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) getBlockInfo().getExtra();
        return (skewedBlockProperty.getBottomSkewed() == null)?
                new Point3D(0, 0, 0):
                skewedBlockProperty.getBottomSkewed();
    }

    private Point3D getMiddleSkewed(Point3D topSkewed, Point3D bottomSkewed){
        return new Point3D(
                        bottomSkewed.getX() + (topSkewed.getX() - bottomSkewed.getX()) * CanvasConstant.NODE_ACTIVATION_RATIO,
                        bottomSkewed.getY() + (topSkewed.getY() - bottomSkewed.getY()) * CanvasConstant.NODE_ACTIVATION_RATIO,
                        bottomSkewed.getZ() + (topSkewed.getZ() - bottomSkewed.getZ()) * CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private Point3D getLayerBlockPosition(double x, double y, double z){
        return new Point3D(x, y - getBlockInfo().getHeight() / 2 * (CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    private Point3D getActivationBlockPosition(double x, double y, double z){
        return new Point3D(x, y + getBlockInfo().getHeight() / 2 * (1 - CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    @Override
    public Point3D getTopCenterPosition(){
        return getBlockInfo().getPosition()
                .add(new Point3D(0, -getBlockInfo().getHeight() / 2, 0))
                .add(getTopSkewed());
    }

    @Override
    public Point3D getBottomCenterPosition(){
        return getBlockInfo().getPosition()
                .add(new Point3D(0, getBlockInfo().getHeight() / 2, 0))
                .add(getBottomSkewed());
    }
}
