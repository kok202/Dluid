package org.kok202.deepblock.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.domain.structure.GraphNode;
import org.nd4j.linalg.activations.Activation;

public abstract class MonoBlockNode extends BlockNode {
    public MonoBlockNode(Layer layer) {
        super(layer);
        createBlockModel(layer);
    }

    protected void createBlockModel(Layer layer){
        Point2D topSize = new Point2D(
                layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D middleSize = getMiddleSize(topSize, bottomSize);
        BlockHexahedron layerHexahedron = createHexahedron(topSize, middleSize, getLayerModelHeight());
        BlockHexahedron activationHexahedron = createHexahedron(middleSize, bottomSize, getActivationModelHeight());
        setMainBlockModel(layerHexahedron);
        setSubBlockModel(activationHexahedron);
    }

    private BlockHexahedron createHexahedron(Point2D topSize, Point2D bottomSize, float height) {
        topSize = topSize.multiply(0.5);
        bottomSize = bottomSize.multiply(0.5);
        float halfNodeHeight = height / 2;
        return BlockHexahedron.builder()
                .leftTopFront(new Point3D(-topSize.getX(), -halfNodeHeight, -topSize.getY()))
                .leftTopBack(new Point3D(-topSize.getX(), -halfNodeHeight, topSize.getY()))
                .leftBottomFront(new Point3D(-bottomSize.getX(),  halfNodeHeight, -bottomSize.getY()))
                .leftBottomBack(new Point3D(-bottomSize.getX(),  halfNodeHeight, bottomSize.getY()))
                .rightTopFront(new Point3D(topSize.getX(), -halfNodeHeight, -topSize.getY()))
                .rightTopBack(new Point3D(topSize.getX(), -halfNodeHeight, topSize.getY()))
                .rightBottomFront(new Point3D(bottomSize.getX(),  halfNodeHeight, -bottomSize.getY()))
                .rightBottomBack(new Point3D(bottomSize.getX(),  halfNodeHeight, bottomSize.getY()))
                .textureSources(null)
                .colors(null)
                .blockNode(this)
                .build();
    }

    public void reshapeBlockModel(Point2D topSize, Point2D bottomSize) {
        deleteHexahedron(getMainBlockModel());
        deleteHexahedron(getSubBlockModel());

        Point2D middleSize = getMiddleSize(topSize, bottomSize);
        BlockHexahedron layerHexahedron = reshapeHexahedron(topSize, middleSize, getLayerModelHeight(), getLayerBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron activationHexahedron = reshapeHexahedron(middleSize, bottomSize, getActivationModelHeight(), getActivationBlockPosition(getBlockInfo().getPosition()));
        setMainBlockModel(layerHexahedron);
        setSubBlockModel(activationHexahedron);
        refreshBlockCover();
    }

    private BlockHexahedron reshapeHexahedron(Point2D topSize, Point2D bottomSize, float height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, bottomSize, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    @Override
    public void refreshBlockCover(){
        super.refreshBlockCover();
        getSubBlockModel().setVisible(isActivationFunctionExist());
    }

    @Override
    public void setPosition(double x, double y, double z){
        getMainBlockModel().setPosition(getLayerBlockPosition(x, y, z));
        getSubBlockModel().setPosition(getActivationBlockPosition(x, y, z));
        getBlockInfo().setPosition(x, y, z);
    }

    private boolean isActivationFunctionExist(){
        return getBlockInfo().getLayer().getProperties().getActivationFunction() != null &&
                getBlockInfo().getLayer().getProperties().getActivationFunction() != Activation.IDENTITY;
    }

    private float getLayerModelHeight(){
        return !isActivationFunctionExist()? CanvasConstant.NODE_HEIGHT : CanvasConstant.NODE_HEIGHT * (1 - CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private float getActivationModelHeight(){
        return !isActivationFunctionExist()? 0 : CanvasConstant.NODE_HEIGHT * (CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private Point2D getMiddleSize(Point2D topSize, Point2D bottomSize){
        return !isActivationFunctionExist()?
                bottomSize :
                new Point2D(
                        bottomSize.getX() + (topSize.getX() - bottomSize.getX()) * CanvasConstant.NODE_ACTIVATION_RATIO,
                        bottomSize.getY() + (topSize.getY() - bottomSize.getY()) * CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private Point3D getLayerBlockPosition(Point3D position){
        return getLayerBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getActivationBlockPosition(Point3D position){
        return getActivationBlockPosition(position.getX(), position.getY(), position.getZ());
    }

    private Point3D getLayerBlockPosition(double x, double y, double z){
        return new Point3D(x, !isActivationFunctionExist()? y : y - CanvasConstant.NODE_HEIGHT / 2 * (CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    private Point3D getActivationBlockPosition(double x, double y, double z){
        return new Point3D(x, !isActivationFunctionExist()? 0 : y + CanvasConstant.NODE_HEIGHT / 2 * (1 - CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    @Override
    public boolean isPossibleToAppendFront() {
        GraphNode<BlockNode> frontGraphNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontGraphNode.isNoWay();
    }

    @Override
    public boolean isPossibleToAppendBack() {
        GraphNode<BlockNode> frontGraphNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontGraphNode.getAdjacentNodes().isEmpty();
    }
}
