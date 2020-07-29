package org.kok202.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kok202.dluid.content.train.ModelTrainTaskController;
import org.kok202.dluid.domain.action.Action;
import org.kok202.dluid.domain.action.ActionType;
import org.kok202.dluid.domain.action.Reducer;

import java.util.Observable;

@AllArgsConstructor
public class RefreshTrainingLogReducer extends Reducer {
    ModelTrainTaskController modelTrainTaskController;

    @Override
    public boolean support(Action action) {
        return action.getType() == ActionType.REFRESH_TRAINING_LOG;
    }

    @Override
    public void update(Observable o, Action action) {
        modelTrainTaskController.getLineChartAdapter().clearChart();
        modelTrainTaskController.getTextAreaTrainingLog().clear();
    }
}
