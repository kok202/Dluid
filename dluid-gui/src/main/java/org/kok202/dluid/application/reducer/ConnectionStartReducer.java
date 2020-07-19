package org.kok202.dluid.application.reducer;

import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.canvas.content.BlockConnectionPayload;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

public class ConnectionStartReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.BLOCK_CONNECTION_START;
    }

    @Override
    public void update(Observable o, Action action) {
        BlockConnectionPayload blockConnectionPayload = (BlockConnectionPayload) action.getPayload();
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setStart(blockConnectionPayload.getStart());
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setEnd(blockConnectionPayload.getEnd());
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setVisible(blockConnectionPayload.isVisible());
    }
}
