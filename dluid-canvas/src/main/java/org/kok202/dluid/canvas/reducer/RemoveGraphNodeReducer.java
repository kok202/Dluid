package org.kok202.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.canvas.singleton.structure.BlockNodeManager;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RemoveGraphNodeReducer extends Reducer {
    BlockNodeManager blockNodeManager;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REMOVE_GRAPH_NODE;
    }

    @Override
    public void update(Observable o, Action action) {
        String layerId = (String) action.getPayload();
        blockNodeManager.removeGraphNode(layerId);
    }
}
