package org.kokzoz.dluid.canvas.block.plain;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

public class InputBlockNode extends PlainBlockNode {
    public InputBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GREEN);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GREEN_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GREEN_);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendFrontByConnection() {
        return false;
    }

    @Override
    public boolean isPossibleToAppendFrontByDirection() {
        return false;
    }
}
