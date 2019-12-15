package org.kok202.dluid.canvas.block.mono;

import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;

// Block that only one input and only one output.
// It doesn't have any technical something, you can think it as just connection edge.
public class PipeBlockNode extends MonoBlockNode {
    public PipeBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GRAY);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GRAY_);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return false;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
