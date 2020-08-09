package org.kokzoz.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.canvas.block.BlockNode;
import org.kokzoz.dluid.canvas.singleton.structure.BlockNodeManager;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;
import org.kokzoz.dluid.domain.structure.GraphNode;

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
