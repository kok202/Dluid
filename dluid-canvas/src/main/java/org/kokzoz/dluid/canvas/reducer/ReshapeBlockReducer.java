package org.kokzoz.dluid.canvas.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.canvas.singleton.structure.BlockNodeManager;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class ReshapeBlockReducer extends Reducer {
    BlockNodeManager blockNodeManager;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.RESHAPE_BLOCK;
    }

    @Override
    public void update(Observable o, Action action) {
        String layerId = (String) action.getPayload();
        blockNodeManager.reshapeBlock(layerId);
    }
}
