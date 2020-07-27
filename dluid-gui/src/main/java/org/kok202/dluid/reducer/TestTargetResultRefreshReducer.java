package org.kok202.dluid.reducer;

import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;
import org.kok202.dluid.singleton.AppSingleton;

import java.util.Observable;

public class TestTargetResultRefreshReducer extends Reducer {
    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.TEST_TARGET_RESULT_REFRESH;
    }

    @Override
    public void update(Observable o, Action action) {
        String testInputLayerId = (String) action.getPayload();
        AppSingleton.getInstance()
                .getTabsController()
                .getTabModelTestController()
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .refreshTestTargetResultLayerInformation(testInputLayerId);
    }
}
