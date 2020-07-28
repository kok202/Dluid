package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.TabsController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

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
