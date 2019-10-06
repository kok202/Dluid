package org.kok202.deepblock.canvas.block.layer;

import javafx.geometry.Point2D;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;

public abstract class LayerBlockNode extends BlockNode {
    public LayerBlockNode(Layer layer) {
        super(layer,
                new Point2D(
                        layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                        layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT),
                new Point2D(
                        layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                        layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT));
//        blockLayerModel.setColors(new Color[]{
//                CanvasConstant.COLOR_BLUE,
//                CanvasConstant.COLOR_BLUE,
//                CanvasConstant.COLOR_BLUE,
//                CanvasConstant.COLOR_BLUE,
//                CanvasConstant.COLOR_YELLOW,
//                CanvasConstant.COLOR_YELLOW});
//        blockLayerModel.refreshBlockCover();
//        or
//        blockLayerModel.setTextures(new String[]{
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString(),
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString(),
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString(),
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString(),
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString(),
//                getClass().getClassLoader().getResource("images/sample_texture.png").toString()});
//        blockLayerModel.refreshBlockCover();
    }
}
