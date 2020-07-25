package org.kok202.dluid.reducer;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.singleton.AppWidgetSingleton;

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
