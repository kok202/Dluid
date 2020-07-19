package org.kok202.dluid.application.reducer;

import org.kok202.dluid.application.singleton.AppWidgetSingleton;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

public class ConnectionReleaseReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.BLOCK_CONNECTION_RELEASE;
    }

    @Override
    public void update(Observable o, Action action) {
        AppWidgetSingleton.getInstance()
                .getTabsController()
                .getTabModelDesignController()
                .getCanvasContainerController()
                .getBlockConnectionFollower()
                .setVisible(false);
    }
}
