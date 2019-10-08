package org.kok202.deepblock.canvas.block.layer;

import javafx.geometry.Point2D;
import org.kok202.deepblock.ai.entity.Layer;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.canvas.singleton.CanvasConstant;
import org.kok202.deepblock.canvas.singleton.CanvasSingleton;
import org.kok202.deepblock.domain.structure.TreeNode;

public abstract class LayerBlockNode extends BlockNode {
    public LayerBlockNode(Layer layer) {
        super(layer,
                new Point2D(
                        layer.getProperties().getInputSize()[0] * CanvasConstant.NODE_UNIT,
                        layer.getProperties().getInputSize()[1] * CanvasConstant.NODE_UNIT),
                new Point2D(
                        layer.getProperties().getOutputSize()[0] * CanvasConstant.NODE_UNIT,
                        layer.getProperties().getOutputSize()[1] * CanvasConstant.NODE_UNIT));
    }

    @Override
    public boolean isPossibleToAppendFront() {
        TreeNode<BlockNode> frontTreeNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findTreeNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontTreeNode.getParent() == null;
    }

    @Override
    public boolean isPossibleToAppendBack() {
        TreeNode<BlockNode> frontTreeNode = CanvasSingleton.getInstance()
                .getBlockNodeManager()
                .findTreeNodeByLayerId(this.getBlockInfo().getLayer().getId());
        return frontTreeNode.getChildren().isEmpty();
    }
}
