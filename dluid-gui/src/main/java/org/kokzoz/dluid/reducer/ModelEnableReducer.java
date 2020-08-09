package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.TabsController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class ModelEnableReducer extends Reducer {
    TabsController tabsController;


    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.MODEL_ENABLE;
    }

    @Override
    public void update(Observable o, Action action) {
        tabsController.getTabModelTrainController().setSettingExpandAndDisable(false);
        tabsController.getTabModelTestController().setSettingExpandAndDisable(false);
    }
}
