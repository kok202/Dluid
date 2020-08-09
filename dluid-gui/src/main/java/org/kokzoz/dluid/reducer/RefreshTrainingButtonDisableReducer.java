package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.TabModelTrainController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTrainingButtonDisableReducer extends Reducer {
    TabModelTrainController tabModelTrainController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TRAINING_BUTTON_DISABLE;
    }

    @Override
    public void update(Observable o, Action action) {
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingOneTime().setDisable(true);
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingNTime().setDisable(true);
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingStop().setDisable(false);
        tabModelTrainController.getModelInformationController().getButtonInitialize().setDisable(true);
    }
}
