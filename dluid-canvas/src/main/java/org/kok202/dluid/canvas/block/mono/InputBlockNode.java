package org.kok202.dluid.canvas.block.mono;

import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.domain.entity.Layer;

public class InputBlockNode extends MonoBlockNode {
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
