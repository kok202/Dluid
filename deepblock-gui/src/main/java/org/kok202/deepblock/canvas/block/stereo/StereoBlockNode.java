package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.Group;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;

public abstract class StereoBlockNode extends BlockNode {
    public StereoBlockNode(Layer layer) {
        super(layer);
        createBlockModel(layer);
    }

    protected BlockHexahedron createHexahedron(
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

    public abstract void reshapeBlockModel(LayerProperties layerProperties);

    protected BlockHexahedron reshapeHexahedron(
            Point2D topSize, Point2D topPosition,
            Point2D bottomSize, Point2D bottomPosition,
            float height, Point3D position){
        Group sceneRoot = CanvasSingleton.getInstance().getMainCanvas().getMainScene().getSceneRoot();
        BlockHexahedron blockHexahedron = createHexahedron(topSize, topPosition, bottomSize, bottomPosition, height);
        blockHexahedron.setPosition(position.getX(), position.getY(), position.getZ());
        blockHexahedron.addedToScene(sceneRoot);
        return blockHexahedron;
    }

    protected Point2D getLeftPosition(Point2D leftSize, Point2D rightSize, float nodeGap){
        // FIXME : calc!
        return new Point2D(0, 0);
    }

    protected Point2D getRightPosition(Point2D leftSize, Point2D rightSize, float nodeGap){
        // FIXME : calc!
        return new Point2D(0, 0);
    }

    @Override
    public void refreshBlockCover() {
        getMainBlockModel().setColors(getBlockInfo().getLayerColors());
        getMainBlockModel().setTextureSources(getBlockInfo().getLayerTextureSources());
        getMainBlockModel().refreshBlockCover();
        getSubBlockModel().setColors(getBlockInfo().getActivationColors());
        getSubBlockModel().setTextureSources(getBlockInfo().getActivationTextureSources());
        getSubBlockModel().refreshBlockCover();
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
