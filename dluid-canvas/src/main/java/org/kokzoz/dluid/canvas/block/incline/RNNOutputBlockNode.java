package org.kokzoz.dluid.canvas.block.incline;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

public class RNNOutputBlockNode extends InclineBlockNode {
    public RNNOutputBlockNode(Layer layer) {
        super(layer);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(INPUT_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        setBlockColor(CENTER_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(CENTER_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(CENTER_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(CENTER_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN__);
        setBlockColor(OUTPUT_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN__);
        refreshBlockCover();
    }
}
