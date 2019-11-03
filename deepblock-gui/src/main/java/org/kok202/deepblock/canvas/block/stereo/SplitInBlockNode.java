package org.kok202.deepblock.canvas.block.stereo;

import javafx.geometry.Point2D;
import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.ai.entity.LayerProperties;
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
        Point2D leftSize = getLeftSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = getRightSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layerProperties.getOutputSize()[0],
                layerProperties.getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftTopPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP).add(getLeftTopSkewed());
        Point2D rightTopPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP).add(getRightTopSkewed());
        Point2D leftBottomPosition = new Point2D(0,0).add(getLeftBottomSkewed());
        Point2D rightBottomPosition = new Point2D(0,0).add(getRightBottomSkewed());
        BlockHexahedron leftHexahedron = createHexahedron(leftSize, leftTopPosition, bottomSize, leftBottomPosition, getBlockInfo().getHeight());
        BlockHexahedron rightHexahedron = createHexahedron(rightSize, rightTopPosition, bottomSize, rightBottomPosition, getBlockInfo().getHeight());
        getBlockHexahedronList().add(leftHexahedron);
        getBlockHexahedronList().add(rightHexahedron);
    }

    @Override
    public void reshapeBlockModel() {
        deleteHexahedrons();
        LayerProperties layerProperties = getBlockInfo().getLayer().getProperties();
        Point2D leftSize = getLeftSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = getRightSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D bottomSize = new Point2D(
                layerProperties.getOutputSize()[0],
                layerProperties.getOutputSize()[1])
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D leftTopPosition = getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP).add(getLeftTopSkewed());
        Point2D rightTopPosition = getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP).add(getRightTopSkewed());
        Point2D leftBottomPosition = new Point2D(0,0).add(getLeftBottomSkewed());
        Point2D rightBottomPosition = new Point2D(0,0).add(getRightBottomSkewed());
        BlockHexahedron leftHexahedron = reshapeHexahedron(leftSize, leftTopPosition, bottomSize, leftBottomPosition, getBlockInfo().getHeight(), getBlockInfo().getPosition());
        BlockHexahedron rightHexahedron = reshapeHexahedron(rightSize, rightTopPosition, bottomSize, rightBottomPosition,  getBlockInfo().getHeight(), getBlockInfo().getPosition());
        getBlockHexahedronList().add(leftHexahedron);
        getBlockHexahedronList().add(rightHexahedron);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return true;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return true;
    }

    @Override
    public Point3D getTopCenterPosition(int index){
        Point2D leftSize = getLeftSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D rightSize = getRightSize()
                .multiply(CanvasConstant.NODE_UNIT);
        Point2D position = (index == LEFT_BLOCK_INDEX)?
                getLeftPosition(leftSize, rightSize, CanvasConstant.NODE_GAP) :
                getRightPosition(leftSize, rightSize, CanvasConstant.NODE_GAP);
        Point2D skewed = (index == LEFT_BLOCK_INDEX)?
                getLeftTopSkewed() :
                getRightTopSkewed();
        return new Point3D(position.getX(), 0, position.getY())
                .add(new Point3D(0, -getBlockInfo().getHeight() / 2, 0))
                .add(new Point3D(skewed.getX(), 0, skewed.getY()));
    }

    @Override
    public Point3D getBottomCenterPosition(int index){
        Point2D skewed = (index == LEFT_BLOCK_INDEX)?
                getLeftBottomSkewed() :
                getRightBottomSkewed();
        return new Point3D(0,0,0)
                .add(new Point3D(0, -getBlockInfo().getHeight() / 2, 0))
                .add(new Point3D(skewed.getX(), 0, skewed.getY()));
    }
}
