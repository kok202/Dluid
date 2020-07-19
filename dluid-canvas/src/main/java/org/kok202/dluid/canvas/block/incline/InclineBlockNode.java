package org.kok202.dluid.canvas.block.incline;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.canvas.polygon.block.BlockHexahedron;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.BlockNodeUtil;
import org.kok202.dluid.domain.entity.Layer;
import org.kok202.dluid.domain.entity.enumerator.ActivationWrapper;

import java.util.Map;

public abstract class InclineBlockNode extends BlockNode {
    public static final int INPUT_BLOCK_INDEX = 0;
    public static final int CENTER_BLOCK_INDEX = 1;
    public static final int OUTPUT_BLOCK_INDEX = 2;
    public static final int BLOCK_HEXAHEDRON_SIZE = 3;

    public InclineBlockNode(Layer layer) {
        super(layer, BLOCK_HEXAHEDRON_SIZE);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = BlockNodeUtil.getBlockNodeTopXY(layer);
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        Point2D centerTopSize = getCenterSize(topSize, bottomSize);
        Point2D centerBottomSize = getCenterSize(topSize, bottomSize);
        refreshTopSkewed(topSize, centerTopSize);
        refreshBottomSkewed(centerBottomSize, bottomSize);
        getBlockInfo().setHeight(calcHeight());

        Point3D topSkewed = getTopSkewed();
        Point3D centerTopSkewed = getCenterTopSkewed();
        Point3D centerBottomSkewed = getCenterBottomSkewed();
        Point3D bottomSkewed = getBottomSkewed();

        BlockHexahedron inputHexahedron = createHexahedron(topSize, topSkewed, centerTopSize, centerTopSkewed, getInputModelHeight());
        BlockHexahedron centerHexahedron = createHexahedron(centerTopSize, centerTopSkewed, centerBottomSize, centerBottomSkewed, getCenterModelHeight());
        BlockHexahedron outputHexahedron = createHexahedron(centerBottomSize, centerBottomSkewed, bottomSize, bottomSkewed, getOutputModelHeight());
        getBlockHexahedronList().set(INPUT_BLOCK_INDEX, inputHexahedron);
        getBlockHexahedronList().set(CENTER_BLOCK_INDEX, centerHexahedron);
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
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        Point2D centerTopSize = getCenterSize(topSize, bottomSize);
        Point2D centerBottomSize = getCenterSize(topSize, bottomSize);
        refreshTopSkewed(topSize, centerTopSize);
        refreshBottomSkewed(centerBottomSize, bottomSize);
        getBlockInfo().setHeight(calcHeight());

        Point3D topSkewed = getTopSkewed();
        Point3D centerTopSkewed = getCenterTopSkewed();
        Point3D centerBottomSkewed = getCenterBottomSkewed();
        Point3D bottomSkewed = getBottomSkewed();

        BlockHexahedron inputHexahedron = reshapeHexahedron(topSize, topSkewed, centerTopSize, centerTopSkewed, getInputModelHeight(), getInputBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron centerHexahedron = reshapeHexahedron(centerTopSize, centerTopSkewed, centerBottomSize, centerBottomSkewed, getCenterModelHeight(), getCenterBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron outputHexahedron = reshapeHexahedron(centerBottomSize, centerBottomSkewed, bottomSize, bottomSkewed, getOutputModelHeight(), getOutputBlockPosition(getBlockInfo().getPosition()));
        getBlockHexahedronList().set(INPUT_BLOCK_INDEX, inputHexahedron);
        getBlockHexahedronList().set(CENTER_BLOCK_INDEX, centerHexahedron);
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

    private void refreshTopSkewed(Point2D topSize, Point2D centerTopSize){
        getBlockInfo().getExtra().setTopSkewed(new Point3D((topSize.getX() - centerTopSize.getX()) / 2, 0, 0));
    }

    private void refreshBottomSkewed(Point2D centerBottomSize, Point2D bottomSize){
        getBlockInfo().getExtra().setBottomSkewed(new Point3D((centerBottomSize.getX() - bottomSize.getX()) / 2, 0, 0));
    }

    @Override
    public final void refreshBlockCover(){
        super.refreshBlockCover();
        Map<BlockFace, Color> outputColor = isActivationFunctionExist()?
                getBlockInfo().getColorMapList().get(OUTPUT_BLOCK_INDEX) :
                getBlockInfo().getColorMapList().get(CENTER_BLOCK_INDEX);
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
        getBlockHexahedronList().get(CENTER_BLOCK_INDEX).setPosition(getCenterBlockPosition(x, y, z));
        getBlockHexahedronList().get(OUTPUT_BLOCK_INDEX).setPosition(getOutputBlockPosition(x, y, z));
        getBlockInfo().setPosition(x, y, z);
    }

    private boolean isActivationFunctionExist(){
        return getBlockLayer().getProperties().getActivationFunction() != null &&
                getBlockLayer().getProperties().getActivationFunction() != ActivationWrapper.IDENTITY;
    }

    private double getInputModelHeight(){
        return getBlockInfo().getHeight() * (CanvasConstant.NODE_INCLINE_SKEWED_RATIO);
    }

    private double getCenterModelHeight(){
        return getBlockInfo().getHeight() * (1 - CanvasConstant.NODE_INCLINE_SKEWED_RATIO * 2);
    }

    private double getOutputModelHeight(){
        return getBlockInfo().getHeight() * (CanvasConstant.NODE_INCLINE_SKEWED_RATIO);
    }

    private Point3D getInputBlockPosition(Point3D position){
        return getInputBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getCenterBlockPosition(Point3D position){
        return getCenterBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getOutputBlockPosition(Point3D position){
        return getOutputBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point2D getCenterSize(Point2D topSize, Point2D bottomSize){
        return new Point2D(
                Math.max(topSize.getX(), bottomSize.getX()),
                Math.max(topSize.getY(), bottomSize.getY()));
    }

    private Point3D getCenterTopSkewed(){
        return new Point3D(0, 0, 0);
    }

    private Point3D getCenterBottomSkewed(){
        return new Point3D(0, 0, 0);
    }

    private Point3D getInputBlockPosition(double x, double y, double z){
        return new Point3D(x, y - getBlockInfo().getHeight() / 2 * (1 - CanvasConstant.NODE_INCLINE_SKEWED_RATIO), z);
    }

    private Point3D getCenterBlockPosition(double x, double y, double z){
        return new Point3D(x, y, z);
    }

    private Point3D getOutputBlockPosition(double x, double y, double z){
        return new Point3D(x, y + getBlockInfo().getHeight() / 2 * (1 - CanvasConstant.NODE_INCLINE_SKEWED_RATIO), z);
    }
}
