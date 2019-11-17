package org.kok202.deepblock.canvas.block.activation;

import javafx.scene.paint.Color;
import org.kok202.deepblock.CanvasConstant;
import org.kok202.deepblock.ai.entity.Layer;

public class OutputBlockNode extends ActivationBlockNode {
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
