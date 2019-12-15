package org.kok202.dluid.canvas.block.activation;

import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;

public class Convolution2DBlockNode extends ActivationBlockNode {
    public Convolution2DBlockNode(Layer layer) {
        super(layer);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_RED);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_RED_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_RED_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_RED_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_RED_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_RED__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_RED__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_RED__);
        refreshBlockCover();
    }
}
