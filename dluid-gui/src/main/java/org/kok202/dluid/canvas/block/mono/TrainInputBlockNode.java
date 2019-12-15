package org.kok202.dluid.canvas.block.mono;

import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;
import org.kok202.dluid.canvas.polygon.block.BlockFace;

public class TrainInputBlockNode extends MonoBlockNode {
    public TrainInputBlockNode(Layer layer) {
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
}
