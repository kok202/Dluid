package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.TabModelTestController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

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
