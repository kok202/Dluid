package org.kokzoz.dluid.canvas.block.activation;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

public class FeedForwardBlockNode extends ActivationBlockNode {
    public FeedForwardBlockNode(Layer layer) {
        super(layer);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_BLUE);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_BLUE_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_BLUE_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_BLUE_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_BLUE_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_BLUE__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_BLUE__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_BLUE__);
        refreshBlockCover();
    }
}
