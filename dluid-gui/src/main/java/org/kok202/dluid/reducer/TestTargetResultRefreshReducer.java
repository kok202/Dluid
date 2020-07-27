package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.TabModelTestController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class TestTargetResultRefreshReducer extends Reducer {
    TabModelTestController tabModelTestController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.TEST_TARGET_RESULT_REFRESH;
    }

    @Override
    public void update(Observable o, Action action) {
        String testInputLayerId = (String) action.getPayload();
        tabModelTestController
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .refreshTestTargetResultLayerInformation(testInputLayerId);
    }
}
