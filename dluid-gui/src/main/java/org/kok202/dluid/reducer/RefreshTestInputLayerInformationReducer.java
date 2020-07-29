package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.test.ModelTestFeatureTableController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTestInputLayerInformationReducer extends Reducer {
    ModelTestFeatureTableController modelTestFeatureTableController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TEST_INPUT_LATYER_INFORMATION;
    }

    @Override
    public void update(Observable o, Action action) {
        modelTestFeatureTableController.refreshTestInputLayerInformation();
    }
}
