package org.kok202.deepblock.canvas.block.support;

import javafx.geometry.Point2D;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;

public abstract class SupportBlockNode extends BlockNode {
    // Support material.material 은 dl4j 의 레이어에 의존적이지 않고 deep material.material 에서만 사용하는 블락 개념
    // ex. splitter material.material, random padding material.material 등..
    public SupportBlockNode(Layer layer, Point2D inputSize, Point2D outputSize) {
        super(layer, inputSize, outputSize);
    }
}
