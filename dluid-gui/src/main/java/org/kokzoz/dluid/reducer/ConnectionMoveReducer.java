package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.canvas.content.BlockConnectionPayload;
import org.kokzoz.dluid.content.design.CanvasContainerController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class ConnectionMoveReducer extends Reducer {
    CanvasContainerController canvasContainerController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.BLOCK_CONNECTION_MOVE;
    }

    @Override
    public void update(Observable o, Action action) {
        BlockConnectionPayload blockConnectionPayload = (BlockConnectionPayload) action.getPayload();
        canvasContainerController.getBlockConnectionFollower().setEnd(blockConnectionPayload.getEnd());
        canvasContainerController.getBlockConnectionFollower().setVisible(blockConnectionPayload.isVisible());
    }
}
