package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.entity.SplitBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public abstract class SplitBlockNode extends BlockNode {
    public static final int LEFT_BLOCK_INDEX = 0;
    public static final int RIGHT_BLOCK_INDEX = 1;

    public SplitBlockNode(Layer layer) {
        super(layer);
    }

    protected final BlockHexahedron createHexahedron(
            Point2D topSize, Point2D topPosition,
            Point2D bottomSize, Point2D bottomPosition,
            double height) {
        topSize = topSize.multiply(0.5);
        bottomSize = bottomSize.multiply(0.5);
        double halfNodeHeight = height / 2;
        return BlockHexahedron.builder()
                .leftTopFront(new Point3D(-topSize.getX() + topPosition.getX(), -halfNodeHeight, -topSize.getY() + topPosition.getY()))
                .leftTopBack(new Point3D(-topSize.getX()  + topPosition.getX(), -halfNodeHeight, topSize.getY() + topPosition.getY()))
                .leftBottomFront(new Point3D(-bottomSize.getX() + bottomPosition.getX(),  halfNodeHeight, -bottomSize.getY() + bottomPosition.getY()))
                .leftBottomBack(new Point3D(-bottomSize.getX() + bottomPosition.getX(),  halfNodeHeight, bottomSize.getY() + bottomPosition.getY()))
                .rightTopFront(new Point3D(topSize.getX() + topPosition.getX(), -halfNodeHeight, -topSize.getY() + topPosition.getY()))
                .rightTopBack(new Point3D(topSize.getX() + topPosition.getX(), -halfNodeHeight, topSize.getY() + topPosition.getY()))
                .rightBottomFront(new Point3D(bottomSize.getX() + bottomPosition.getX(),  halfNodeHeight, -bottomSize.getY() + bottomPosition.getY()))
                .rightBottomBack(new Point3D(bottomSize.getX() + bottomPosition.getX(),  halfNodeHeight, bottomSize.getY() + bottomPosition.getY()))
                .textureSources(null)
                .colors(null)
                .blockNode(this)
                .build();
    }

    protected final BlockHexahedron reshapeHexahedron(
            Point2D topSize, Point2D topPosition,
            Point2D bottomSize, Point2D bottomPosition,
            double height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, topPosition, bottomSize, bottomPosition, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    protected final Point2D getLeftSize(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getSplitLeftSize()[0],
                splitBlockProperty.getSplitLeftSize()[1]);
    }

    protected final Point2D getRightSize(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getSplitRightSize()[0],
                splitBlockProperty.getSplitRightSize()[1]);
    }

    protected final Point2D getLeftTopSkewed(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getLeftTopSkewed().getX(),
                splitBlockProperty.getLeftTopSkewed().getZ());
    }

    protected final Point2D getRightTopSkewed(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getRightTopSkewed().getX(),
                splitBlockProperty.getRightTopSkewed().getZ());
    }

    protected final Point2D getLeftBottomSkewed(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getLeftBottomSkewed().getX(),
                splitBlockProperty.getLeftBottomSkewed().getZ());
    }

    protected final Point2D getRightBottomSkewed(){
        if(getBlockInfo().getLayer().getExtra() == null)
            return new Point2D(0,0);
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) getBlockInfo().getLayer().getExtra();
        return new Point2D(
                splitBlockProperty.getRightBottomSkewed().getX(),
                splitBlockProperty.getRightBottomSkewed().getZ());
    }

    protected final Point2D getLeftPosition(Point2D leftSize, Point2D rightSize, double nodeGap){
        return new Point2D(-(leftSize.getX() + nodeGap) / 2 , 0);
    }

    protected final Point2D getRightPosition(Point2D leftSize, Point2D rightSize, double nodeGap){
        return new Point2D((rightSize.getX() + nodeGap) / 2 , 0);
    }

    protected final Point2D getBalancedLeftPosition(Point2D leftSize, Point2D rightSize, double nodeGap){
        double totalSize = leftSize.getX() + rightSize.getX() + nodeGap;
        double leftStart = -totalSize / 2;
        return new Point2D(leftStart + leftSize.getX() / 2 , 0);
    }

    protected final Point2D getBalancedRightPosition(Point2D leftSize, Point2D rightSize, double nodeGap){
        double totalSize = leftSize.getX() + rightSize.getX() + nodeGap;
        double rightEnd = totalSize / 2;
        return new Point2D(rightEnd - rightSize.getX() / 2 , 0);
    }

    public boolean isLeftSide(BlockHexahedron blockHexahedron){
        return (getBlockHexahedronList().get(LEFT_BLOCK_INDEX) == blockHexahedron);
    }

    public boolean isRightSide(BlockHexahedron blockHexahedron){
        return (getBlockHexahedronList().get(RIGHT_BLOCK_INDEX) == blockHexahedron);
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
        return false;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
