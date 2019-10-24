package org.kok202.deepblock.canvas.block.mono;

import javafx.scene.paint.Color;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.ActivationBlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public class FeedForwardBlockNode extends ActivationBlockNode {
    public FeedForwardBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_BLUE,
                        CanvasConstant.COLOR_BLUE,
                        CanvasConstant.COLOR_BLUE,
                        CanvasConstant.COLOR_BLUE,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                },
                new Color[]{
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                });
    }
}
