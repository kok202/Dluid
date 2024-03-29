package org.kokzoz.dluid.canvas.block.plain;

import org.kokzoz.dluid.canvas.CanvasConstant;
import org.kokzoz.dluid.canvas.polygon.block.BlockFace;
import org.kokzoz.dluid.domain.entity.Layer;

public class Pooling2DBlockNode extends PlainBlockNode {
    public Pooling2DBlockNode(Layer layer) {
        super(layer);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.FRONT, CanvasConstant.COLOR_PURPLE);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.LEFT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.RIGHT, CanvasConstant.COLOR_PURPLE_);
        setBlockColor(MONO_BLOCK_INDEX, BlockFace.BACK, CanvasConstant.COLOR_PURPLE_);
        refreshBlockCover();
    }
}
