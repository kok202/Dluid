package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.TabModelTestController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class TestEnableReducer extends Reducer {
    TabModelTestController tabModelTestController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.TEST_ENABLE;
    }

    @Override
    public void update(Observable o, Action action) {
        tabModelTestController
                .getModelTestTestingController()
                .getModelTestTestingTaskController()
                .getButtonTest()
                .setDisable(false);
    }
}
