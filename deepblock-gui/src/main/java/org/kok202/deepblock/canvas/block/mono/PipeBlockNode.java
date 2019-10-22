package org.kok202.deepblock.canvas.block.mono;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public class PipeBlockNode extends MonoBlockNode {
    public PipeBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                },
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                });
    }

    @Override
    protected void createBlockModel(Layer layer){
        Point2D topSize = new Point2D(
                layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT);
        //FIXME
        Point2D topPosition = new Point2D(
                0,
                0);
        Point2D bottomPosition = new Point2D(
                0,
                0);
        float height = 0;
        BlockHexahedron layerHexahedron = createHexahedron(
                topSize, topPosition,
                bottomSize, bottomPosition,
                height);
        setMainBlockModel(layerHexahedron);
    }

    private BlockHexahedron createHexahedron(
            Point2D topSize, Point2D topPosition,
            Point2D bottomSize, Point2D bottomPosition,
            float height) {
        topSize = topSize.multiply(0.5);
        bottomSize = bottomSize.multiply(0.5);
        float halfNodeHeight = height / 2;
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

    @Override
    public void reshapeBlockModel(Point2D topSize, Point2D bottomSize) {
        deleteHexahedron(getMainBlockModel());
        //FIXME
        Point2D topPosition = new Point2D(
                0,
                0);
        Point2D bottomPosition = new Point2D(
                0,
                0);
        float height = 0;
        BlockHexahedron layerHexahedron = reshapeHexahedron(
                topSize, topPosition,
                bottomSize, bottomPosition,
                height, getBlockInfo().getPosition());
        setMainBlockModel(layerHexahedron);
        refreshBlockCover();
    }

    private BlockHexahedron reshapeHexahedron(
            Point2D topSize, Point2D topPosition,
            Point2D bottomSize, Point2D bottomPosition,
            float height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, topPosition, bottomSize, bottomPosition, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    @Override
    public void refreshBlockCover(){
        super.refreshBlockCover();
    }

    @Override
    public void setPosition(double x, double y, double z){
        getMainBlockModel().setPosition(x, y, z);
        getBlockInfo().setPosition(x, y, z);
    }
}
