package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.LayerProperties;
import org.kok202.deepblock.canvas.entity.SplitBlockProperty;
import org.kok202.deepblock.canvas.polygon.block.BlockHexahedron;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class SplitOutBlockNode extends StereoBlockNode {
    public SplitOutBlockNode(Layer layer) {
        super(layer);
        Color[] mainColorCover = new Color[]{
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.COLOR_GRAY,
                CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND};
        setBlockCover(mainColorCover, mainColorCover);
    }

    @Override
    protected void createBlockModel(Layer layer) {
        LayerProperties layerProperties = layer.getProperties();
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        Point2D topSize = new Point2D(
                layerProperties.getInputSize()[0],
                layerProperties.getInputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D topPosition = new Point2D(0, 0)
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftSize = new Point2D(
                splitBlockProperty.getSplitLeftSize()[0],
                splitBlockProperty.getSplitLeftSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = new Point2D(
                splitBlockProperty.getSplitRightSize()[0],
                splitBlockProperty.getSplitRightSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        Point2D rightPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        BlockHexahedron leftHexahedron = createHexahedron(topSize, topPosition, leftSize, leftPosition, CanvasConstant.NODE_HEIGHT);
        BlockHexahedron rightHexahedron = createHexahedron(topSize, topPosition, rightSize, rightPosition,  CanvasConstant.NODE_HEIGHT);
        setMainBlockModel(leftHexahedron);
        setSubBlockModel(rightHexahedron);
    }

    @Override
    public void reshapeBlockModel(Layer layer) {
        deleteHexahedron(getMainBlockModel());
        deleteHexahedron(getSubBlockModel());

        LayerProperties layerProperties = layer.getProperties();
        SplitBlockProperty splitBlockProperty = (SplitBlockProperty) layer.getExtra();
        Point2D topSize = new Point2D(
                layerProperties.getInputSize()[0],
                layerProperties.getInputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D topPosition = new Point2D(0, 0)
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftSize = new Point2D(
                splitBlockProperty.getSplitLeftSize()[0],
                splitBlockProperty.getSplitLeftSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = new Point2D(
                splitBlockProperty.getSplitRightSize()[0],
                splitBlockProperty.getSplitRightSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        Point2D rightPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        BlockHexahedron leftHexahedron = reshapeHexahedron(topSize, topPosition, leftSize, leftPosition, CanvasConstant.NODE_HEIGHT, getBlockInfo().getPosition());
        BlockHexahedron rightHexahedron = reshapeHexahedron(topSize, topPosition, rightSize, rightPosition,  CanvasConstant.NODE_HEIGHT, getBlockInfo().getPosition());
        setMainBlockModel(leftHexahedron);
        setSubBlockModel(rightHexahedron);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
