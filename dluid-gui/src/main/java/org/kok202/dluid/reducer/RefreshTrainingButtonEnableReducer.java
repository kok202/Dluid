package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.TabModelTrainController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTrainingButtonEnableReducer extends Reducer {
    TabModelTrainController tabModelTrainController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TRAINING_BUTTON_ENABLE;
    }

    @Override
    public void update(Observable o, Action action) {
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingOneTime().setDisable(false);
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingNTime().setDisable(false);
        tabModelTrainController.getModelTrainTaskController().getButtonTrainingStop().setDisable(true);
        tabModelTrainController.getModelInformationController().getButtonInitialize().setDisable(false);
    }
}
