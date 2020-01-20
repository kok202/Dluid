package org.kok202.dluid.canvas.block.recurrent;

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

public abstract class RecurrentBlockNode extends BlockNode {
    public static final int INPUT_BLOCK_INDEX = 0;
    public static final int OUTPUT_BLOCK_INDEX = 1;
    public static final int BLOCK_HEXAHEDRON_SIZE = 2;

    public RecurrentBlockNode(Layer layer) {
        super(layer, BLOCK_HEXAHEDRON_SIZE);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = BlockNodeUtil.getBlockNodeTopXY(layer);
        Point2D middleSize = topSize;
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        refreshBottomSkewed(middleSize, bottomSize);

        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        Point3D middleSkewed = getMiddleSkewed();

        BlockHexahedron inputHexahedron = createHexahedron(topSize, topSkewed, middleSize, middleSkewed, getLayerModelHeight());
        BlockHexahedron outputHexahedron = createHexahedron(middleSize, middleSkewed, bottomSize, bottomSkewed, getActivationModelHeight());
        getBlockHexahedronList().set(INPUT_BLOCK_INDEX, inputHexahedron);
        getBlockHexahedronList().set(OUTPUT_BLOCK_INDEX, outputHexahedron);
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
        Point2D middleSize = topSize;
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        refreshBottomSkewed(middleSize, bottomSize);

        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        Point3D middleSkewed = getMiddleSkewed();

        BlockHexahedron inputHexahedron = reshapeHexahedron(topSize, topSkewed, middleSize, middleSkewed, getLayerModelHeight(), getInputBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron outputHexahedron = reshapeHexahedron(middleSize, middleSkewed, bottomSize, bottomSkewed, getActivationModelHeight(), getOutputBlockPosition(getBlockInfo().getPosition()));
        getBlockHexahedronList().set(INPUT_BLOCK_INDEX, inputHexahedron);
        getBlockHexahedronList().set(OUTPUT_BLOCK_INDEX, outputHexahedron);
        refreshBlockCover();
    }

    private BlockHexahedron reshapeHexahedron(Point2D topSize, Point3D topSkewed, Point2D bottomSize, Point3D bottomSkewed, double height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, topSkewed, bottomSize, bottomSkewed, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    private void refreshBottomSkewed(Point2D middleSize, Point2D bottomSize){
        SkewedBlockProperty skewedBlockProperty = (SkewedBlockProperty) getBlockInfo().getExtra();
        skewedBlockProperty.setBottomSkewed(new Point3D((middleSize.getX() - bottomSize.getX()) / 2, 0, 0));
    }

    @Override
    public final void refreshBlockCover(){
        super.refreshBlockCover();
        Map<BlockFace, Color> outputColor = isActivationFunctionExist()?
                getBlockInfo().getColorMapList().get(OUTPUT_BLOCK_INDEX) :
                getBlockInfo().getColorMapList().get(INPUT_BLOCK_INDEX);
        getBlockHexahedronList().get(OUTPUT_BLOCK_INDEX).setColorMap(outputColor);
        getBlockHexahedronList().get(OUTPUT_BLOCK_INDEX).refreshBlockCover();
    }

    @Override
    public void setHeight(double height){
        getBlockInfo().setHeight(height);
    }

    @Override
    public final void setPosition(double x, double y, double z){
        getBlockHexahedronList().get(INPUT_BLOCK_INDEX).setPosition(getInputBlockPosition(x, y, z));
        getBlockHexahedronList().get(OUTPUT_BLOCK_INDEX).setPosition(getOutputBlockPosition(x, y, z));
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

    private Point3D getInputBlockPosition(Point3D position){
        return getInputBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getOutputBlockPosition(Point3D position){
        return getOutputBlockPosition(position.getX(), position.getY(), position.getZ());
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

    private Point3D getMiddleSkewed(){
        return new Point3D(0, 0, 0);
    }

    private Point3D getInputBlockPosition(double x, double y, double z){
        return new Point3D(x, y - getBlockInfo().getHeight() / 2 * (CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    private Point3D getOutputBlockPosition(double x, double y, double z){
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
