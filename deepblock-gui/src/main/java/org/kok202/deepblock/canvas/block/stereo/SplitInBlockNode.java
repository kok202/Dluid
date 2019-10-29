package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.canvas.entity.SplitBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class SplitInBlockNode extends SplitBlockNode {
    public SplitInBlockNode(Layer layer) {
        super(layer);
        Color[] mainColorCover = new Color[]{
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND};
        setBlockCover(
                mainColorCover,
                mainColorCover);
    }

    @Override
    protected void createBlockModel(Layer layer) {
        LayerProperties layerProperties = layer.getProperties();
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        Point2D leftSize = new Point2D(
                splitBlockProperty.getSplitLeftSize()[0],
                splitBlockProperty.getSplitLeftSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = new Point2D(
                splitBlockProperty.getSplitRightSize()[0],
                splitBlockProperty.getSplitRightSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layerProperties.getOutputSize()[0],
                layerProperties.getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomPosition = new Point2D(0, 0)
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        Point2D rightPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        BlockHexahedron leftHexahedron = createHexahedron(leftSize, leftPosition, bottomSize, bottomPosition, getBlockInfo().getHeight());
        BlockHexahedron rightHexahedron = createHexahedron(rightSize, rightPosition, bottomSize, bottomPosition, getBlockInfo().getHeight());
        getBlockHexahedronList().add(leftHexahedron);
        getBlockHexahedronList().add(rightHexahedron);
    }

    @Override
    public void reshapeBlockModel(Layer layer) {
        deleteHexahedrons();
        LayerProperties layerProperties = layer.getProperties();
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        Point2D leftSize = new Point2D(
                splitBlockProperty.getSplitLeftSize()[0],
                splitBlockProperty.getSplitLeftSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = new Point2D(
                splitBlockProperty.getSplitRightSize()[0],
                splitBlockProperty.getSplitRightSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layerProperties.getOutputSize()[0],
                layerProperties.getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomPosition = new Point2D(0, 0)
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        Point2D rightPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        BlockHexahedron leftHexahedron = reshapeHexahedron(leftSize, leftPosition, bottomSize, bottomPosition, getBlockInfo().getHeight(), getBlockInfo().getPosition());
        BlockHexahedron rightHexahedron = reshapeHexahedron(rightSize, rightPosition, bottomSize, bottomPosition,  getBlockInfo().getHeight(), getBlockInfo().getPosition());
        getBlockHexahedronList().add(leftHexahedron);
        getBlockHexahedronList().add(rightHexahedron);
        refreshBlockCover();
    }
}
