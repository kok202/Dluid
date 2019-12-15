package org.kok202.dluid.canvas.block.activation;

import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;

public class OutputBlockNode extends ActivationBlockNode {
    public OutputBlockNode(Layer layer) {
        super(layer);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GRAY);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GRAY_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GRAY__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GRAY__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GRAY__);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
