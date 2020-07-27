package org.kok202.dluid.reducer;

import org.kok202.dluid.canvas.content.BlockConnectionPayload;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.singleton.AppSingleton;

import java.util.Observable;

public class ConnectionMoveReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.BLOCK_CONNECTION_MOVE;
    }

    @Override
    public void update(Observable o, Action action) {
        BlockConnectionPayload blockConnectionPayload = (BlockConnectionPayload) action.getPayload();
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setEnd(blockConnectionPayload.getEnd());
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setVisible(blockConnectionPayload.isVisible());
    }
}
