package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.canvas.content.BlockConnectionPayload;
import org.kok202.dluid.content.design.CanvasContainerController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class ConnectionStartReducer extends Reducer {
    CanvasContainerController canvasContainerController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.BLOCK_CONNECTION_START;
    }

    @Override
    public void update(Observable o, Action action) {
        BlockConnectionPayload blockConnectionPayload = (BlockConnectionPayload) action.getPayload();
        canvasContainerController.getBlockConnectionFollower().setStart(blockConnectionPayload.getStart());
        canvasContainerController.getBlockConnectionFollower().setEnd(blockConnectionPayload.getEnd());
        canvasContainerController.getBlockConnectionFollower().setVisible(blockConnectionPayload.isVisible());
    }
}
