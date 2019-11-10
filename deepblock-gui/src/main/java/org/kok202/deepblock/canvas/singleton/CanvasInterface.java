package org.kok202.deepblock.canvas.singleton;

import lombok.Data;
import org.kok202.deepblock.canvas.block.BlockNode;
import org.kok202.deepblock.domain.structure.GraphNode;

@Data
public class CanvasInterface {
    private static class CanvasInterfaceHolder {
        private static final CanvasInterface instance = new CanvasInterface();
    }

    public static CanvasInterface getInstance(){
        return CanvasInterfaceHolder.instance;
    }

    private CanvasInterface(){
    }

    public boolean isPossibleToAddTrainInputLayer(){
        for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes()) {
            if(graphNode.getData().getBlockInfo().getLayer().getType().isTrainInputLayerType())
                return false;
        }
        return true;
    }

    public boolean isPossibleToAddTestInputLayer(){
        for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes()) {
            if(graphNode.getData().getBlockInfo().getLayer().getType().isTestInputLayerType())
                return false;
        }
        return true;
    }

    public boolean isPossibleToAddOutputLayer(){
        for (GraphNode<BlockNode> graphNode : CanvasSingleton.getInstance().getBlockNodeManager().getGraphNodes()) {
            if(graphNode.getData().getBlockInfo().getLayer().getType().isOutputLayerType())
                return false;
        }
        return true;
    }
}
