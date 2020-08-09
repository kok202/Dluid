package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.test.ModelTestFeatureTableController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

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
