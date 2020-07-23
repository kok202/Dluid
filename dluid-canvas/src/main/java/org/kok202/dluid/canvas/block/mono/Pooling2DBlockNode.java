package org.kok202.dluid.canvas.block.mono;

import org.kok202.dluid.canvas.CanvasConstant;
import org.kok202.dluid.canvas.polygon.block.BlockFace;
import org.kok202.dluid.domain.entity.Layer;

public class Pooling2DBlockNode extends MonoBlockNode {
    public Pooling2DBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_PURPLE);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_PURPLE_);
        refreshBlockCover();
    }
}
