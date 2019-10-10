package org.kok202.deepblock.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class OutputBlockNode extends MonoBlockNode {
    public OutputBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND
                },
                new Color[]{
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND
                });
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
