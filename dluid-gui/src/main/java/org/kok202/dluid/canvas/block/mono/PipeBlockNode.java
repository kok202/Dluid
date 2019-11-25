package org.kok202.dluid.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class PipeBlockNode extends MonoBlockNode {
    public PipeBlockNode(Layer layer) {
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
