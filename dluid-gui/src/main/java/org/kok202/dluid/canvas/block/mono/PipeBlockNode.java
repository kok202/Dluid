package org.kok202.dluid.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

// Block that only one input and only one output.
// It doesn't have any technical something, you can think it as just connection edge.
public class PipeBlockNode extends MonoBlockNode {
    public PipeBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.COLOR_GRAY_,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND
                });
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
