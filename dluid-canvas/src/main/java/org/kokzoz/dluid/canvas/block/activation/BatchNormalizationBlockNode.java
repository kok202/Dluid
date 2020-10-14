package org.kokzoz.dluid.canvas.block.activation;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

public class BatchNormalizationBlockNode extends ActivationBlockNode {
    public BatchNormalizationBlockNode(Layer layer) {
        super(layer);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_PURPLE);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(LAYER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_PURPLE__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_PURPLE__);
        setBlockColor(ACTIVATION_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_PURPLE__);
        refreshBlockCover();
    }
}
