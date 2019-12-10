package org.kok202.dluid.canvas.block.activation;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class Convolution1DBlockNode extends ActivationBlockNode {
    public Convolution1DBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                },
                new Color[]{
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND,
                        CanvasConstant.CONTEXT_COLOR_POSSIBLE_APPEND
                });
    }
}
