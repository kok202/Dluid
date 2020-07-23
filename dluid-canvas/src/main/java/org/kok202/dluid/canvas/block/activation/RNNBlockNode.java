package org.kok202.dluid.canvas.block.activation;

import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.domain.entity.Layer;

public class RNNBlockNode extends ActivationBlockNode {
    public RNNBlockNode(Layer layer) {
        super(layer);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN__);
        refreshBlockCover();
    }
}
