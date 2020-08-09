package org.kokzoz.dluid.reducer;

import lombok.AllArgsConstructor;
import org.kokzoz.dluid.content.train.ModelTrainTaskController;
import org.kokzoz.dluid.domain.action.Action;
import org.kokzoz.dluid.domain.action.ActionType;
import org.kokzoz.dluid.domain.action.Reducer;

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
