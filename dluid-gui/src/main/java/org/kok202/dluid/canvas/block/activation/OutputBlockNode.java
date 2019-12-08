package org.kok202.dluid.canvas.block.activation;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class OutputBlockNode extends ActivationBlockNode {
    public OutputBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND
                },
                new Color[]{
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.COLOR_GRAY__,
                        CanvasConstant.COLOR_GRAY__,
                        CanvasConstant.COLOR_GRAY__,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND
                });
    }

    @Override
    public boolean isPossibleToAppendBack() {
        return false;
    }
}
