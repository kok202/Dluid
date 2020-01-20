package org.kok202.dluid.canvas.block.recurrent;

import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;

public class LSTMBlockNode extends RecurrentBlockNode {
    public LSTMBlockNode(Layer layer) {
        super(layer);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        setBlockColor(RECURRENT_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(RECURRENT_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(RECURRENT_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(RECURRENT_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN__);
        refreshBlockCover();
    }
}
