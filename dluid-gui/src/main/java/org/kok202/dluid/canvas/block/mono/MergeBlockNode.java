package org.kok202.dluid.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class MergeBlockNode extends MonoBlockNode {
    public MergeBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_PURPLE,
                        CanvasConstant.COLOR_PURPLE_,
                        CanvasConstant.COLOR_PURPLE_,
                        CanvasConstant.COLOR_PURPLE_,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                });
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return true;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return true;
    }
}
