package org.kok202.dluid.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.entity.SkewedBlockProperty;
import org.kok202.dluid.canvas.polygon.block.BlockHexahedron;
import org.kok202.dluid.canvas.singleton.CanvasSingleton;
import org.kok202.dluid.domain.exception.CanNotFindGraphNodeException;
import org.kok202.dluid.domain.structure.GraphNode;

public abstract class MonoBlockNode extends BlockNode {
    public static final int MONO_BLOCK_INDEX = 0;
    public static final int BLOCK_HEXAHEDRON_SIZE = 1;

    public MonoBlockNode(Layer layer) {
        super(layer, BLOCK_HEXAHEDRON_SIZE);
    }

    @Override
    protected final void createBlockModel(Layer layer){
        Point2D topSize = new Point2D(
                layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT);

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
        Point2D topSize = new Point2D(layer.getProperties().getInputSize()[0], layer.getProperties().getInputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(layer.getProperties().getOutputSize()[0], layer.getProperties().getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point3D topSkewed = getTopSkewed();
        Point3D bottomSkewed = getBottomSkewed();

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

    @Override
    public boolean isPossibleToAppendFront() {
        try{
            GraphNode<BlockNode> frontGraphNode = CanvasSingleton.getInstance()
                    .getBlockNodeManager()
                    .findGraphNodeByLayerId(this.getBlockLayer().getId());
            return frontGraphNode.getIncomingNodes().isEmpty();
        }catch (CanNotFindGraphNodeException canNotFindGraphNodeException){
            // When initialize, it is not registered in graph manger.
            return true;
        }
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return true;
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
