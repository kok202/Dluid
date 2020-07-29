package org.kok202.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.canvas.block.BlockNode;
import org.kok202.dluid.canvas.singleton.structure.BlockNodeManager;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.domain.structure.GraphNode;

import java.util.Observable;

@AllArgsConstructor
public class RemoveAllGraphNodeReducer extends Reducer {
    BlockNodeManager blockNodeManager;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REMOVE_ALL_GRAPH_NODE;
    }

    @Override
    public void update(Observable o, Action action) {
        while(!blockNodeManager.getGraphNodes().isEmpty()){
            GraphNode<BlockNode> blockNodeGraphNode = blockNodeManager.getGraphNodes().get(0);
            blockNodeManager.removeGraphNode(blockNodeGraphNode.getData().getBlockLayer().getId());
        }
    }
}
