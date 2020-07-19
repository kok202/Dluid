package org.kok202.dluid.canvas.block.mono;

import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.domain.entity.Layer;

public class SwitchBlockNode extends MonoBlockNode {
    public SwitchBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_PURPLE);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_PURPLE_);
        refreshBlockCover();
    }

    // NOTICE : Multi layer for input is only available on merge and switch layer
    @Override
    public boolean isPossibleToAppendFrontByConnection() {
        return true;
    }

    // NOTICE : Multi layer for input is only available when using connection
    @Override
    public boolean isPossibleToAppendFrontByDirection() {
        return false;
    }
}
