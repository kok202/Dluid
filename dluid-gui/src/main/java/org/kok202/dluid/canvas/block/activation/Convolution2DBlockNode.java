package org.kok202.dluid.canvas.block.activation;

import javafx.scene.paint.Color;
import org.kok202.dluid.CanvasConstant;
import org.kok202.dluid.ai.entity.Layer;

public class Convolution2DBlockNode extends ActivationBlockNode {
    public Convolution2DBlockNode(Layer layer) {
        super(layer);
        setBlockCover(
                new Color[]{
                        CanvasConstant.COLOR_RED,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                },
                new Color[]{
                        CanvasConstant.COLOR_RED_,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.COLOR_RED__,
                        CanvasConstant.COLOR_YELLOW,
                        CanvasConstant.COLOR_YELLOW
                });
    }
}
