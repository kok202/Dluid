package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
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

    public abstract void reshapeBlockModel(Layer layer);

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
