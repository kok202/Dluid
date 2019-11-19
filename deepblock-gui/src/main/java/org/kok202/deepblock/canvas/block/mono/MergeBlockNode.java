package org.kok202.deepblock.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.deepblock.CanvasConstant;
import org.kok202.deepblock.ai.entity.Layer;

public class MergeBlockNode extends MonoBlockNode {
    public MergeBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
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
