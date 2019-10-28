package org.kok202.deepblock.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.activation.ActivationBlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class TrainInputBlockNode extends ActivationBlockNode {
    public TrainInputBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.COLOR_GRAY,
                        CanvasConstant.CONTEXT_COLOR_IMPOSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                });
    }

    @Override
    public boolean isPossibleToAppendFront() {
        return false;
    }
}
