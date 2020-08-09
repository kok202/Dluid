package org.kokzoz.dluid.canvas.block.plain;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

// Block that only one input and only one output.
// It doesn't have any technical something, you can think it as just connection edge.
public class PipeBlockNode extends PlainBlockNode {
    public PipeBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_GRAY);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_GRAY_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_GRAY_);
        refreshBlockCover();
    }

    @Override
    public boolean isPossibleToAppendFrontByConnection() {
        return false;
    }

    @Override
    public boolean isPossibleToAppendBackByConnection() {
        return false;
    }

    @Override
    public double calcHeight(){
        return getBlockInfo().getHeight();
    }
}
