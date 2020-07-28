package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.test.ModelTestTestingTaskController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTestLogReducer extends Reducer {
    ModelTestTestingTaskController modelTestTestingTaskController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TEST_LOG;
    }

    @Override
    public void update(Observable o, Action action) {
        modelTestTestingTaskController.getTextAreaTestLog().clear();
    }
}
