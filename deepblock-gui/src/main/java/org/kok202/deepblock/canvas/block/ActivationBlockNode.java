package org.kok202.deepblock.canvas.block;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.domain.structure.GraphNode;
import org.nd4j.linalg.activations.Activation;

public abstract class ActivationBlockNode extends BlockNode {
    public static final int LAYER_BLOCK_INDEX = 0;
    public static final int ACTIVATION_BLOCK_INDEX = 1;

    public ActivationBlockNode(Layer layer) {
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
        Point2D middleSize = getMiddleSize(topSize, bottomSize);
        BlockHexahedron layerHexahedron = createHexahedron(topSize, middleSize, getLayerModelHeight());
        BlockHexahedron activationHexahedron = createHexahedron(middleSize, bottomSize, getActivationModelHeight());
        getBlockHexahedronList().add(layerHexahedron);
        getBlockHexahedronList().add(activationHexahedron);
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
        deleteHexahedrons();
        Point2D middleSize = getMiddleSize(topSize, bottomSize);
        BlockHexahedron layerHexahedron = reshapeHexahedron(topSize, middleSize, getLayerModelHeight(), getLayerBlockPosition(getBlockInfo().getPosition()));
        BlockHexahedron activationHexahedron = reshapeHexahedron(middleSize, bottomSize, getActivationModelHeight(), getActivationBlockPosition(getBlockInfo().getPosition()));
        getBlockHexahedronList().add(layerHexahedron);
        getBlockHexahedronList().add(activationHexahedron);
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
        getBlockHexahedronList().get(ACTIVATION_BLOCK_INDEX).setVisible(isActivationFunctionExist());
    }

    @Override
    public void setPosition(double x, double y, double z){
        getBlockHexahedronList().get(LAYER_BLOCK_INDEX).setPosition(getLayerBlockPosition(x, y, z));
        getBlockHexahedronList().get(ACTIVATION_BLOCK_INDEX).setPosition(getActivationBlockPosition(x, y, z));
        getBlockInfo().setPosition(x, y, z);
    }

    private boolean isActivationFunctionExist(){
        return getBlockInfo().getLayer().getProperties().getActivationFunction() != null &&
                getBlockInfo().getLayer().getProperties().getActivationFunction() != Activation.IDENTITY;
    }

    private float getLayerModelHeight(){
        return !isActivationFunctionExist()? getBlockInfo().getHeight() : getBlockInfo().getHeight() * (1 - CanvasConstant.NODE_ACTIVATION_RATIO);
    }

    private float getActivationModelHeight(){
        return !isActivationFunctionExist()? 0 : getBlockInfo().getHeight() * (CanvasConstant.NODE_ACTIVATION_RATIO);
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
        return new Point3D(x, !isActivationFunctionExist()? y : y - getBlockInfo().getHeight() / 2 * (CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    private Point3D getActivationBlockPosition(double x, double y, double z){
        return new Point3D(x, !isActivationFunctionExist()? 0 : y + getBlockInfo().getHeight() / 2 * (1 - CanvasConstant.NODE_ACTIVATION_RATIO), z);
    }

    @Override
    public boolean isPossibleToAppendFront() {
        GraphNode<BlockNode> frontGraphNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontGraphNode.getIncomingNodes().isEmpty();
    }

    @Override
    public boolean isPossibleToAppendBack() {
        GraphNode<BlockNode> frontGraphNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findGraphNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontGraphNode.getOutgoingNodes().isEmpty();
    }
}
