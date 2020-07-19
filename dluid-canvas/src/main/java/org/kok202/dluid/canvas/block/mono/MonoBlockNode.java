package org.kok202.dluid.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.polygon.block.BlockHexahedron;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.canvas.util.BlockNodeUtil;
import org.kok202.dluid.domain.entity.Layer;

public abstract class MonoBlockNode extends BlockNode {
    public static final int MONO_BLOCK_INDEX = 0;
    public static final int BLOCK_HEXAHEDRON_SIZE = 1;

    public MonoBlockNode(Layer layer) {
        super(layer, BLOCK_HEXAHEDRON_SIZE);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = BlockNodeUtil.getBlockNodeTopXY(layer);
        Point2D bottomSize = BlockNodeUtil.getBlockNodeBottomXY(layer);
        getBlockInfo().setHeight(calcHeight());

        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        BlockHexahedron layerHexahedron = createHexahedron(
                topSize, topSkewed,
                bottomSize, bottomSkewed,
                getBlockInfo().getHeight());
        getBlockHexahedronList().set(MONO_BLOCK_INDEX, layerHexahedron);
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
        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();
        getBlockInfo().setHeight(calcHeight());

        BlockHexahedron layerHexahedron = reshapeHexahedron(
                topSize, topSkewed,
                bottomSize, bottomSkewed,
                getBlockInfo().getHeight(),
                getBlockInfo().getPosition());
        getBlockHexahedronList().set(MONO_BLOCK_INDEX, layerHexahedron);
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
}
