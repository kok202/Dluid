package org.kok202.dluid.reducer;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.singleton.AppSingleton;

import java.util.Observable;

public class TestSucceedReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.TEST_SUCCEED;
    }

    @Override
    public void update(Observable o, Action action) {
        AppSingleton.getInstance()
            .getTabsController()
            .getTabModelTestController()
            .getModelTestTestingController()
            .getModelTestTestingTaskController()
            .getButtonTest()
            .setDisable(false);
        AppSingleton.getInstance()
            .getTabsController()
            .getTabModelTestController()
            .setTestResultTableExpandAndDisable(false);
    }
}
